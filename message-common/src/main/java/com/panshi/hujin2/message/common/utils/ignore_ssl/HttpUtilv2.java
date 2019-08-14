package com.panshi.hujin2.message.common.utils.ignore_ssl;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author shenJianKang
 * @date 2019/8/9 16:58
 *
 *
 * 忽略证书
 */

@Component
public class HttpUtilv2 {
    private final Logger LOGGER = LoggerFactory.getLogger(getClass());

//    @Autowired(required = false)
//    private ProxyConfig proxyConfig;

    private String host = "127.0.0.1";
    private Integer port = 8118;

    public BasicHttpResponse executeHttpsGet(String url) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, URISyntaxException {

        BasicHttpResponse returnRes = new BasicHttpResponse();
        CloseableHttpClient httpclient = null;
        try{
            // TODO 测试，暂时写死
//            HttpHost proxyConfig = new HttpHost("127.0.0.1", 9666);

//            SSLContext sslContext = null;
//            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                @Override
//                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
//                    return true;
//                }
//            }).build();
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);//这里的红色部分
//
//            httpclient = HttpClients.custom().setProxy(proxyConfig).setSSLSocketFactory(sslsf).build();

//            HttpHost proxy = new HttpHost(proxyConfig.getProxyHost(), proxyConfig.getProxyPort());
            httpclient = (CloseableHttpClient) getHttpClient();

            //1.create request method
            URI requestUri = new URI(url);
            LOGGER.info("************* execute get http  request url={}***********", requestUri);
            HttpGet get = new HttpGet(requestUri);

            CloseableHttpResponse response = httpclient.execute(get);
            returnRes.setStatusCode(response.getStatusLine().getStatusCode());
            HttpEntity responseEntity = response.getEntity();

            String reponseContentString = EntityUtils.toString(responseEntity);
            returnRes.setHtmlContent(reponseContentString);

            //log.info("******response = {}", reponseContentString);
            return returnRes;
        }finally {
            HttpClientUtils.closeQuietly(httpclient);
        }
    }

    public String executeHttpsGetCall(String url) throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException, URISyntaxException {

        CloseableHttpClient httpclient = null;
        try{
            HttpHost proxy = new HttpHost("127.0.0.1", 9666);

            SSLContext sslContext = null;
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);//这里的红色部分

            httpclient = HttpClients.custom().setProxy(proxy).setSSLSocketFactory(sslsf).build();

//            HttpHost proxy = new HttpHost(proxyConfig.getProxyHost(), proxyConfig.getProxyPort());
//            httpclient = (CloseableHttpClient) getHttpClient();

            //1.create request method
            URI requestUri = new URI(url);
            LOGGER.info("************* execute get http  request url={}***********", requestUri);
            HttpGet get = new HttpGet(requestUri);

            CloseableHttpResponse response = httpclient.execute(get);

            HttpEntity responseEntity = response.getEntity();

            String reponseContentString = EntityUtils.toString(responseEntity);

            //log.info("******response = {}", reponseContentString);
            return reponseContentString;
        }finally {
            HttpClientUtils.closeQuietly(httpclient);
        }
    }



    /**
     * 生成httpclient
     *
     * @return
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private HttpClient getHttpClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        String activeProfiles = MySpringContextUtil.getActiveProfile();

        if (activeProfiles.equals("dev") || activeProfiles.equals("testina")|| activeProfiles.equals("testvi")) {
            HttpHost proxy = new HttpHost(host, port);

            SSLContext sslContext = null;
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);//这里的红色部分

            CloseableHttpClient httpclient = HttpClients.custom().setProxy(proxy).setSSLSocketFactory(sslsf).build();
            return httpclient;
        } else {
            SSLContext sslContext = null;
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);//这里的红色部分

            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
            return httpclient;
        }
    }
}
