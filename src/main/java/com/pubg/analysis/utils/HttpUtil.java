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

/**
 * @author sunpeikai
 * @version HttpUtil, v0.1 2020/7/10 17:22
 * @description
 */
@Slf4j
public class HttpUtil {

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
            log.info("sendGet - {}", urlNameString);
            URL realUrl = new URL(urlNameString);
            URLConnection connection = realUrl.openConnection();
            connection.setRequestProperty("accept", "application/vnd.api+json");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            connection.setRequestProperty("Authorization", "Bearer " +  PUBGConfig.apiKey);
            connection.connect();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
            log.info("recv - {}", result);
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
}
