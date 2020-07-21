/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author sunpeikai
 * @version TrackPlayerResponse, v0.1 2020/7/21 14:34
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "玩家列表")
public class TrackPlayerResponse implements Serializable {

    // 玩家ID
    @ApiModelProperty("玩家ID")
    private String accountId;
    // 玩家昵称
    @ApiModelProperty("玩家昵称")
    private String playerName;
    // 团队排名
    @ApiModelProperty("团队排名")
    private Integer winPlace;
}
