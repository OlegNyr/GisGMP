package ru.nyrk.gisgmp.core;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import ru.nyrk.gisgmp.util.crypto.SOAPXMLSignatureManager;
import ru.nyrk.gisgmp.util.smev.ParamSmev;
import ru.nyrk.gisgmp.util.smev.SMEVFactory;
import ru.nyrk.gisgmp.util.smev.UnifoTransferMsg;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;
import ru.nyrk.util.HttpHelper;
import ru.nyrk.util.XMLMyUtil;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import java.io.File;

@Component
public class StartTest {

    private static final Logger log = LoggerFactory.getLogger(StartTest.class);

    @Autowired
    @Qualifier("SOAPXMLSignatureManager")
    private SOAPXMLSignatureManager manager;

    @Autowired
    private SMEVFactory smevFactory;


    @Autowired
    private UnifoTransferMsg unifoTransferMsg;
    @Autowired
    private ParamSmev smev;
    @Autowired
    private SOAPUtility soapUtility;

    public void run() throws Exception {


        SOAPMessage soapMessage = smevFactory.createMessage();
        File fXmlFile = new File("c:\\_Projects\\intr\\GisGMP\\doc\\SMEV01382.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(fXmlFile);
        doc.getDocumentElement().normalize();

        unifoTransferMsg.addMessageImportRequest(soapMessage, doc);

        manager.signElementByTag(soapMessage, "FinalPayment");
        manager.constructSecuredMessage(soapMessage);

        SOAPUtility.saveToFile(soapMessage, new File("c:\\_Projects\\intr\\GisGMP\\doc\\request.xml"));

        HttpHelper hh = new HttpHelper();
        hh.setProxyHost("172.16.2.1", 3128);
        hh.setEnabledChunked(false);

        String ss = hh.performPost("http://188.254.16.92:7777/gateway/services/SID0003218", SOAPUtility.saveToString(soapMessage).getBytes());
        System.out.println(XMLMyUtil.formatXml(ss.getBytes()));
        FileUtils.writeByteArrayToFile(new File("c:\\_Projects\\intr\\GisGMP\\doc\\response.xml"), ss.getBytes());
    }
}
