/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.controller;

import com.pubg.analysis.base.Page;
import com.pubg.analysis.base.R;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import com.pubg.analysis.request.MatchRequest;
import com.pubg.analysis.response.MatchDetailResponse;
import com.pubg.analysis.response.MatchPlayerResponse;
import com.pubg.analysis.response.MatchResponse;
import com.pubg.analysis.service.IPubgService;
import com.pubg.analysis.utils.EntityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sunpeikai
 * @version MatchController, v0.1 2020/7/15 10:49
 * @description
 */
@Slf4j
@Api(value = "对局",tags = "对局")
@RestController
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private IPubgService iPubgService;

    @ApiOperation(value = "账户昵称搜索对局列表本地库",notes = "账户昵称搜索对局列表本地库")
    @GetMapping("/findMatchPageByPlayerName")
    public R<Page<MatchResponse>> findMatchPageByPlayerName(MatchRequest request){
        handleParam(request);
        return R.data(iPubgService.findMatchPageByPlayerName(request));
    }

    @ApiOperation(value = "账户昵称搜索对局列表远程库",notes = "账户昵称搜索对局列表远程库")
    @GetMapping("/findMatchPageByPlayerNameRemote")
    public R<Page<MatchResponse>> findMatchPageByPlayerNameRemote(MatchRequest request){
        return R.data(iPubgService.findMatchPageByPlayerName(true,request));
    }

    @ApiOperation(value = "账户ID搜索对局列表本地库",notes = "账户ID搜索对局列表本地库")
    @GetMapping("/findMatchPageByAccountId")
    public R<Page<MatchResponse>> findMatchPageByAccountId(MatchRequest request){
        handleParam(request);
        return R.data(iPubgService.findMatchPageByAccountId(request));
    }

    @ApiOperation(value = "账户ID搜索对局列表远程库",notes = "账户ID搜索对局列表远程库")
    @GetMapping("/findMatchPageByAccountIdRemote")
    public R<Page<MatchResponse>> findMatchPageByAccountIdRemote(MatchRequest request){
        return R.data(iPubgService.findMatchPageByAccountId(true,request));
    }

    @ApiOperation(value = "对局ID搜索对局详情本地库",notes = "对局ID搜索对局详情本地库")
    @PostMapping("/findMatchDetailByMatchId")
    public R<MatchDetailResponse> findMatchDetailByMatchId(@RequestBody MatchRequest request){
        if(StringUtils.isEmpty(request.getMatchId())){
            return null;
        }
        MatchDetailResponse result = new MatchDetailResponse();

        // 搜索对局基本信息
        Match match = iPubgService.findMatchByMatchId(request.getMatchId());
        if(match != null){
            // 格式化对局基本信息
            result.setMatch(match.getResponse());
        }
        // 获取对局所有玩家信息
        List<MatchPlayer> matchPlayers = iPubgService.findMatchPlayersByMatchId(request.getMatchId());
        result.setMatchPlayers(EntityUtil.copyBeans(matchPlayers, MatchPlayerResponse.class));
        return R.data(result);
    }

    @ApiOperation(value = "对局ID和玩家ID搜索对局详情本地库",notes = "对局ID和玩家ID搜索对局详情本地库")
    @PostMapping("/findMatchDetailByMatchIdAndAccountId")
    public R<MatchDetailResponse> findMatchDetailByMatchIdAndAccountId(@RequestBody MatchRequest request){
        if(StringUtils.isEmpty(request.getMatchId()) || StringUtils.isEmpty(request.getAccountId())){
            return null;
        }
        MatchDetailResponse result = new MatchDetailResponse();
        // 搜索对局基本信息
        Match match = iPubgService.findMatchByMatchId(request.getMatchId());
        if(match != null){
            // 格式化对局基本信息
            result.setMatch(match.getResponse());
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

        return R.data(result);
    }

    @ApiOperation(value = "对局ID和玩家昵称搜索对局详情本地库",notes = "对局ID和玩家昵称搜索对局详情本地库")
    @PostMapping("/findMatchDetailByMatchIdAndPlayerName")
    public R<MatchDetailResponse> findMatchDetailByMatchIdAndPlayerName(@RequestBody MatchRequest request){
        if(StringUtils.isEmpty(request.getMatchId()) || StringUtils.isEmpty(request.getPlayerName())){
            return null;
        }
        MatchDetailResponse result = new MatchDetailResponse();
        // 搜索对局基本信息
        Match match = iPubgService.findMatchByMatchId(request.getMatchId());
        if(match != null){
            // 格式化对局基本信息
            result.setMatch(match.getResponse());
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

        return R.data(result);
    }

    private void handleParam(MatchRequest request){
        request.setPlayerName(StringUtils.isEmpty(request.getPlayerName())?"":request.getPlayerName());
        request.setAccountId(StringUtils.isEmpty(request.getAccountId())?"":request.getAccountId());
        request.setMatchId(StringUtils.isEmpty(request.getMatchId())?"":request.getMatchId());
    }
}
