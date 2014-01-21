package ru.nyrk.gisgmp.core.mess;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.commons.vfs2.FileObject;
import org.joda.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import ru.nyrk.gisgmp.core.bis.elm.AppData;
import ru.nyrk.gisgmp.core.bis.loader.ParsingBisFile;
import ru.nyrk.gisgmp.core.bis.saver.BisSaver;
import ru.nyrk.gisgmp.database.entity.DataEntity;
import ru.nyrk.gisgmp.database.entity.MessagesEntity;
import ru.nyrk.gisgmp.database.entity.TicketEntity;
import ru.nyrk.gisgmp.database.service.FoldersService;
import ru.nyrk.gisgmp.database.service.MessagesService;
import ru.nyrk.gisgmp.database.service.TicketService;
import ru.nyrk.gisgmp.database.service.TypeMessService;
import ru.nyrk.gisgmp.util.XMLUtility;
import ru.nyrk.gisgmp.util.crypto.SOAPXMLSignatureManager;
import ru.nyrk.gisgmp.util.smev.SMEVFactory;
import ru.nyrk.gisgmp.util.smev.UnifoTransferMsg;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;
import ru.nyrk.util.HttpHelper;
import ru.nyrk.util.XMLMyUtil;

import javax.xml.soap.SOAPMessage;
import java.io.*;


@Component
public class MessageExecuttor {


    private static final Logger log = LoggerFactory.getLogger(MessageExecuttor.class);

    @Qualifier("foldersService")
    @Autowired
    private FoldersService foldersService;

    @Qualifier("messagesService")
    @Autowired
    private MessagesService messagesService;

    @Qualifier("parsingBisFile")
    @Autowired
    private ParsingBisFile parsingBisFile;

    @Autowired
    @Qualifier("SOAPXMLSignatureManager")
    private SOAPXMLSignatureManager manager;

    @Autowired
    private SMEVFactory smevFactory;


    @Autowired
    private UnifoTransferMsg unifoTransferMsg;

    @Value("${smev.url}")
    String urlSmev;

    @Qualifier("ticketService")
    @Autowired
    private TicketService ticketService;
    @Qualifier("parsingResponse")
    @Autowired
    private ParsingResponse parsingResponse;
    @Qualifier("bisSaver")
    @Autowired
    private BisSaver bisSaver;
    @Qualifier("typeMessService")
    @Autowired
    private TypeMessService typeMessService;


    /**
     * Бум грузить и создавать сообщение
     *
     *
     * @param file
     * @throws IOException
     */
    public long loadMessageFromFileBisFormat(FileObject file) throws Exception {

        byte[] sourceBt = IOUtils.toByteArray(file.getContent().getInputStream());
        AppData appData = parsingBisFile.parsingBisSource(new ByteArrayInputStream(sourceBt));

        DataEntity dataEntity = new DataEntity();
        dataEntity.setFileNm(file.getName().getBaseName());
        dataEntity.setMesData(sourceBt);

        MessagesEntity messagesEntity = new MessagesEntity();
        messagesEntity.setFoldersEntity(foldersService.getReference("outbox"));
        messagesEntity.setDataSourceEntity(dataEntity);
        messagesEntity.setPostBlockId(appData.getImportData().getImportRequest().getPostBlock().getId());
        messagesEntity.setPostBlockDt(appData.getImportData().getImportRequest().getPostBlock().getTimeStamp().toLocalDateTime());
        messagesEntity.setBisRefNm(appData.getImportData().getRef());
        messagesEntity.setPayerIdentifier(appData.getImportData().getImportRequest().getFinalPayment().getPayerIdentifier());
        messagesEntity.setSupplierBillId(appData.getImportData().getImportRequest().getFinalPayment().getSupplierBillID());
        messagesEntity.setCreateDt(LocalDateTime.now());
        messagesEntity.setChangeStatus(appData.getImportData().getImportRequest().getFinalPayment().getChangeStatus());

        messagesEntity.setTypeMessEntity(typeMessService.getReference("SENDPAY"));
        messagesEntity = messagesService.save(messagesEntity);

        log.debug("{}", dataEntity);

        return messagesEntity.getMesId();
    }


    public void sendMess(MessagesEntity msEnt) throws Exception {
        try {
            //Сформируем сообщение
            SOAPMessage soapMessage = null;
            byte[] byMessage = null;

            if (msEnt.getDataEntity() == null) {


                soapMessage = smevFactory.createMessage();
                if (msEnt.getTypeMessEntity().getTypeMessKd().equals("SENDPAY")) {
                    Document srcDoc = XMLUtility.createDocument(new ByteArrayInputStream(msEnt.getDataSourceEntity().getMesData()));
                    //Импортруем исходник
                    unifoTransferMsg.addMessageImportRequest(soapMessage, srcDoc);

                    //Подпишем элемент для гис ГМП
                    manager.signElementByTag(soapMessage, "FinalPayment");
                } else if (msEnt.getTypeMessEntity().getTypeMessKd().equals("CHARGE")) {
                    unifoTransferMsg.addMessageExportData(soapMessage, msEnt);

                    //manager.signElementByTag(soapMessage, "DataRequest");
                }


                //Сформируем заголовок для СМЭВ
                manager.constructSecuredMessage(soapMessage);
                //Сохраним запрос
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                SOAPUtility.saveToStream(soapMessage, bout);

                byMessage = bout.toByteArray();
                DataEntity data = new DataEntity();
                data.setMesData(byMessage);

                msEnt.setDataEntity(data);
                msEnt = messagesService.save(msEnt);
            } else {
                byMessage = msEnt.getDataEntity().getMesData();
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            //Отправляем
            HttpHelper httpHelper = new HttpHelper();
            httpHelper.setEnabledChunked(false);
            httpHelper.setResponseStream(out);

            if (log.isDebugEnabled()) {
                log.debug("Request {}", XMLMyUtil.formatXml(byMessage));
            }
            String res = httpHelper.performPost(urlSmev, byMessage);
            if (log.isDebugEnabled()) {
                log.debug("Response {} \n {}", res, XMLMyUtil.formatXml(out.toByteArray()));
            }

            TicketEntity te = new TicketEntity();
            te.setMessagesEntity(msEnt);
            te.setDataEntity(new DataEntity(out.toByteArray()));

            SOAPMessage respn = parsingResponse.execute(te);

            te = ticketService.save(te);

            //Привязать тикет к сообщению
            //и передовать его для сохранение
            //Плюс передовать сообщение ответа

            if (msEnt.getTypeMessEntity().getTypeMessKd().equals("SENDPAY"))
                bisSaver.executor(te, respn);

            msEnt.setFoldersEntity(foldersService.getReference("sended"));
            messagesService.save(msEnt);


        } catch (Throwable th) {

            TicketEntity te = new TicketEntity();
            te.setMessagesEntity(msEnt);
            te.setErrorKd("Error");
            te.setErrorMsg(ExceptionUtils.getMessage(th));

            ByteArrayOutputStream bb = new ByteArrayOutputStream();
            ExceptionUtils.printRootCauseStackTrace(th, new PrintStream(bb));
            te.setDataEntity(new DataEntity(bb.toByteArray()));

            te = ticketService.save(te);

            msEnt.setFoldersEntity(foldersService.getReference("sended"));
            messagesService.save(msEnt);
        }

    }
}
