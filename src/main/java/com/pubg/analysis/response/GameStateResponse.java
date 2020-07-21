/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.response;

import com.pubg.analysis.entity.log.Location;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author sunpeikai
 * @version GameStateResponse, v0.1 2020/7/21 16:10
 * @description
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStateResponse implements Serializable {

    // 对局耗时
    // private Integer elapsedTime;
    // 存活队伍数量
    // private Integer numAliveTeams;
    // 加入人数
    // private Integer numJoinPlayers;
    // 游戏开始时玩家数量
    // private Integer numStartPlayers;
    // 当前存活人数
    // private Integer numAlivePlayers;
    private Location safetyZonePosition;
    private BigDecimal safetyZoneRadius;
    private Location poisonGasWarningPosition;
    private BigDecimal poisonGasWarningRadius;
    // 轰炸区
    // private Location redZonePosition;
    // private BigDecimal redZoneRadius;
    // 黑圈
    // private Location blackZonePosition;
    // private BigDecimal blackZoneRadius;
}
