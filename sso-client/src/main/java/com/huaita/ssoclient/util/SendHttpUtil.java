package com.huaita.ssoclient.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * 〈一句话功能简述〉<br>
 * 通过Http发送xml报文
 * 
 * @author 13040555
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class SendHttpUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(SendHttpUtil.class);

    private SendHttpUtil() {
    }

    /**
     * 
     * 功能描述: <br>
     * 〈功能详细描述〉
     * 
     * @param url http地址
     * @param content 报文
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String send(String url, String content){
        return HttpClientTemplate.postMsgStream(url, content);
    }

    /**
     * 
     * 功能描述: <br>
     * 发送xml以URLConnection的方式
     * 
     * @param url
     * @param content
     * @return
     * @throws Exception
     * @see [相关类/方法](可选)
     * @since [产品/模块版本](可选)
     */
    public static String sendWithURLConnection(String url, String content) {
        URL u = null;
        HttpURLConnection con = null;
        int maxTryCount = 10;
        int tryCount = 0;
        int leftTryCount = 0;
        String errMsg = null;
        while (tryCount < maxTryCount) {
            // 构建请求参数
            try {
                StringBuilder buffer = new StringBuilder();
                u = new URL(url);
                con = (HttpURLConnection) u.openConnection();
                // 超时时间0.5s
                con.setConnectTimeout(5000);
                con.setRequestMethod("POST");
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setUseCaches(false);
                con.setRequestProperty("Content-Type", "application/xml");
                con.connect();
                OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream(), "UTF-8");
                osw.write(content);
                osw.flush();
                osw.close();
                long begin = System.currentTimeMillis();
                InputStreamReader inputStream = new InputStreamReader(con.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(inputStream);
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    buffer.append(temp.trim());
                }
                inputStream.close();
                br.close();
                long end = System.currentTimeMillis();
                LOGGER.debug("cost:" + (end - begin) + "ms, url:" + url);
                return buffer.toString();
            } catch (Exception e) {
                LOGGER.debug("SendHttpUtil-sendWithURLConnection", e);
            } finally {
                if (con != null) {
                    con.disconnect();
                }
            }
            tryCount++;
            leftTryCount = maxTryCount - tryCount;

            if (leftTryCount == 0) {
                LOGGER.error("发送请求报文报错, 尝试次数：" + tryCount + ", 错误原因:" + errMsg + ", 请求地址:" + url + ", 报文:" + content);
            } else {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    LOGGER.debug("SendHttpUtil-sendWithURLConnection-sleep", ex);
                }
            }
        }
        return null;
    }
}