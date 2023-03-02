package com.song.sunset.base.net;

import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Date;
import java.util.Set;

import javax.net.ssl.X509TrustManager;

/**
 * Desc:    针对部分手机系统时间不对,导致 sslexception 的解决方案.忽略指定证书的过期时间问题
 * Author:  songmingwen
 * Email:   mingwen@mgtv.com
 * Time:    2023/3/2 14:49
 */
public class IgnoreExpirationTrustManager implements X509TrustManager {

    private final X509TrustManager innerTrustManager;

    public IgnoreExpirationTrustManager(X509TrustManager innerTrustManager) {
        this.innerTrustManager = innerTrustManager;
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.innerTrustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        //图片库使用的网络底层也有类似问题.但是图片来源众多,不能根据cn识别,这里干脆忽略所有证书时间
//        if (chain[0].getSubjectDN().getName().contains("mgtv.com")) {//只要是cn包含mgtv.com的,忽略有效期校验
        chain = Arrays.copyOf(chain, chain.length);
        X509Certificate[] newChain = new X509Certificate[chain.length];
        //替换证书链中所有的.
        for (int i = 0; i < chain.length; i++) {
            newChain[i] = new EternalCertificate(chain[i]);
        }
        //仅替换第一个.但是根证书也存在有效期.所以干脆按照上面的方式全部替换
        //            newChain[0] = new EternalCertificate(chain[0]);
        //            System.arraycopy(chain, 1, newChain, 1, chain.length - 1);
        chain = newChain;
//        }

        try {
            this.innerTrustManager.checkServerTrusted(chain, authType);
//            java.lang.RuntimeException: An error occurred while executing doInBackground()
//            at android.os.AsyncTask$3.done(AsyncTask.java:354)
//            at java.util.concurrent.FutureTask.finishCompletion(FutureTask.java:383)
//            at java.util.concurrent.FutureTask.setException(FutureTask.java:252)
//            at java.util.concurrent.FutureTask.run(FutureTask.java:271)
//            at com.a.a.a.a.a.handleMessage(TaskHandler.java:61)
//            at android.os.Handler.dispatchMessage(Handler.java:106)
//            at android.os.Looper.loop(Looper.java:226)
//            at android.os.HandlerThread.run(HandlerThread.java:65)
//            Caused by: java.lang.AssertionError: throwExceptionFromBoringSSLError called with no error
//            at com.android.org.conscrypt.NativeCrypto.X509_verify(Native Method)
//            at com.android.org.conscrypt.OpenSSLX509Certificate.verifyOpenSSL(OpenSSLX509Certificate.java:379)
//            at com.android.org.conscrypt.OpenSSLX509Certificate.verify(OpenSSLX509Certificate.java:409)
//            at com.mgtv.task.http.IgnoreExpirationTrustManager$EternalCertificate.verify(IgnoreExpirationTrustManager.java:164)
//            at com.android.org.conscrypt.TrustedCertificateIndex.findAllByIssuerAndSignature(TrustedCertificateIndex.java:197)
//            at com.android.org.conscrypt.TrustManagerImpl.checkTrustedRecursive(TrustManagerImpl.java:618)
//            at com.android.org.conscrypt.TrustManagerImpl.checkTrusted(TrustManagerImpl.java:495)
//            at com.android.org.conscrypt.TrustManagerImpl.checkServerTrusted(TrustManagerImpl.java:321)
//            at android.security.net.config.NetworkSecurityTrustManager.checkServerTrusted(NetworkSecurityTrustManager.java:113)
//            at android.security.net.config.NetworkSecurityTrustManager.checkServerTrusted(NetworkSecurityTrustManager.java:87)
//            at android.security.net.config.RootTrustManager.checkServerTrusted(RootTrustManager.java:116)
//            at com.mgtv.task.http.IgnoreExpirationTrustManager.checkServerTrusted(IgnoreExpirationTrustManager.java:56)
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.innerTrustManager.getAcceptedIssuers();
    }

    private class EternalCertificate extends X509Certificate {
        private final X509Certificate originalCertificate;

        public EternalCertificate(X509Certificate originalCertificate) {
            this.originalCertificate = originalCertificate;
        }

        @Override
        public void checkValidity() throws CertificateExpiredException, CertificateNotYetValidException {
            // Ignore notBefore/notAfter
        }

        @Override
        public void checkValidity(Date date) throws CertificateExpiredException, CertificateNotYetValidException {
            // Ignore notBefore/notAfter
        }

        @Override
        public int getVersion() {
            return originalCertificate.getVersion();
        }

        @Override
        public BigInteger getSerialNumber() {
            return originalCertificate.getSerialNumber();
        }

        @Override
        public Principal getIssuerDN() {
            return originalCertificate.getIssuerDN();
        }

        @Override
        public Principal getSubjectDN() {
            return originalCertificate.getSubjectDN();
        }

        @Override
        public Date getNotBefore() {
            return originalCertificate.getNotBefore();
        }

        @Override
        public Date getNotAfter() {
            return originalCertificate.getNotAfter();
        }

        @Override
        public byte[] getTBSCertificate() throws CertificateEncodingException {
            return originalCertificate.getTBSCertificate();
        }

        @Override
        public byte[] getSignature() {
            return originalCertificate.getSignature();
        }

        @Override
        public String getSigAlgName() {
            return originalCertificate.getSigAlgName();
        }

        @Override
        public String getSigAlgOID() {
            return originalCertificate.getSigAlgOID();
        }

        @Override
        public byte[] getSigAlgParams() {
            return originalCertificate.getSigAlgParams();
        }

        @Override
        public boolean[] getIssuerUniqueID() {
            return originalCertificate.getIssuerUniqueID();
        }

        @Override
        public boolean[] getSubjectUniqueID() {
            return originalCertificate.getSubjectUniqueID();
        }

        @Override
        public boolean[] getKeyUsage() {
            return originalCertificate.getKeyUsage();
        }

        @Override
        public int getBasicConstraints() {
            return originalCertificate.getBasicConstraints();
        }

        @Override
        public byte[] getEncoded() throws CertificateEncodingException {
            return originalCertificate.getEncoded();
        }

        @Override
        public void verify(PublicKey key) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException, NoSuchProviderException,
                SignatureException {
            originalCertificate.verify(key);
        }

        @Override
        public void verify(PublicKey key, String sigProvider) throws CertificateException, NoSuchAlgorithmException, InvalidKeyException,
                NoSuchProviderException, SignatureException {
            originalCertificate.verify(key, sigProvider);
        }

        @Override
        public String toString() {
            return originalCertificate.toString();
        }

        @Override
        public PublicKey getPublicKey() {
            return originalCertificate.getPublicKey();
        }

        @Override
        public Set<String> getCriticalExtensionOIDs() {
            return originalCertificate.getCriticalExtensionOIDs();
        }

        @Override
        public byte[] getExtensionValue(String oid) {
            return originalCertificate.getExtensionValue(oid);
        }

        @Override
        public Set<String> getNonCriticalExtensionOIDs() {
            return originalCertificate.getNonCriticalExtensionOIDs();
        }

        @Override
        public boolean hasUnsupportedCriticalExtension() {
            return originalCertificate.hasUnsupportedCriticalExtension();
        }
    }
}
