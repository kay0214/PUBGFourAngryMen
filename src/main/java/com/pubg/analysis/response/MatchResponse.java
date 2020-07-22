package com.pubg.analysis.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author sunpeikai
 * @version MatchResponse, v0.1 2020/7/14 10:49
 * @description
 */
@Data
@ApiModel("对局列表返回参数")
public class MatchResponse implements Serializable {
    // 对局ID
    @ApiModelProperty("对局ID")
    private String matchId;
    // accountId
    @ApiModelProperty("玩家昵称,不一定有")
    private String playerName;
    // 持续时间
    @ApiModelProperty("持续时间")
    private Integer duration;
    // 持续时间
    @ApiModelProperty("持续时间 - 已格式化")
    private String durationStr;
    // 是否自定义对局
    @ApiModelProperty("是否自定义对局")
    private Integer customMatch;
    // 是否自定义对局
    @ApiModelProperty("是否自定义对局 - 已格式化")
    private String customMatchStr;
    // 地图名称
    @ApiModelProperty("地图名称")
    private String mapName;
    // 模式 - squad - 四排
    @ApiModelProperty("模式")
    private String gameMode;
    // 是否已经拉取了对局日志
    @ApiModelProperty("是否已经拉取了对局日志")
    private Integer fetchLog;
    // 是否已经拉取了对局日志
    @ApiModelProperty("是否已经拉取了对局日志 - 已格式化")
    private String fetchLogStr;
    // 对局创建时间
    @ApiModelProperty("对局创建时间")
    private String createTime;
}
