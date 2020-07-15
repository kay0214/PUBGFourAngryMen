/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.controller;

import com.pubg.analysis.base.Page;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.request.MatchRequest;
import com.pubg.analysis.response.MatchDetailResponse;
import com.pubg.analysis.response.MatchResponse;
import com.pubg.analysis.service.IPubgService;
import com.pubg.analysis.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
        return formatMatch(page);
    }
    @ApiOperation(value = "账户昵称搜索对局列表远程库",notes = "账户昵称搜索对局列表远程库")
    @PostMapping("/findMatchPageByPlayerNameRemote")
    public Page<MatchResponse> findMatchPageByPlayerNameRemote(@RequestBody MatchRequest request){
        Page<Match> page =  iPubgService.findMatchPageByPlayerName(true,request);
        return formatMatch(page);
    }
    @ApiOperation(value = "账户ID搜索对局列表本地库",notes = "账户ID搜索对局列表本地库")
    @PostMapping("/findMatchPageByAccountId")
    public Page<MatchResponse> findMatchPageByAccountId(@RequestBody MatchRequest request){
        Page<Match> page =  iPubgService.findMatchPageByAccountId(request);
        return formatMatch(page);
    }
    @ApiOperation(value = "账户ID搜索对局列表远程库",notes = "账户ID搜索对局列表远程库")
    @PostMapping("/findMatchPageByAccountIdRemote")
    public Page<MatchResponse> findMatchPageByAccountIdRemote(@RequestBody MatchRequest request){
        Page<Match> page = iPubgService.findMatchPageByAccountId(true,request);
        return formatMatch(page);
    }

    private Page<MatchResponse> formatMatch(Page<Match> matchPage){
        Page<MatchResponse> result = matchPage.convert(match -> {
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
        });
        return result;
    }
    @ApiOperation(value = "测试matches",notes = "测试matches")
    @PostMapping("/matches/{id}")
    public MatchDetailResponse matches(@PathVariable String id){
        // return iPubgService.findMatchDetailByMatchId(id);
        // TODO:sunpeikai 对局详情请求实现
        return null;
    }
}
