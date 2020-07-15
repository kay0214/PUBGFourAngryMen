package com.pubg.analysis.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author sunpeikai
 * @version MatchResponse, v0.1 2020/7/14 10:49
 * @description
 */
@Data
@ApiModel("对局列表返回参数")
public class MatchResponse {
    // 对局ID
    @ApiModelProperty("对局ID")
    private String matchId;
    // 持续时间
    @ApiModelProperty("持续时间")
    private Integer duration;
    // 是否自定义对局
    @ApiModelProperty("是否自定义对局")
    private Integer customMatch;
    // 地图名称
    @ApiModelProperty("地图名称")
    private String mapName;
    // 模式 - squad - 四排
    @ApiModelProperty("模式")
    private String gameMode;
    // 对局日志URL
    @ApiModelProperty("对局日志URL")
    private String assetsUrl;
    // 对局创建时间
    @ApiModelProperty("对局创建时间")
    private String createTime;
}
