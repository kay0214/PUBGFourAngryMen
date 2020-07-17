/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import lombok.Data;

import java.io.Serializable;

/**
 * @author sunpeikai
 * @version Stats, v0.1 2020/7/14 10:31
 * @description
 */
@Data
public class Stats implements Serializable {

    private Integer killCount;
    private Double distanceOnFoot;
    private Double distanceOnSwim;
    private Double distanceOnVehicle;
    private Double distanceOnParachute;
    private Double distanceOnFreefall;
}
