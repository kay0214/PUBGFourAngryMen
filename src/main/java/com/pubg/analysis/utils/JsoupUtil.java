package com.pubg.analysis.utils;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

/**
 * jsoup工具类
 *
 * @author yangy
 * @date 2020/7/10 16:36
 */
@Slf4j
public class JsoupUtil {

	private static final String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; zh-CN; rv:1.9.2.15)";

	/**
	 * 获取通用连接
	 *
	 * @param url url
	 * @return jsoup 连接
	 */
	public static Connection getBaseConnection(String url) throws Exception {

		return Jsoup.connect(url)
				.postDataCharset("UTF-8")
				.ignoreContentType(true)
				.userAgent(DEFAULT_USER_AGENT)
				.maxBodySize(Integer.MAX_VALUE)
				.timeout(20000);

	}

	/**
	 * 获取链接返回字符串
	 * 超时重试
	 *
	 * @param connection jsoup连接
	 * @return 内容body
	 */
	public static String getStringByConnection(Connection connection) {

		String result = "";

		try {
			Connection.Response response = connection.execute();
			result = response.body();

		} catch (Exception e) {
			log.error("get响应失败", e);
		}

		return result;
	}
}
