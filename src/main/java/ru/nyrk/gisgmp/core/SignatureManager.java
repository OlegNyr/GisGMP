package ru.nyrk.gisgmp.core;

import org.w3c.dom.Document;

import javax.xml.soap.SOAPMessage;

public interface SignatureManager {

    public void constructSecuredMessage(SOAPMessage mf) throws Exception;


    void testSignCert(Document doc) throws Exception;
}
