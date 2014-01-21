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

package ru.nyrk.gisgmp.util.crypto;

import ru.CryptoPro.JCPxml.xmldsig.JCPXMLDSigInit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.*;
import java.security.cert.*;
import java.util.*;

/**
 * Class with auxiliary functions.
 */
public class SpecUtility {


    /**
     * Functions initializes XML JCP.
     */
    public static void initJCP() {

        if (!JCPXMLDSigInit.isInitialized())
            JCPXMLDSigInit.init();

        System.setProperty("com.ibm.security.enableCRLDP", "false");
    }

    /**
     * Function loads store information from key store.
     *
     * @param storeType     - type of key store (HDImageStore, JKS, PKCS12).
     * @param store         - store file.
     * @param storePassword - password to store.
     * @return loaded key store.
     * @throws java.security.KeyStoreException
     *
     * @throws java.security.NoSuchAlgorithmException
     *
     * @throws java.security.cert.CertificateException
     *
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
     */
    public static KeyStore loadKeyStore(String storeType, File store, char[] storePassword)
            throws KeyStoreException, NoSuchAlgorithmException, CertificateException,
            FileNotFoundException, IOException {

        KeyStore keyStore = KeyStore.getInstance(storeType);
        FileInputStream inputStream = null;

        if (store != null)
            inputStream = new FileInputStream(store);

        keyStore.load(keyStore == null ? null : inputStream, storePassword);
        return keyStore;
    }

    /**
     * Function verifies the user certificate chain 'certs'.
     *
     * @param crlFile             - CRL file (can be null).
     * @param certs               - list of user certificates to be checked.
     * @param cacerts             - list of trusted certificates.
     * @param endPointCertificate - a certificate as last verifiable link in the chain (can be null).
     * @return true, if chain is valid.
     * @throws java.security.cert.CRLException
     *
     * @throws java.io.FileNotFoundException
     * @throws java.security.SignatureException
     *
     * @throws java.security.NoSuchProviderException
     *
     * @throws java.security.InvalidKeyException
     *
     * @throws java.security.cert.CertPathBuilderException
     *
     */
    public static boolean validateCertPath(File crlFile,
                                           X509Certificate[] certs, X509Certificate[] cacerts,
                                           X509Certificate endPointCertificate) throws
            CRLException, FileNotFoundException, InvalidKeyException,
            NoSuchProviderException, SignatureException, CertPathBuilderException {

        PKIXCertPathValidatorResult bldResult = null;
        List<CertStore> storeCertCRL = null;
        CertStore crlCertStore = null;
        boolean crlIsAdded = false;
        boolean bResult = false;

        try {

            CertificateFactory certFactory = CertificateFactory.getInstance("X509");

            /**
             * Add CRL in list.
             */
            if (crlFile != null) {

                CRL crl = certFactory.generateCRL(new FileInputStream(crlFile));
                if (crl != null)
                    crlCertStore = CertStore.getInstance("Collection", new CollectionCertStoreParameters(Collections.singletonList(crl)));

                if (crlCertStore != null) {
                    storeCertCRL = Arrays.asList(crlCertStore);
                    crlIsAdded = true;
                }
            }

            List<X509Certificate> certList = Arrays.asList(certs);
            CertPath path = certFactory.generateCertPath(certList);
            System.out.println("Certificate path length: " + path.getCertificates().size());

            Set<TrustAnchor> trustedAnchors = new HashSet<TrustAnchor>();

            if (cacerts != null) {
                for (int i = 0; i < cacerts.length; ++i) {
                    TrustAnchor anchor =
                            new TrustAnchor(cacerts[i],
                                    cacerts[i].getExtensionValue("2.5.29.30"));
                    if (anchor != null && !trustedAnchors.contains(anchor))
                        trustedAnchors.add(anchor);
                }
            }

            /* 
            if (certs != null) {
                for ( int i = 0; i < certs.length; ++i ) {
                	TrustAnchor anchor = 
                		new TrustAnchor(certs[i], 
                    		certs[i].getExtensionValue(Merlin.NAME_CONSTRAINTS_OID));
                	if ( anchor != null && !trustedAnchors.contains(anchor) )
                		trustedAnchors.add(anchor);
                }
            }
            */

            System.out.println("Trusted anchor deepth: " + trustedAnchors.size());

            if (certs != null && cacerts != null &&
                    certs.length == 1 && cacerts.length == 1) {
                certs[0].checkValidity();
                certs[0].verify(cacerts[0].getPublicKey());
            }

            PKIXParameters param = new PKIXParameters(trustedAnchors);
            param.setSigProvider(null);
            param.setRevocationEnabled(crlIsAdded);
            if (crlIsAdded)
                param.setCertStores(storeCertCRL);

            if (endPointCertificate != null) {
                final X509CertSelector selector = new X509CertSelector();
                selector.setCertificate(endPointCertificate);
                param.setTargetCertConstraints(selector);
            }

            CertPathValidator certPathValidator = CertPathValidator.getInstance("PKIX");
            bldResult = (PKIXCertPathValidatorResult) certPathValidator.validate(path, param);
            /* MUST BE (bldResult != null)
            PublicKey publicKey = bldResult.getPublicKey();
            */
            bResult = true;

        } catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (CertificateException ex) {
            ex.printStackTrace();
        } catch (InvalidAlgorithmParameterException ex) {
            ex.printStackTrace();
        } catch (CertPathValidatorException ex) {
            ex.printStackTrace();
            System.out.println("Index of certificate that caused exception: "
                    + ex.getIndex());
        }

        System.out.println("Path Validator result (public key algorithm): " +
                ((bldResult != null) ? bldResult.getPublicKey().getAlgorithm() : null));

        return bResult;
    }
}
