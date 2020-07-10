package com.pubg.analysis.service;

import java.util.List;

/**
 * pubg api接口调用封装
 *
 * @author yangy
 * @date 2020/7/10 16:02
 */
public interface IPubgApiService {

	/**
	 * 取得玩家比赛
	 *
	 * @param nameList 玩家列表
	 * @return 比赛id列表
	 */
	List<String> getMatchesByPlayerNames(List<String> nameList);

	/**
	 * 获取遥测文件url
	 *
	 * @param matchId 比赛id
	 * @return json文件url
	 */
	String getMatchTelemetryUrl(String matchId);
}
