/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import com.pubg.analysis.utils.ScaleUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

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
    private BigDecimal safetyZoneRadius;
    private Location poisonGasWarningPosition;
    private BigDecimal poisonGasWarningRadius;
    private Location redZonePosition;
    private BigDecimal redZoneRadius;
    private Location blackZonePosition;
    private BigDecimal blackZoneRadius;

    public void setSafetyZoneRadius(BigDecimal safetyZoneRadius) {
        this.safetyZoneRadius = ScaleUtil.threeScale(safetyZoneRadius);
    }

    public void setPoisonGasWarningRadius(BigDecimal poisonGasWarningRadius) {
        this.poisonGasWarningRadius = ScaleUtil.threeScale(poisonGasWarningRadius);
    }

    public void setRedZoneRadius(BigDecimal redZoneRadius) {
        this.redZoneRadius = ScaleUtil.threeScale(redZoneRadius);
    }

    public void setBlackZoneRadius(BigDecimal blackZoneRadius) {
        this.blackZoneRadius = ScaleUtil.threeScale(blackZoneRadius);
    }
}
