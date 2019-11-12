package com.huaita.ssoclient.util;

import com.huaita.ssoclient.exception.BaseException;
import com.huaita.ssoclient.model.OmsqConstants;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 
 * 向服务端post xml流
 * 
 * @author 13040555
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class HttpClientTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientTemplate.class);

    private static final String DEFAULT_CONTENT_ENCODING = "UTF-8";

    /** 线程安全的存放HttpClient的map */
    private static ConcurrentMap<String, DefaultHttpClient> httpClientMap = new ConcurrentHashMap<String, DefaultHttpClient>();

    /**
     * 线程安全的HttpClient连接管理器
     */
    private static ThreadSafeClientConnManager connectionManager = null;

    private HttpClientTemplate() {
    }

    /**
     * 初始化httpclient的连接管理器
     */
    static {
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
        schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
        connectionManager = new ThreadSafeClientConnManager(schemeRegistry);
        connectionManager.setMaxTotal(OmsqConstants.OmsqNumberEnum.MAXTOTALCONN.toValue());
        // 每条通道的并发连接数设置（连接池）
        connectionManager.setDefaultMaxPerRoute(OmsqConstants.OmsqNumberEnum.MAXCONNPERROUTE.toValue());
    }

    /**
     * 以流的方式向服务端post Xml字符串
     * 
     * @throws RscException
     */
    public static String postMsgStream(String url, String msg, String encode, int timeOut, int soTimeOut) {
        String uid = "";
        if (StringUtils.isNotBlank(msg)) {
            uid = msg.substring(msg.indexOf("<UId>"), msg.indexOf("</UId>") + 6);
        }
        String realEncode = DEFAULT_CONTENT_ENCODING;
        if (StringUtils.isNotBlank(encode)) {
            realEncode = encode;
        }
        StringBuilder result = new StringBuilder();
        HttpClient httpclient = getHttpClient(url, timeOut, soTimeOut);
        HttpPost httppost = new HttpPost(url);
        HttpResponse response = null;
        try {
            StringEntity myEntity = new StringEntity(msg, realEncode);
            httppost.addHeader("Content-Type", "text/xml;charset=utf-8");
            httppost.setEntity(myEntity);
            response = httpclient.execute(httppost);
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                InputStreamReader reader = new InputStreamReader(resEntity.getContent(), realEncode);
                char[] buff = new char[1024];
                int length = 0;
                while ((length = reader.read(buff)) != -1) {
                    result.append(buff, 0, length);
                }
                reader.close();
            }
        } catch (Exception e) {
            // 释放HttpClient连接
            httppost.abort();
            if (SocketTimeoutException.class.isInstance(e)) {
                LOGGER.error(uid + ":socket timeout error!", e);
                throw new BaseException("socket timeout!", e);
            } else if (ConnectTimeoutException.class.isInstance(e) || ConnectException.class.isInstance(e)) {
                LOGGER.error(uid + ":connection timeout error!", e);
                throw new BaseException("connection timeout!", e);
            } else {
                LOGGER.error("Client requestXml: [{}] \n Server responseXml: [{}] \n HttpResponse: [{}]", new Object[] {
                        msg, result, response });
                throw new BaseException(e.getMessage(), e);
            }
        }
        LOGGER.debug("[HttpClientTemplate:postMsgStream()]: \n[request={}] \n[response={}]\n send successful!", msg,
                result);
        return result.toString();
    }

    /**
     * 
     * 功能描述: <br>
     * 以流的方式向服务端post字符串
     * 
     * @param url
     * @param msg
     * @param enCode
     * @return
     * @throws RscException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String postMsgStream(String url, String msg, String enCode) throws BaseException{
        return postMsgStream(url, msg, enCode, 0, 0);
    }

    /**
     * 
     * 功能描述: <br>
     * 以流的方式向服务端post字符串
     * 
     * @param url
     * @param msg
     * @return
     * @throws RscException
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String postMsgStream(String url, String msg) throws BaseException {
        return postMsgStream(url, msg, null);
    }

    /**
     * 获取HttpClient,不同的业务场景传入不同的连接超时、请求超时参数
     * 
     * @param url 访问的url
     * @param timeOut 连接超时
     * @param soTimeout 请求超时
     * @return HttpClient
     */
    public static HttpClient getHttpClient(String url, int timeOut, int soTimeout) {
        int realTimeOut = OmsqConstants.OmsqNumberEnum.THOUSAND.toValue();
        if (timeOut != 0) {
            realTimeOut = timeOut;
        }
        int realSoTimeOut = OmsqConstants.OmsqNumberEnum.FIVE_THOUSAND.toValue();
        if (soTimeout != 0) {
            realSoTimeOut = soTimeout;
        }
        StringBuilder sb = new StringBuilder(5);
        sb.append(url).append(".").append(realTimeOut).append(".").append(realSoTimeOut);
        String key = sb.toString();
        DefaultHttpClient httpclient = httpClientMap.get(key);
        if (httpclient == null) {
            httpclient = new DefaultHttpClient(connectionManager);
            HttpParams params = httpclient.getParams();
            params.setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, realTimeOut);
            params.setParameter(CoreConnectionPNames.SO_TIMEOUT, realSoTimeOut);
            DefaultHttpClient tempClient = httpClientMap.putIfAbsent(key, httpclient);
            if (tempClient != null) {
                httpclient = tempClient;
            }
        }
        return httpclient;
    }
}
