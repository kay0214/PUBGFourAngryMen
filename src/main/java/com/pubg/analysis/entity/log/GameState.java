/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunpeikai
 * @version GameState, v0.1 2020/7/14 11:44
 * @description
 */
@Data
public class GameState implements Serializable {

    private Integer elapsedTime;
    private Integer numAliveTeams;
    private Integer numJoinPlayers;
    private Integer numStartPlayers;
    private Integer numAlivePlayers;
    private Location safetyZonePosition;
    private Double safetyZoneRadius;
    private Location poisonGasWarningPosition;
    private Double poisonGasWarningRadius;
    private Location redZonePosition;
    private Double redZoneRadius;
    private Location blackZonePosition;
    private Double blackZoneRadius;
}
