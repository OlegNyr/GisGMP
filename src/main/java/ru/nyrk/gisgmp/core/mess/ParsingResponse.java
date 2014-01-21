package ru.nyrk.gisgmp.core.mess;

import org.apache.xpath.XPathAPI;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nyrk.gisgmp.database.entity.DataEntity;
import ru.nyrk.gisgmp.database.entity.TicketEntity;
import ru.nyrk.gisgmp.database.service.DataService;
import ru.nyrk.gisgmp.util.crypto.Const;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;
import ru.nyrk.util.Base64;

import javax.xml.soap.SOAPMessage;
import java.io.ByteArrayInputStream;

@Component
public class ParsingResponse {


    @Qualifier("SOAPUtility")
    @Autowired
    private SOAPUtility utility;
    @Qualifier("dataService")
    @Autowired
    private DataService dataService;


    SOAPMessage execute(TicketEntity te) throws Exception {


        SOAPMessage sm = utility.getSOAPMessageFromStream(new ByteArrayInputStream(te.getDataEntity().getMesData()));
        Document doc = sm.getSOAPPart().getEnvelope().getOwnerDocument();

        Element elMsg = (Element) XPathAPI.selectSingleNode(doc.getDocumentElement(), "//*[local-name()='UnifoTransferMsg' and namespace-uri()='" + Const.SCHEMA_SmevUnifoService + "']");
        if (elMsg != null) {
//            String pref_smev = doc.getDocumentElement().lookupPrefix(Const.SCHEMA_SMEV);

            String pref_tick = elMsg.lookupPrefix(Const.SCHEMA_TICKET);

            Element elTicket = (Element) XPathAPI.selectSingleNode(elMsg, ".//" + pref_tick + ":Ticket");
            if (elTicket != null) {

                Element code = (Element) XPathAPI.selectSingleNode(elTicket, "./RequestProcessResult/ErrorCode");
                if (code != null)
                    te.setErrorKd(code.getTextContent());

                Element elMess = (Element) XPathAPI.selectSingleNode(elTicket, "./RequestProcessResult/ErrorDescription");
                if (elMess != null)
                    te.setErrorMsg(elMess.getTextContent());

                Element elTimeStamp = (Element) XPathAPI.selectSingleNode(elTicket, "./PostBlock/TimeStamp");
                te.setTicketDt(DateTime.parse(elTimeStamp.getTextContent()).toLocalDateTime());
            } else {
                String pref_response = elMsg.lookupPrefix(Const.SCHEMA_UNIFO);
                Element elResponse = (Element) XPathAPI.selectSingleNode(elMsg, ".//" + pref_response + ":exportDataResponse");

                if (elResponse != null) {
                    Element elData = (Element) XPathAPI.selectSingleNode(elResponse, "./ResponseTemplate/Charges/ChargeInfo/ChargeData");
                    if (elData != null) {
                        String dataString = elData.getTextContent();
                        byte[] dataByte = Base64.decode(dataString);
                        dataService.save(new DataEntity(dataByte, String.valueOf(te.getMessagesEntity().getMesId())));
                    }
                }

            }

        }

        return sm;
    }
}
