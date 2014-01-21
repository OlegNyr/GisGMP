package ru.nyrk.gisgmp.util.smev;


import org.apache.xpath.XPathAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.Element;
import ru.nyrk.gisgmp.database.entity.MessagesEntity;
import ru.nyrk.gisgmp.util.crypto.Const;
import ru.nyrk.gisgmp.util.crypto.SOAPXMLSignatureManager;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;
import ru.nyrk.util.DateTry;
import ru.nyrk.util.StrTry;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;
import java.util.Date;

@Component
public class UnifoTransferMsg {

    @Autowired
    private SMEVFactory smevFactory;

    @Autowired
    private ParamSmev smev;


    @Autowired
    @Qualifier("SOAPXMLSignatureManager")
    private SOAPXMLSignatureManager manager;

    private SOAPBodyElement createUnifoTransferMsg(SOAPMessage sm) throws Exception {
        SOAPBodyElement soapBodyElement = sm.getSOAPBody().addBodyElement(new QName(Const.SCHEMA_SmevUnifoService, "UnifoTransferMsg", "n1"));
        soapBodyElement.setAttribute("xsi:schemaLocation", Const.SCHEMA_LOCATION);
        soapBodyElement.addNamespaceDeclaration("ds", Const.SCHEMA_DS);
        soapBodyElement.addNamespaceDeclaration("xsi", Const.SCHEMA_XSI);
        soapBodyElement.addNamespaceDeclaration("smev", Const.SCHEMA_SMEV);
        soapBodyElement.addNamespaceDeclaration("unifo", Const.SCHEMA_UNIFO);
        soapBodyElement.addNamespaceDeclaration("pirq", Const.SCHEMA_PIRQ);
        soapBodyElement.addNamespaceDeclaration("pdrq", Const.SCHEMA_PDRQ);

        return soapBodyElement;
    }

    public SOAPElement addMessageAppData(SOAPMessage sm) throws Exception {
        SOAPElement elUnifoTransferMsg = createUnifoTransferMsg(sm);
        SOAPElement elMessage = elUnifoTransferMsg.addChildElement("Message", "smev");
        {
            SOAPElement elSender = elMessage.addChildElement("Sender", "smev");
            elSender.addChildElement("Code", "smev").setTextContent(smev.getSender_code());
            elSender.addChildElement("Name", "smev").setTextContent(smev.getSender_name());
            SOAPElement elRecipient = elMessage.addChildElement("Recipient", "smev");
            elRecipient.addChildElement("Code", "smev").setTextContent(smev.getRecipient_code());
            elRecipient.addChildElement("Name", "smev").setTextContent(smev.getRecipient_name());

            elMessage.addChildElement("TypeCode", "smev").setTextContent(smev.getTypecode());
            elMessage.addChildElement("Status", "smev").setTextContent("REQUEST");
            elMessage.addChildElement("Date", "smev").setTextContent(DateTry.toStringISO(new Date()));
            elMessage.addChildElement("ExchangeType", "smev").setTextContent(smev.getExchangeType());
        }
        return elUnifoTransferMsg.
                addChildElement("MessageData", "smev").
                addChildElement("AppData", "smev");
    }

    public void addMessageImportRequest(SOAPMessage sm, org.w3c.dom.Document doc) throws Exception {

        SOAPElement appData = addMessageAppData(sm);

        SOAPElement elImportRequestTarget = appData.
                addChildElement("ImportData", "unifo").
                addChildElement("ImportRequest", "pirq");

        Element elImportRequestSource = (Element) XPathAPI.selectSingleNode(doc.getDocumentElement(), "//AppData/ImportData/ImportRequest");
        Element elFinal = (Element) XPathAPI.selectSingleNode(elImportRequestSource, "./FinalPayment");
        elFinal.removeAttribute("Version");


        Element elPostBlock = (Element) XPathAPI.selectSingleNode(elImportRequestSource, "./PostBlock");

        Element elSendInd = doc.createElement("SenderIdentifier");
        elSendInd.setTextContent(smev.getSender_code());
        elPostBlock.appendChild(elSendInd);

        SOAPUtility.copyChildElements(elImportRequestTarget, elImportRequestSource.getChildNodes());
    }

    public void addMessageExportData(SOAPMessage sm, MessagesEntity msEnt) throws Exception {
        SOAPElement appData = addMessageAppData(sm);
        SOAPElement elDataRequest = appData.addChildElement("exportData", "unifo").addChildElement("DataRequest", "pdrq");
        elDataRequest.setAttribute("kind", msEnt.getTypeMessEntity().getTypeMessKd());
        SOAPElement elPostBlock = elDataRequest.addChildElement("PostBlock");
        elPostBlock.addChildElement("ID").setTextContent(msEnt.getPostBlockId());
        elPostBlock.addChildElement("TimeStamp").setTextContent(DateTry.toStringISO(msEnt.getCreateDt().toDate()));
        elPostBlock.addChildElement("SenderIdentifier").setTextContent(smev.getSender_code());
//        if (!StrTry.isEmpty(msEnt.getSupplierBillId())) {
//            elDataRequest.addChildElement("SupplierBillIDs").addChildElement("SupplierBillID").setTextContent(msEnt.getSupplierBillId());
//        }
        if (!StrTry.isEmpty(msEnt.getPayerIdentifier())) {
            elDataRequest.addChildElement("Payers").addChildElement("PayerIdentifier").setTextContent(msEnt.getPayerIdentifier());
        }
    }
}

