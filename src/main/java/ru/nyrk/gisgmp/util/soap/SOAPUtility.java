/**
 * Copyright 2004-2012 Crypto-Pro. All rights reserved.
 * Этот файл содержит информацию, являющуюся
 * собственностью компании Крипто-Про.
 *
 * Любая часть этого файла не может быть скопирована,
 * исправлена, переведена на другие языки,
 * локализована или модифицирована любым способом,
 * откомпилирована, передана по сети с или на
 * любую компьютерную систему без предварительного
 * заключения соглашения с компанией Крипто-Про.
 */

package ru.nyrk.gisgmp.util.soap;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.transform.dom.DOMSource;
import java.io.*;

/**
 * Class with auxiliary SOAP functions.
 */
@Component
public class SOAPUtility {
    @Autowired
    @Qualifier(value = "SOAPFactory")
    private SOAPFactory factory;

    /**
     * Function checks 'org.apache.ws.security.crypto.provider' option in
     * 'crypto.properties' to determine wss4j version:
     * if Merlin's base class is AbstractCrypto, then wss4j version is 1.5.11,
     * else if Merlin's base class is CryptoBase, then version is 1.6.3.
     *
     * @param className - name of provider (Merlin).
     * @return - true, if version is 1.5.11.
     * @throws ClassNotFoundException
     */
    public static boolean is_1_5_11(String className) throws ClassNotFoundException {

        Class classType = Class.forName(className);
        Class superClassType = classType.getSuperclass();
        String superClassName = superClassType.getSimpleName();

        // If properties contains MerlinEx class
        if (superClassName.equalsIgnoreCase("Merlin")) {
            return is_1_5_11(superClassType.getName());
        }

        if (superClassName.equalsIgnoreCase("AbstractCrypto")) {
            return true;
        }

        return false;
    }

    public SOAPMessage getSOAPMessageFromString(String text) throws Exception {
        InputStream input = new ByteArrayInputStream(text.getBytes());
        return getSOAPMessageFromStream(input);
    }

    public SOAPMessage getSOAPMessageFromFile(File file) throws Exception {
        return getSOAPMessageFromStream(new FileInputStream(file));
    }

    static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    public static DOMSource getDOMSourceFromStream(InputStream inputStream) throws Exception {

        dbFactory.setNamespaceAware(true);
        DocumentBuilder builder = dbFactory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        return new DOMSource(document);
    }

    public SOAPMessage getSOAPMessageFromStream(InputStream inputStream) throws Exception {
        SOAPMessage message = factory.createMessage();
        SOAPPart soapPart = message.getSOAPPart();
        soapPart.setContent(getDOMSourceFromStream(inputStream));
        return message;
    }

    public static void copyAttributes(SOAPElement target,
                                      Element source) {
        NamedNodeMap attrs = source.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            Node nd = attrs.item(i);
            target.setAttribute(nd.getNodeName(), nd.getNodeValue());
        }
    }

    public static SOAPElement copyChildElement(SOAPElement parent,
                                               Element source) throws SOAPException {
        SOAPElement el = parent.addChildElement(source.getNodeName());
        copyAttributes(el, source);
        if (source.hasChildNodes())
            copyChildElements(el, source.getChildNodes());

        return el;

    }

    public static void copyChildElements(SOAPElement el, NodeList nodeList) throws SOAPException {

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nd = nodeList.item(i);
            if (nd.getNodeType() == Node.ELEMENT_NODE) {
                copyChildElement(el, (Element) nd);
            } else if (nd.getNodeType() == Node.TEXT_NODE) {
                if (!el.hasChildNodes())
                    el.setValue(nd.getNodeValue());
            }

        }
    }

    public static void saveToFile(SOAPMessage soapMessage, File file) throws IOException, SOAPException {
        OutputStream ou = FileUtils.openOutputStream(file);
        saveToStream(soapMessage, ou);
        ou.close();
    }

    public static String saveToString(SOAPMessage soapMessage) throws IOException, SOAPException {
        Document dc = soapMessage.getSOAPPart().getEnvelope().getOwnerDocument();
        return org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(dc);
    }

    public static void saveToStream(SOAPMessage soapMessage, OutputStream outputStream) throws SOAPException {
        Document dc = soapMessage.getSOAPPart().getEnvelope().getOwnerDocument();
        org.apache.ws.security.util.XMLUtils.ElementToStream(dc.getDocumentElement(), outputStream);
    }

    public static void refreshSoap(SOAPMessage soapMessage) throws Exception {

        ByteArrayOutputStream but = new ByteArrayOutputStream();
        SOAPUtility.saveToStream(soapMessage, but);
        but.close();

        ByteArrayInputStream bit = new ByteArrayInputStream(but.toByteArray());
        soapMessage.getSOAPPart().setContent(SOAPUtility.getDOMSourceFromStream(bit));
        bit.close();
    }
}
