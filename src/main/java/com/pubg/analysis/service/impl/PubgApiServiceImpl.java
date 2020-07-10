package com.pubg.analysis.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.service.IPubgApiService;
import com.pubg.analysis.utils.JsoupUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * api接口封装实现
 *
 * @author yangy
 * @date 2020/7/10 16:04
 */
@Service
@PropertySource(value = {"classpath:api-key"})
@Slf4j
public class PubgApiServiceImpl implements IPubgApiService {

	private static final String PLAYER_API = "https://api.pubg.com/shards/steam/players";
	private static final String MATCH_INFO_API = "https://api.pubg.com/shards/steam/matches";

	@Value("${api-key}")
	private String apiKey;

	@Override
	public List<String> getMatchesByPlayerNames(List<String> nameList) {

		log.info("获取比赛id: {}", nameList);
		List<String> result = new ArrayList<>();
		if (nameList == null || nameList.isEmpty()) {
			return result;
		}

		try {
			Connection connection = JsoupUtil
					.getBaseConnection(PLAYER_API)
					.header("Accept", "application/vnd.api+json")
					.header("Authorization", "Bearer " + apiKey)
					.data("filter[playerNames]", StringUtils.join(nameList, ","));

			String raw = JsoupUtil.getStringByConnection(connection);
			JSONObject json = JSON.parseObject(raw);
			JSONArray data = json.getJSONArray("data");
			result = data
					.stream()
					.map(p -> ((JSONObject) p)
							.getJSONObject("relationships")
							.getJSONObject("matches")
							.getJSONArray("data")
					)
					.flatMap(List::stream)
					.map(m -> (JSONObject) m)
					.filter(m -> "match".equals(m.getString("type")))
					.map(m -> m.getString("id"))
					.distinct()
					.collect(Collectors.toList());

		} catch (Exception e) {
			log.error("数据请求失败", e);
		}

		return result;
	}

	@Override
	public String getMatchTelemetryUrl(String matchId) {

		log.info("获取遥测地址: {}", matchId);
		String result = "";
		if (StringUtils.isEmpty(matchId)) {
			return result;
		}

		try {
			Connection connection = JsoupUtil
					.getBaseConnection(MATCH_INFO_API + "/" + matchId)
					.header("Accept", "application/vnd.api+json");
			String raw = JsoupUtil.getStringByConnection(connection);
			JSONObject json = JSON.parseObject(raw);
			List<String> assetIdList = json
					.getJSONObject("data")
					.getJSONObject("relationships")
					.getJSONObject("assets")
					.getJSONArray("data")
					.parallelStream()
					.map(d -> (JSONObject) d)
					.filter(e -> "asset".equals(e.getString("type")) &&
							StringUtils.isNotEmpty(e.getString("id")))
					.map(e -> e.getString("id"))
					.collect(Collectors.toList());
			log.debug("取得asset列表: {}", assetIdList);

			if (!assetIdList.isEmpty()) {
				JSONArray included = json.getJSONArray("included");
				Optional<String> url = included
						.parallelStream()
						.map(e -> (JSONObject) e)
						.filter(e -> "asset".equals(e.getString("type")))
						.map(e -> e.getJSONObject("attributes"))
						.filter(e -> "telemetry".equals(e.getString("name")))
						.map(e -> e.getString("URL"))
						.findAny();
				if (url.isPresent()) {
					result = url.get();
				}

			}

		} catch (Exception e) {
			log.error("获取比赛遥测错误", e);
		}

		return result;
	}
}
