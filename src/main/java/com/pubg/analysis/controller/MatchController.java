/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.controller;

import com.pubg.analysis.base.Page;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import com.pubg.analysis.request.MatchRequest;
import com.pubg.analysis.response.MatchDetailResponse;
import com.pubg.analysis.response.MatchPlayerResponse;
import com.pubg.analysis.response.MatchResponse;
import com.pubg.analysis.service.IPubgService;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.EntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunpeikai
 * @version TestMongo, v0.1 2020/7/10 10:49
 * @description
 */
@Api(value = "对局",tags = "对局")
@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private IPubgService iPubgService;

    @ApiOperation(value = "账户昵称搜索对局列表本地库",notes = "账户昵称搜索对局列表本地库")
    @PostMapping("/findMatchPageByPlayerName")
    public Page<MatchResponse> findMatchPageByPlayerName(@RequestBody MatchRequest request){
        Page<Match> page =  iPubgService.findMatchPageByPlayerName(request);
        return page.convert(this::formatMatch);
    }

    @ApiOperation(value = "账户昵称搜索对局列表远程库",notes = "账户昵称搜索对局列表远程库")
    @PostMapping("/findMatchPageByPlayerNameRemote")
    public Page<MatchResponse> findMatchPageByPlayerNameRemote(@RequestBody MatchRequest request){
        Page<Match> page =  iPubgService.findMatchPageByPlayerName(true,request);
        return page.convert(this::formatMatch);
    }

    @ApiOperation(value = "账户ID搜索对局列表本地库",notes = "账户ID搜索对局列表本地库")
    @PostMapping("/findMatchPageByAccountId")
    public Page<MatchResponse> findMatchPageByAccountId(@RequestBody MatchRequest request){
        Page<Match> page =  iPubgService.findMatchPageByAccountId(request);
        return page.convert(this::formatMatch);
    }

    @ApiOperation(value = "账户ID搜索对局列表远程库",notes = "账户ID搜索对局列表远程库")
    @PostMapping("/findMatchPageByAccountIdRemote")
    public Page<MatchResponse> findMatchPageByAccountIdRemote(@RequestBody MatchRequest request){
        Page<Match> page = iPubgService.findMatchPageByAccountId(true,request);
        return page.convert(this::formatMatch);
    }

    @ApiOperation(value = "对局ID搜索对局详情本地库",notes = "对局ID搜索对局详情本地库")
    @PostMapping("/findMatchDetailByMatchId")
    public MatchDetailResponse findMatchDetailByMatchId(@RequestBody MatchRequest request){
        if(StringUtils.isEmpty(request.getMatchId())){
            return null;
        }
        MatchDetailResponse result = new MatchDetailResponse();

        // 搜索对局基本信息
        Match match = iPubgService.findMatchByMatchId(request.getMatchId());
        if(match != null){
            // 格式化对局基本信息
            MatchResponse matchResponse = formatMatch(match);
            result.setMatch(matchResponse);
        }
        // 获取对局所有玩家信息
        List<MatchPlayer> matchPlayers = iPubgService.findMatchPlayersByMatchId(request.getMatchId());
        result.setMatchPlayers(EntityUtil.copyBeans(matchPlayers, MatchPlayerResponse.class));
        return result;
    }

    @ApiOperation(value = "对局ID和玩家ID搜索对局详情本地库",notes = "对局ID和玩家ID搜索对局详情本地库")
    @PostMapping("/findMatchDetailByMatchIdAndAccountId")
    public MatchDetailResponse findMatchDetailByMatchIdAndAccountId(@RequestBody MatchRequest request){
        if(StringUtils.isEmpty(request.getMatchId()) || StringUtils.isEmpty(request.getAccountId())){
            return null;
        }
        MatchDetailResponse result = new MatchDetailResponse();
        // 搜索对局基本信息
        Match match = iPubgService.findMatchByMatchId(request.getMatchId());
        if(match != null){
            // 格式化对局基本信息
            MatchResponse matchResponse = formatMatch(match);
            result.setMatch(matchResponse);
        }
        // 获取对局所有玩家信息
        List<MatchPlayer> matchPlayers = iPubgService.findMatchPlayersByMatchId(request.getMatchId());
        result.setMatchPlayers(EntityUtil.copyBeans(matchPlayers, MatchPlayerResponse.class));
        // 获取对局个人玩家信息
        MatchPlayer personal = null;
        for (MatchPlayer matchPlayer : matchPlayers) {
            if (matchPlayer.getAccountId().equals(request.getAccountId())) {
                personal = matchPlayer;
            }
        }
        if(personal != null){
            result.setMatchPlayer(EntityUtil.copyBean(personal, MatchPlayerResponse.class));
            // 获取对局团队玩家信息
            List<MatchPlayer> teamPlayers = new ArrayList<>();
            for (MatchPlayer matchPlayer : matchPlayers) {
                if (matchPlayer.getTeamId().equals(personal.getTeamId())) {
                    teamPlayers.add(matchPlayer);
                }
            }
            result.setTeamPlayers(EntityUtil.copyBeans(teamPlayers, MatchPlayerResponse.class));
        }

        return result;
    }

    @ApiOperation(value = "对局ID和玩家昵称搜索对局详情本地库",notes = "对局ID和玩家昵称搜索对局详情本地库")
    @PostMapping("/findMatchDetailByMatchIdAndPlayerName")
    public MatchDetailResponse findMatchDetailByMatchIdAndPlayerName(@RequestBody MatchRequest request){
        if(StringUtils.isEmpty(request.getMatchId()) || StringUtils.isEmpty(request.getPlayerName())){
            return null;
        }
        MatchDetailResponse result = new MatchDetailResponse();
        // 搜索对局基本信息
        Match match = iPubgService.findMatchByMatchId(request.getMatchId());
        if(match != null){
            // 格式化对局基本信息
            MatchResponse matchResponse = formatMatch(match);
            result.setMatch(matchResponse);
        }
        // 获取对局所有玩家信息
        List<MatchPlayer> matchPlayers = iPubgService.findMatchPlayersByMatchId(request.getMatchId());
        result.setMatchPlayers(EntityUtil.copyBeans(matchPlayers, MatchPlayerResponse.class));
        // 获取对局个人玩家信息
        MatchPlayer personal = null;
        for (MatchPlayer matchPlayer : matchPlayers) {
            if (matchPlayer.getPlayerName().equals(request.getPlayerName())) {
                personal = matchPlayer;
            }
        }
        if(personal != null){
            result.setMatchPlayer(EntityUtil.copyBean(personal, MatchPlayerResponse.class));
            // 获取对局团队玩家信息
            List<MatchPlayer> teamPlayers = new ArrayList<>();
            for (MatchPlayer matchPlayer : matchPlayers) {
                if (matchPlayer.getTeamId().equals(personal.getTeamId())) {
                    teamPlayers.add(matchPlayer);
                }
            }
            result.setTeamPlayers(EntityUtil.copyBeans(teamPlayers, MatchPlayerResponse.class));
        }

        return result;
    }

    private MatchResponse formatMatch(Match match){
        // 格式化处理
        MatchResponse response = new MatchResponse();
        response.setMatchId(match.getMatchId());
        response.setDuration(match.getDuration());
        response.setCustomMatch(match.getCustomMatch());
        response.setMapName(match.getMapName());
        response.setGameMode(match.getGameMode());
        response.setAssetsUrl(match.getAssetsUrl());
        response.setCreateTime(DateUtil.formatDateTime(match.getCreateTime()));
        return response;
    }
}