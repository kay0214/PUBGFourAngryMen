package com.pubg.analysis.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunpeikai
 * @version MatchPlayerResponse, v0.1 2020/7/14 10:49
 * @description
 */
@Data
@ApiModel("对局人物返回参数")
public class MatchPlayerResponse {

    @ApiModelProperty(value = "对局ID")
    private String matchId;

    @ApiModelProperty(value = "玩家ID")
    private String accountId;

    @ApiModelProperty(value = "玩家昵称")
    private String playerName;

    @ApiModelProperty(value = "team id")
    private String teamId;

    @ApiModelProperty(value = "matchPlayerId - 团队与个人映射关系")
    private String matchPlayerId;

    @ApiModelProperty(value = "个人击杀")
    private Integer kills;

    @ApiModelProperty(value = "个人爆头击杀")
    private Integer headshotKills;

    @ApiModelProperty(value = "团队排名")
    private Integer winPlace;

    @ApiModelProperty(value = "治疗次数")
    private Integer heals;

    @ApiModelProperty(value = "跑步移动距离")
    private double walkDistance;

    @ApiModelProperty(value = "最长击杀时间?")
    private double longestKill;

    @ApiModelProperty(value = "摧毁载具")
    private Integer vehicleDestroys;

    @ApiModelProperty(value = "不知道什么意思")
    private Integer DBNOs;

    @ApiModelProperty(value = "驾车移动距离")
    private double rideDistance;

    @ApiModelProperty(value = "死亡原因 - byplayer")
    private String deathType;

    @ApiModelProperty(value = "复活次数?还是扶起倒地队友次数 - 倾向后者")
    private Integer revives;

    @ApiModelProperty(value = "助攻数")
    private Integer assists;

    @ApiModelProperty(value = "不知道什么意思")
    private Integer killPlace;

    @ApiModelProperty(value = "不知道什么意思")
    private Integer killStreaks;

    @ApiModelProperty(value = "游泳移动距离")
    private double swimDistance;

    @ApiModelProperty(value = "获得武器")
    private Integer weaponsAcquired;

    @ApiModelProperty(value = "不知道什么意思")
    private Integer roadKills;

    @ApiModelProperty(value = "不知道什么意思")
    private Integer boosts;

    @ApiModelProperty(value = "存活时间")
    private double timeSurvived;

    @ApiModelProperty(value = "造成伤害")
    private double damageDealt;

    @ApiModelProperty(value = "团队击杀")
    private Integer teamKills;
}