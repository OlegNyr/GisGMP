package ru.nyrk.gisgmp.core;

import org.apache.xpath.XPathAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import ru.nyrk.gisgmp.util.crypto.Const;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;

import javax.xml.soap.SOAPMessage;
import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: olegnyr
 * Date: 09.12.13
 * Time: 19:23
 * To change this template use File | Settings | File Templates.
 */
@Component
public class ParsingTest {
    @Autowired
    private SOAPUtility soapUtility;

    public void run() throws Exception {
        SOAPMessage sm = soapUtility.getSOAPMessageFromFile(new File("c:\\_Projects\\intr\\GisGMP\\doc\\responseErrorSoap.xml"));
        Document doc = sm.getSOAPPart().getEnvelope().getOwnerDocument();

        Element elMsg = (Element) XPathAPI.selectSingleNode(doc.getDocumentElement(), "//*[local-name()='UnifoTransferMsg' and namespace-uri()='" + Const.SCHEMA_SmevUnifoService + "']");
        if (elMsg != null) {
            String pref_smev = doc.getDocumentElement().lookupPrefix(Const.SCHEMA_SMEV);
            String pref_tick = doc.getDocumentElement().lookupPrefix(Const.SCHEMA_TICKET);


            Element elTicket = (Element) XPathAPI.selectSingleNode(elMsg, ".//*" + pref_tick + ":Ticket");

            Element code = (Element) XPathAPI.selectSingleNode(elTicket, "./RequestProcessResult/ErrorCode");

            System.out.println(code.getTextContent());

            code = (Element) XPathAPI.selectSingleNode(elTicket, "./RequestProcessResult/ErrorDescription");
            System.out.println(code.getTextContent());
        }



    }
}
