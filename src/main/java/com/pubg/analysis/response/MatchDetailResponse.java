package com.pubg.analysis.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author sunpeikai
 * @version MatchDetailResponse, v0.1 2020/7/14 10:49
 * @description
 */
@Data
@ApiModel("对局详情返回参数")
public class MatchDetailResponse {

    @ApiModelProperty(value = "对局基本信息")
    private MatchResponse match;

    @ApiModelProperty(value = "对局玩家列表信息")
    private List<MatchPlayerResponse> matchPlayers;

    @ApiModelProperty(value = "团队玩家列表信息")
    private List<MatchPlayerResponse> teamPlayers;

    @ApiModelProperty(value = "个人玩家信息")
    private MatchPlayerResponse matchPlayer;
}
