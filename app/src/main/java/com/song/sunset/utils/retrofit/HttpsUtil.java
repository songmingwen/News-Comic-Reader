package com.song.sunset.utils.retrofit;


import android.text.TextUtils;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

public class HttpsUtil {

    /*static {

        HttpsURLConnection.setDefaultHostnameVerifier(new UnSafeHostnameVerifier());
    }*/

    public static class HttpsParam {

        public SSLSocketFactory sslSocketFactory;

        public HostnameVerifier hostnameVerifier;

        public TrustManager trustManager;
    }

    private HttpsUtil() {
    }

    public static HttpsParam createUnsafeSslSockectFactory() {
        return createSslSocketFactory(null, null, null, new UnSafeHostnameVerifier());
    }

    public static SSLSocketFactory createDefaultSSLSocketFactory() {

        HttpsParam param = createUnsafeSslSockectFactory();

        return param.sslSocketFactory;
    }

    public static HttpsParam createSslSocketFactory(InputStream[] certificates, InputStream bksFile, String password, HostnameVerifier hostnameVerifier) {

        HttpsParam httpsParam = new HttpsParam();
        try {
            TrustManager[] trustManagers = prepareTrustManager(certificates);

            KeyManager[] keyManagers = prepareKeyManager(bksFile, password);

            //TLS是最新的IETF制定了标准的安全协议，相当于SSL 3.0后的版本。
            SSLContext sslContext = SSLContext.getInstance("TLS");
            X509TrustManager trustManager = null;

            if (trustManagers != null) {
                trustManager = new MyTrustManager(chooseTrustManager(trustManagers));
            } else {
                trustManager = new UnSafeTrustManager();
            }

            sslContext.init(keyManagers, new TrustManager[]{trustManager}, new SecureRandom());

            httpsParam.sslSocketFactory = sslContext.getSocketFactory();
            httpsParam.trustManager = trustManager;
            httpsParam.hostnameVerifier = hostnameVerifier;
            return httpsParam;
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        } catch (KeyManagementException e) {
            throw new AssertionError(e);
        } catch (KeyStoreException e) {
            throw new AssertionError(e);
        }
    }

    private static TrustManager[] prepareTrustManager(InputStream... certificates) {

        if (certificates == null || certificates.length <= 0) {
            return null;
        }
        try {

            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            TrustManagerFactory trustManagerFactory = null;

            trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            return trustManagers;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password) {

        if (bksFile == null || password == null) {
            return null;
        }
        try {

            KeyStore clientKeyStore = KeyStore.getInstance("BKS");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {

            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }

    private static class MyTrustManager implements X509TrustManager {

        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {

            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(trustManagerFactory.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }


        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }


    private static class UnSafeTrustManager implements X509TrustManager {

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType)
                throws CertificateException {

            if (chain == null || chain.length == 0 || TextUtils.isEmpty(authType)) {
                throw new IllegalArgumentException("illegal argument.");
            } else {
                try {
                    for (X509Certificate certificate : chain) {
                        certificate.checkValidity();
                        //CertificateExpiredException, CertificateNotYetValidException
                    }
                } catch (java.security.cert.CertificateException e) {
                    throw new java.security.cert.CertificateException("Certificate not valid or trusted.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new java.security.cert.X509Certificate[]{};
        }
    }

    public static class UnSafeHostnameVerifier implements HostnameVerifier {

        @Override
        public boolean verify(String hostname, SSLSession session) {
            return false;
        }
    }
}