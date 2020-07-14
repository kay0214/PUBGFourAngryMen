package com.pubg.analysis.service;

import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.response.PositionResponse;

import java.util.List;

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
    PositionResponse getPubgLocation(String matchId);

    /**
     * @description accountId查找对局列表
     * @auth sunpeikai
     * @param accountId 玩家账户ID
     * @return
     */
    List<Match> findByAccountId(String accountId);

    /**
     * @description accountId查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param accountId 玩家账户ID
     * @return
     */
    List<Match> findByAccountId(boolean fromRemote, String accountId);

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param playerName 玩家昵称
     * @return
     */
    List<Match> findByPlayerName(String playerName);

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param playerName 玩家昵称
     * @return
     */
    List<Match> findByPlayerName(boolean fromRemote, String playerName);

    /**
     * @description 对局ID查找对局详情
     * @auth sunpeikai
     * @param matchId 玩家昵称
     * @return
     */
    Match findByMatchId(String matchId);
}
