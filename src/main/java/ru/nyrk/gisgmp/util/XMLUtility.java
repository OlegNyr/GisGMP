package ru.nyrk.gisgmp.util;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class XMLUtility {
    static DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

    public static Document createDocument(InputStream inputStream) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.parse(inputStream);
    }

    public static Document createDocument() throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        return dBuilder.newDocument();
    }

    public static void saveDocument(Document document, OutputStream outputStream) {
        org.apache.ws.security.util.XMLUtils.ElementToStream(document.getDocumentElement(), outputStream);
    }

}
