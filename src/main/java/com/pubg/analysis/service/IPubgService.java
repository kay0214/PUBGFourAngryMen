package com.pubg.analysis.service;

import com.pubg.analysis.base.Page;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.request.MatchRequest;
import com.pubg.analysis.response.MatchResponse;
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
     * @param request 玩家账户ID
     * @return
     */
    Page<Match> findMatchPageByAccountId(MatchRequest request);

    /**
     * @description accountId查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param request 玩家账户ID
     * @return
     */
    Page<Match> findMatchPageByAccountId(boolean fromRemote, MatchRequest request);

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param request 玩家昵称
     * @return
     */
    Page<Match> findMatchPageByPlayerName(MatchRequest request);

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param request 玩家昵称
     * @return
     */
    Page<Match> findMatchPageByPlayerName(boolean fromRemote, MatchRequest request);

    /**
     * @description 对局ID查找对局详情
     * @auth sunpeikai
     * @param matchId 玩家昵称
     * @return
     */
    Match findMatchDetailByMatchId(String matchId);
}
