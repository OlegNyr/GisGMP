package ru.nyrk.gisgmp.util.smev;

import org.springframework.stereotype.Component;
import ru.nyrk.gisgmp.util.crypto.Const;
import ru.nyrk.gisgmp.util.soap.SOAPFactory;

import javax.xml.soap.SOAPMessage;

@Component
public class SMEVFactory extends SOAPFactory {

    @Override
    public SOAPMessage createMessage() throws Exception {
        SOAPMessage sm = super.createMessage();

        sm.getSOAPPart().getEnvelope().addNamespaceDeclaration("wsse", Const.SCHEMA_WSSE);
        sm.getSOAPPart().getEnvelope().addNamespaceDeclaration("wsu", Const.SCHEMA_WSU);
        //sm.getSOAPPart().getEnvelope().addNamespaceDeclaration("ds", Const.SCHEMA_DS);
        sm.getSOAPBody().setAttributeNS(Const.SCHEMA_WSU, "wsu:Id", "body");
        return sm;
    }



}
