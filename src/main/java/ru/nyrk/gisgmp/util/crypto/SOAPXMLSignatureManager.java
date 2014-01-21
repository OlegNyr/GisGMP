package ru.nyrk.gisgmp.util.crypto;

import org.apache.ws.security.message.WSSecHeader;
import org.apache.ws.security.message.token.X509Security;
import org.apache.xml.security.transforms.Transforms;
import org.apache.xpath.XPathAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.CryptoPro.JCP.JCP;
import ru.nyrk.gisgmp.core.SignatureManager;
import ru.nyrk.gisgmp.util.smev.SMEVFactory;
import ru.nyrk.gisgmp.util.soap.SOAPUtility;
import ru.nyrk.util.XMLMyUtil;

import javax.annotation.PostConstruct;
import javax.xml.crypto.KeySelector;
import javax.xml.crypto.XMLStructure;
import javax.xml.crypto.dsig.*;
import javax.xml.crypto.dsig.dom.DOMSignContext;
import javax.xml.crypto.dsig.dom.DOMValidateContext;
import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import javax.xml.crypto.dsig.keyinfo.KeyInfoFactory;
import javax.xml.crypto.dsig.keyinfo.X509Data;
import javax.xml.crypto.dsig.spec.C14NMethodParameterSpec;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class SOAPXMLSignatureManager implements SignatureManager {
    private static final Logger log = LoggerFactory.getLogger(SOAPXMLSignatureManager.class);
    private Object[] samData = null;
    private Provider xmlDSigProvider = null;

    PrivateKey privateKey;
    X509Certificate cert;


    @Value("${cryptoPro.keystore.type}")
    String storeType;
    @Value("${cryptoPro.keystore.alias}")
    String alias;
    @Value("${cryptoPro.keystore.password}")
    String storePassword;
    @Autowired
    private SMEVFactory factory;


    public SOAPXMLSignatureManager() throws Exception {

    }


    @PostConstruct
    protected void init() throws Exception {
        ru.CryptoPro.JCPxml.XmlInit.init();
        com.sun.org.apache.xml.internal.security.Init.init();
        SpecUtility.initJCP();


        xmlDSigProvider = new ru.CryptoPro.JCPxml.dsig.internal.dom.XMLDSigRI();
        // Create objects to sign and verify SOAP XML messages
        setSAMdata(storePassword.toCharArray(), alias, storePassword.toCharArray());

    }

    private void setSAMdata(char[] keyStorePass, String alias, char[] aliasKeyRecoveryPass)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            IOException, UnrecoverableKeyException, InvalidAlgorithmParameterException {
        // Инициализация ключевого контейнера.

        KeyStore keyStore = KeyStore.getInstance(JCP.HD_STORE_NAME);
        keyStore.load(null, null);

        // Получение ключа и сертификата.
        privateKey = (PrivateKey) keyStore.getKey(alias, storePassword.toCharArray());
        cert = (X509Certificate) keyStore.getCertificate(alias);
    }

    public void print(String str, SOAPMessage message) throws SOAPException {
        if (log.isDebugEnabled()) {
            Document dc = message.getSOAPPart().getEnvelope().getOwnerDocument();
            String messageStr = org.apache.ws.security.util.XMLUtils.PrettyDocumentToString(dc);
            log.debug(str + "\n{}", XMLMyUtil.formatXml(messageStr.getBytes()));
        }
    }


    public void signElementByTag(SOAPMessage soapMessage, String tag) throws Exception {
        SOAPUtility.refreshSoap(soapMessage);

        //Формируем новый документ из части сообщения
        NodeList tagNodeList = soapMessage.getSOAPPart().getElementsByTagName(tag);
        Document newXMLDocument = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
        Node copyNode = newXMLDocument.importNode(tagNodeList.item(0), true);
        newXMLDocument.appendChild(copyNode);

        //Подписываемый элемент
        NodeList newNodeList = newXMLDocument.getElementsByTagName(tag);
        Element signedNode = (Element) newNodeList.item(0);

        Provider xmlDSigProvider = new ru.CryptoPro.JCPxml.dsig.internal.dom.XMLDSigRI();

        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", xmlDSigProvider);

        List<Transform> transformList = new ArrayList<Transform>();

        Transform transform = fac.newTransform(Transform.ENVELOPED, (XMLStructure) null);
        Transform transformC14N = fac.newTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS, (XMLStructure) null);
        transformList.add(transform);
        transformList.add(transformC14N);

        Reference ref = fac.newReference("", fac.newDigestMethod("http://www.w3.org/2001/04/xmldsig-more#gostr3411", null), transformList, null, null);

        SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,
                (C14NMethodParameterSpec) null),
                fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411", null),
                Collections.singletonList(ref));

        KeyInfoFactory kif = fac.getKeyInfoFactory();
        X509Data x509d = kif.newX509Data(Collections.singletonList(cert));
        KeyInfo ki = kif.newKeyInfo(Collections.singletonList(x509d));

        javax.xml.crypto.dsig.XMLSignature sig = fac.newXMLSignature(si, ki);

        DOMSignContext signContext = new DOMSignContext(privateKey, signedNode);
        signContext.putNamespacePrefix(javax.xml.crypto.dsig.XMLSignature.XMLNS, "ds");
        sig.sign(signContext);

        //Заменяем исходный элемент на подписанный
        Document doc = soapMessage.getSOAPPart().getEnvelope().getOwnerDocument();
        Node signedTag = newXMLDocument.getFirstChild();
        Node oldNode = tagNodeList.item(0);
        Node parentNode = oldNode.getParentNode();
        parentNode.removeChild(oldNode);
        Node newNode = doc.importNode(signedTag, true);
        parentNode.appendChild(newNode);

    }

    public void constructSecuredMessage(SOAPMessage message) throws Exception {

        if (message == null)
            return;
        // Строим заголовок
        {
            message.getSOAPPart().getEnvelope().addNamespaceDeclaration("wsse", Const.SCHEMA_WSSE);
            message.getSOAPPart().getEnvelope().addNamespaceDeclaration("wsu", Const.SCHEMA_WSU);
            message.getSOAPPart().getEnvelope().addNamespaceDeclaration("ds", Const.SCHEMA_DS);
            message.getSOAPBody().setAttributeNS(Const.SCHEMA_WSU, "wsu:Id", "body");

            // Формируем заголовок.
            WSSecHeader header = new WSSecHeader();
            header.setActor("http://smev.gosuslugi.ru/actors/smev");
            header.setMustUnderstand(false);
            header.insertSecurityHeader(message.getSOAPPart().getEnvelope().getOwnerDocument());
            Element sec = header.insertSecurityHeader(message.getSOAPPart());

            Document doc = message.getSOAPPart().getEnvelope().getOwnerDocument();

            Element tokenSec = (Element) sec.appendChild(doc.createElementNS(Const.SCHEMA_WSSE, "wsse:BinarySecurityToken"));
            tokenSec.setAttribute("EncodingType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary");
            tokenSec.setAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
            tokenSec.setAttribute("wsu:Id", "SenderCertificate");
            Element token = header.getSecurityHeader();
            token.appendChild(tokenSec);
        }

        SOAPUtility.refreshSoap(message);


        Document doc = message.getSOAPPart().getEnvelope().getOwnerDocument();
        // Формируем заголовок.
        WSSecHeader header = new WSSecHeader();
        header.setActor("http://smev.gosuslugi.ru/actors/smev");
        header.setMustUnderstand(false);
        header.insertSecurityHeader(message.getSOAPPart().getEnvelope().getOwnerDocument());

        // Подписываемый элемент.
        Element token = header.getSecurityHeader();


        final Transforms transforms = new Transforms(doc);
        transforms.addTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS);


        DOMSignContext signContext = new DOMSignContext(privateKey, token);
        signContext.putNamespacePrefix(XMLSignature.XMLNS, "ds");
        javax.xml.crypto.dsig.XMLSignature sigBody;
        {
            XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", xmlDSigProvider);

            // Преобразования над блоком SignedInfo
            List<Transform> transformList = new ArrayList<Transform>();
            Transform transformC14N =
                    fac.newTransform(Transforms.TRANSFORM_C14N_EXCL_OMIT_COMMENTS, (XMLStructure) null);
            transformList.add(transformC14N);

            // Ссылка на подписываемые данные.
            Reference ref = fac.newReference("#body",
                    fac.newDigestMethod("http://www.w3.org/2001/04/xmldsig-more#gostr3411", null),
                    transformList, null, null);

            // Блок SignedInfo.
            SignedInfo si = fac.newSignedInfo(fac.newCanonicalizationMethod(CanonicalizationMethod.EXCLUSIVE,
                    (C14NMethodParameterSpec) null),
                    fac.newSignatureMethod("http://www.w3.org/2001/04/xmldsig-more#gostr34102001-gostr3411", null),
                    Collections.singletonList(ref));


            //Блок KeyInfo.
            KeyInfoFactory kif = fac.getKeyInfoFactory();
            X509Data x509d = kif.newX509Data(Collections.singletonList(cert));
            KeyInfo ki = kif.newKeyInfo(Collections.singletonList(x509d));

            sigBody = fac.newXMLSignature(si, ki);
        }
        sigBody.sign(signContext);

        // Блок подписи Signature.
        Element sigE = (Element) XPathAPI.selectSingleNode(signContext.getParent(), "//ds:Signature");
        // Блок данных KeyInfo.
        Node keyE = XPathAPI.selectSingleNode(sigE, "//ds:KeyInfo", sigE);

        Element cerVal = (Element) XPathAPI.selectSingleNode(token, "//*[@wsu:Id='SenderCertificate']");
        cerVal.setTextContent(XPathAPI.selectSingleNode(keyE, "//ds:X509Certificate", keyE).getFirstChild().getNodeValue());

        removeKeyInfo(keyE);
        // Блок KeyInfo содержит указание на проверку подписи с помощью сертификата SenderCertificate.
        Node str = keyE.appendChild(doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                "wsse:SecurityTokenReference"));
        Element strRef = (Element) str.appendChild(doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd",
                "wsse:Reference"));

        strRef.setAttribute("ValueType", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3");
        strRef.setAttribute("URI", "#SenderCertificate");

        header.getSecurityHeader().appendChild(sigE);
    }

    private void removeKeyInfo(Node keyE) throws TransformerException {
        // Удаляем элементы KeyInfo, попавшие в тело документа. Они должны быть только в header.
        keyE.removeChild(XPathAPI.selectSingleNode(keyE, "//ds:X509Data", keyE));

        NodeList chl = keyE.getChildNodes();

        for (int i = 0; i < chl.getLength(); i++) {
            keyE.removeChild(chl.item(i));
        }
    }


    public void testSignCert(Document doc) throws Exception {
        // Получение блока, содержащего сертификат.
        final Element wssecontext = doc.createElementNS(null, "namespaceContext");
        wssecontext.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + "wsse".trim(),
                "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
        NodeList secnodeList = XPathAPI.selectNodeList(doc.getDocumentElement(), "//wsse:Security");

        // Поиск элемента сертификата.
        Element r = null;
        Element el = null;
        if (secnodeList != null && secnodeList.getLength() > 0) {

            String actorAttr = null;

            for (int i = 0; i < secnodeList.getLength(); i++) {

                el = (Element) secnodeList.item(i);
                actorAttr = el.getAttributeNS("http://schemas.xmlsoap.org/soap/envelope/", "actor");

                if (actorAttr != null && actorAttr.equals("http://smev.gosuslugi.ru/actors/smev")) {

                    r = (Element) XPathAPI.selectSingleNode(el, "//wsse:BinarySecurityToken[1]", wssecontext);
                    break;
                }
            }
        }

        if (r == null) {
            return;
        }

        // Получение сертификата.
        final X509Security x509 = new X509Security(r);

        X509Certificate cert = (X509Certificate) CertificateFactory.getInstance("X.509")
                .generateCertificate(new ByteArrayInputStream(x509.getToken()));

        if (cert == null) {
            throw new Exception("Сертификат не найден.");
        }

        System.out.println("Verify by: " + cert.getSerialNumber().toString() + cert.getSubjectDN());

        // Поиск элемента с подписью.
        NodeList nl = doc.getElementsByTagNameNS("http://www.w3.org/2000/09/xmldsig#", "Signature");
        if (nl.getLength() == 0) {
            throw new Exception("Не найден элемент Signature.");
        }

        // Задаем открытый ключ для проверки подписи.
        XMLSignatureFactory fac = XMLSignatureFactory.getInstance("DOM", xmlDSigProvider);
        DOMValidateContext valContext = new DOMValidateContext(KeySelector.singletonKeySelector(cert.getPublicKey()), nl.item(0));
        javax.xml.crypto.dsig.XMLSignature signature = fac.unmarshalXMLSignature(valContext);

        // Проверяем подпись.
        System.out.println("Verified locally: " + signature.validate(valContext));

    }

}
