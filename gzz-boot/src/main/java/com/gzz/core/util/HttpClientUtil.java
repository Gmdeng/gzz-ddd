package com.gzz.core.util;

import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.*;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;

import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.config.SocketConfig;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

/**
 *
 */
@Slf4j
public class HttpClientUtil {
    private static CloseableHttpClient httpClient = null;

    /**
     * 初始化实例
     */
    static {
        // SSL套接字连接工厂,NoopHostnameVerifier为信任所有服务器
        SSLContext sslContext= null;
        try {
            //SSLContext context = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            //SSLContext context = SSLContext.getInstance("TLS");

            sslContext = SSLContexts.createSystemDefault();
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            };
            sslContext.init(null, new TrustManager[] {tm}, null);
        }catch(IllegalStateException ille) {
            log.error("SSL上下文创建失败，由于" + ille.getLocalizedMessage());
            ille.printStackTrace();
        }catch(Exception  e) {
            log.error("SSL" + e.getMessage());
        }
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", new SSLConnectionSocketFactory( sslContext, NoopHostnameVerifier.INSTANCE))
                .build();
        //
        SocketConfig config = SocketConfig.custom()
                .setSoTimeout(10000) // 设置传输毫秒数
                .build();
        //首先实例化一个连接池管理器，设置最大连接数、并发连接数
        PoolingHttpClientConnectionManager poolMgr = new PoolingHttpClientConnectionManager(registry);
        poolMgr.setMaxTotal(200); // 最大连接数
        poolMgr.setDefaultMaxPerRoute(20); // 设置每个路由的基础连接数
        poolMgr.setValidateAfterInactivity(30000); // 设置重用连接时间
        poolMgr.setDefaultSocketConfig(config);

        // 请求配置信息
        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.STANDARD)
                // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)
                // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)
                // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)
                // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(true).build();


        httpClient = HttpClients.custom()
                .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
                .setRedirectStrategy(new DefaultRedirectStrategy())
                .setConnectionManager(poolMgr)
                .setDefaultRequestConfig(requestConfig)
                .setDefaultCookieStore(new BasicCookieStore())
                .setRetryHandler((exception, executionCount, context) -> {
                    // 实现了HttpRequestRetryHandler接口的
                    // public boolean retryRequest(IOException exception,
                    // int executionCount, HttpContext context)方法
                    if (executionCount >= 3) return false;
                    if (exception instanceof NoHttpResponseException) // 如果服务器断掉了连接那么重试
                        return true;
                    if (exception instanceof SSLHandshakeException)// 不重试握手异常
                        return false;
                    if (exception instanceof InterruptedIOException)// IO传输中断重试
                        return true;
                    if (exception instanceof UnknownHostException)// 未知服务器
                        return false;
                    if (exception instanceof ConnectTimeoutException)// 超时就重试
                        return true;
                    if (exception instanceof SSLException) return false;

                    HttpClientContext cliContext = HttpClientContext.adapt(context);
                    HttpRequest request = cliContext.getRequest();
                    if (!(request instanceof HttpEntityEnclosingRequest))
                        return true;
                    return false;
                })
                .build();
        if (poolMgr != null && poolMgr.getTotalStats() != null)
            log.info("客户池状态：" + poolMgr.getTotalStats().toString());
    }


    /**
     * 处理HTTP请求返回信息
     *
     * @param method
     * @return 结果字符串
     */
    private static String HandleResult(HttpRequestBase method) {
        HttpClientContext context = HttpClientContext.create();
        String content = "";
        // 标准Cookie策略
        // RequestConfig requestConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // method.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        // 发送请求，并执行
        try {
            response = httpClient.execute(method, context); // 执行GET/POST请求
            int statusCode = response.getStatusLine().getStatusCode();
            // 打印响应状态
            log.debug("StatusLine:" + response.getStatusLine());
            if ((statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
                    || (statusCode == HttpStatus.SC_MOVED_TEMPORARILY)
                    || (statusCode == HttpStatus.SC_SEE_OTHER)
                    || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                // 此处重定向处理 此处还未验证
                String newUri = response.getLastHeader("Location").getValue();
                HttpGet httpGet = new HttpGet(newUri);
                response = httpClient.execute(httpGet,context);
            }
            // 获取响应实体
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                log.debug("ContentType:" + entity.getContentType());
                Charset charset = ContentType.getOrDefault(entity).getCharset();
                charset = charset== null? Charset.forName("UTF-8"): charset;
                content = EntityUtils.toString(entity, charset);
            }
        } catch (ConnectTimeoutException cte) {
            log.error("请求连接超时，由于 " + cte.getLocalizedMessage());
            cte.printStackTrace();
        } catch (SocketTimeoutException ste) {
            log.error("请求通信超时，由于 " + ste.getLocalizedMessage());
            ste.printStackTrace();
        } catch (ClientProtocolException cpe) {
            log.error("协议错误（比如构造HttpGet对象时传入协议不对(将'http'写成'htp')or响应内容不符合），由于 " + cpe.getLocalizedMessage());
            cpe.printStackTrace();
        } catch (IOException ie) {
            log.error("实体转换异常或者网络异常， 由于 " + ie.getLocalizedMessage());
            ie.printStackTrace();
        } finally {
            try {
                if (response != null)
                    response.close();
            } catch (IOException e) {
                log.error("响应关闭异常， 由于 " + e.getLocalizedMessage());
            }
            // 自动释放连接
            if (method != null)
                method.releaseConnection();
        }
        log.info("响应信息：{}", content);
        return content;
    }

    /**
     * 发送 post请求
     *
     * @param url
     * @param params
     *            参数
     * @return
     */
    public static String doPost(Map<String, String> headers,String url, Map<String, Object> params) {
        // 创建httppost
        HttpPost httpPost = new HttpPost(url);
        log.info("请求地址URI==>" + httpPost.getURI().toString());
        // 添加头部参数
        if (headers != null){
            for (Map.Entry<String, String> e : headers.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
        }
        // 创建参数队列
        List<NameValuePair> formparams = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
        }

        // 发送请求，并执行
        UrlEncodedFormEntity uefEntity;
        try {
            // 构造一个form表单式的实体
            uefEntity = new UrlEncodedFormEntity(formparams, StandardCharsets.UTF_8);
            // 将请求实体设置到httpPost对象中
            httpPost.setEntity(uefEntity);
        } catch (ParseException e) {
            log.error("解析异常:" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            log.error("IO异常:" + e.getMessage());
            e.printStackTrace();
        }

        return HandleResult(httpPost);
    }
    public static String doPost(String url, Map<String, Object> params) {
        return doPost(null, url, params);
    }

    /**
     * 发送 post请求
     *
     * @param url
     * @param params
     *            参数 字符串 eg: age=90&name=小明
     * @return
     */
    public static String doPost(Map<String, String> headers, String url, String params) {
        HttpPost httpPost = new HttpPost(url);
        log.info("请求地址URI==>" + httpPost.getURI().toString());
        // 添加头部参数
        if (headers != null){
            for (Map.Entry<String, String> e : headers.entrySet()) {
                httpPost.addHeader(e.getKey(), e.getValue());
            }
        }
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(params, StandardCharsets.UTF_8);
            //stringEntity.setContentEncoding("UTF-8");
            // stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return HandleResult(httpPost);
    }
    public static String doPost(String url, String params) {
        return doPost(null ,url, params);
    }


    /**
     * 发送 get请求
     *
     * @param url
     * @param params
     * @return
     */
    public static String doGet(Map<String, String> headers,String url, Map<String, Object> params) {
        HttpGet httpGet = new HttpGet(url);
        // 添加头部参数
        if (headers != null){
            for (Map.Entry<String, String> e : headers.entrySet()) {
                httpGet.addHeader(e.getKey(), e.getValue());
            }
        }
        // 添加参数
        if (params != null && params.size() > 0) {
            // 创建参数队列
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                formparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }
            // 发送请求，并执行
            UrlEncodedFormEntity uefEntity;
            try {
                // 构造一个form表单式的实体
                uefEntity = new UrlEncodedFormEntity(formparams, StandardCharsets.UTF_8);
                String str = EntityUtils.toString(uefEntity);
                httpGet.setURI(new URI(httpGet.getURI().toString() + "?" + str));
            } catch (UnsupportedEncodingException e) {
                log.error("不支持编码异常:" + e.getMessage());
                e.printStackTrace();
            } catch (ParseException e) {
                log.error("解析异常:" + e.getMessage());
                e.printStackTrace();
            } catch (IOException e) {
                log.error("IO异常:" + e.getMessage());
                e.printStackTrace();
            } catch (URISyntaxException e) {
                log.error("URI语法异常:" + e.getMessage());
                e.printStackTrace();
            }
        }
        log.info("请求地址URI==>" + httpGet.getURI().toString());
        return HandleResult(httpGet);
    }

    public static String doGet(String url) {
        return doGet(url, null);
    }
    public static String doGet(String url, Map<String, Object> params) {
        return doGet(null, url, params);
    }

}
