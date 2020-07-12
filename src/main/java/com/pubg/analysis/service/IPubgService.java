package com.pubg.analysis.service;

/**
 * @author yangy
 * @date 2020/7/12 9:20
 */
public interface IPubgService {

	/**
	 * 取得位置
	 *
	 * @param matchId 比赛id
	 */
	void getPubgLocation(String matchId);
}
