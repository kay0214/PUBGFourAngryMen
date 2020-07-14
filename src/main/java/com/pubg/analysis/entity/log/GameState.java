/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

/**
 * @author sunpeikai
 * @version GameState, v0.1 2020/7/14 11:44
 * @description
 */
public class GameState {

    private Integer elapsedTime;
    private Integer numAliveTeams;
    private Integer numJoinPlayers;
    private Integer numStartPlayers;
    private Integer numAlivePlayers;
    private Location safetyZonePosition;
    private double safetyZoneRadius;
    private Location poisonGasWarningPosition;
    private double poisonGasWarningRadius;
    private Location redZonePosition;
    private double redZoneRadius;
    private Location blackZonePosition;
    private double blackZoneRadius;
}
