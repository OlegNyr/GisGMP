package ru.nyrk.gisgmp.core.bis.saver;

import org.apache.commons.vfs2.FileObject;
import org.apache.xpath.XPathAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nyrk.gisgmp.database.entity.TicketEntity;
import ru.nyrk.gisgmp.util.crypto.Const;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;
import ru.nyrk.util.WriterXML;
import ru.nyrk.vfs.MyVFS;

import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

@Component
public class BisSaver {

    @Value("${bis.path.export}")
    String exportPath;

    @Qualifier("SOAPUtility")
    @Autowired
    private SOAPUtility utility;

    public void executor(TicketEntity ticketEntity, SOAPMessage respn) throws Exception {
        if (respn == null)
            respn = utility.getSOAPMessageFromStream(new ByteArrayInputStream(ticketEntity.getDataEntity().getMesData()));
        Document docSource = respn.getSOAPPart().getEnvelope().getOwnerDocument();

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        WriterXML wx = new WriterXML(byteArrayOutputStream);
        try {
            wx.startDocument();
            wx.openTag("AppData");
            wx.openTag("ImportDataResponse").attr("ref", ticketEntity.getMessagesEntity().getBisRefNm());

            Element elMsg = (Element) XPathAPI.selectSingleNode(docSource.getDocumentElement(), "//*[local-name()='UnifoTransferMsg' and namespace-uri()='" + Const.SCHEMA_SmevUnifoService + "']");
            if (elMsg != null) {
                String pref_tick = elMsg.lookupPrefix(Const.SCHEMA_TICKET);
                Element elTicket = (Element) XPathAPI.selectSingleNode(elMsg, ".//" + pref_tick + ":Ticket");

                wx.openTag("Ticket");
                Element elPost = (Element) XPathAPI.selectSingleNode(elTicket, "./PostBlock");
                if (elPost != null) {
                    wx.openTag("PostBlock");
                    try {
                        wx.tagValue("ID", ticketEntity.getMessagesEntity().getPostBlockId());
                        wx.tagValue("TimeStamp", XPathAPI.selectSingleNode(elPost, "./TimeStamp").getTextContent());
                        wx.tagValue("SenderIdentifier", XPathAPI.selectSingleNode(elPost, "./SenderIdentifier").getTextContent());
                    } finally {
                        wx.closeTag();
                    }
                }

                Element elResult = (Element) XPathAPI.selectSingleNode(elTicket, "./RequestProcessResult");
                if (elResult != null) {
                    wx.openTag("RequestProcessResult");
                    try {

                        wx.tagValue("ErrorCode", ticketEntity.getErrorKd());
                        wx.tagValue("ErrorDescription", ticketEntity.getErrorMsg());
                        wx.tagValue("ErrorData", "");
                    } finally {
                        wx.closeTag();
                    }
                }

            } else {

                String pref_tick = docSource.getDocumentElement().lookupPrefix(Const.SCHEMA_SOAP);
                Element elResult = (Element) XPathAPI.selectSingleNode(docSource.getDocumentElement(), "//"+pref_tick+":Fault");
                if (elResult != null) {
                    try {
                        wx.openTag("RequestProcessResult");
                        wx.tagValue("ErrorCode", XPathAPI.selectSingleNode(elResult, "./faultcode").getNodeValue());
                        wx.tagValue("ErrorDescription", XPathAPI.selectSingleNode(elResult, "./faultstring").getNodeValue());
                        wx.tagValue("ErrorData", "");
                    } finally {
                        wx.closeTag();
                    }
                }
            }

        } finally {
            wx.closeTags();
        }
        wx.close();

        FileObject rootFile = MyVFS.getManager().resolveFile(exportPath);
        FileObject destFile = rootFile.resolveFile(ticketEntity.getMessagesEntity().getDataSourceEntity().getFileNm() + ".ticket." + ticketEntity.getTicketId());
        destFile.createFile();
        OutputStream out = destFile.getContent().getOutputStream();
        try {
            out.write(byteArrayOutputStream.toByteArray());
        } finally {
            out.close();
        }
    }
}
