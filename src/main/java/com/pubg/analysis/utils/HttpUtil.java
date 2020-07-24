/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.utils;

import com.pubg.analysis.config.PUBGConfig;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.GZIPInputStream;

/**
 * @author sunpeikai
 * @version HttpUtil, v0.1 2020/7/10 17:22
 * @description
 */
@Slf4j
public class HttpUtil {

    public static final Integer SIZE = 1024;
    public static final Integer OFFSET = 0;
    // 连接超时时间 - 2分钟
    private static final Integer CONNECT_TIMEOUT = 2 * 60 * 1000;
    // 读超时时间 - 30分钟，带宽比较低
    private static final Integer READ_TIMEOUT = 30 * 60 * 1000;

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @param param 请求参数
     * @return 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        try {
            String urlNameString = url + param;
            log.debug("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "application/vnd.api+json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Authorization", "Bearer " +  PUBGConfig.apiKey);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.debug("recv - {}", result);
        } catch (Exception e) {
            log.error("sendGet Exception -> url[{}],param[{}],exception[{}]", url, param, e.getMessage());
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception ex) {
                log.error("close buffer exception -> url[{}],param[{}],exception[{}]", url, param, ex.getMessage());
            }
        }
        return result.toString();
    }

    /**
     * 向指定 URL 发送GET方法的请求
     *
     * @param url   发送请求的 URL
     * @return 所代表远程资源的响应结果
     */
    public static String sendGetGZIP(String url) {
        StringBuilder result = new StringBuilder();
        GZIPInputStream inputStream = null;
        try {
            log.info("sendGet - {}", url);
            URL realUrl = new URL(url);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "application/vnd.api+json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            connection.setReadTimeout(READ_TIMEOUT);
            connection.connect();
            inputStream = new GZIPInputStream(connection.getInputStream());
            int count;
            byte[] data = new byte[SIZE];
            while ((count = inputStream.read(data,OFFSET,SIZE)) != -1) {
                result.append(new String(data,OFFSET,count));
            }
            log.info("received content size - {}", result.toString().length());
        } catch (Exception e) {
            log.error("sendGet Exception -> url[{}],exception[{}]", url, e.getMessage());
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception ex) {
                log.error("close buffer exception -> url[{}],exception[{}]", url, ex.getMessage());
            }
        }
        return result.toString();
    }
}
