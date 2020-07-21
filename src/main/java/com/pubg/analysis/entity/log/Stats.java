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
 * @version Stats, v0.1 2020/7/14 10:31
 * @description
 */
@Data
public class Stats implements Serializable {

    private Integer killCount;
    private BigDecimal distanceOnFoot;
    private BigDecimal distanceOnSwim;
    private BigDecimal distanceOnVehicle;
    private BigDecimal distanceOnParachute;
    private BigDecimal distanceOnFreefall;

    public void setDistanceOnFoot(BigDecimal distanceOnFoot) {
        this.distanceOnFoot = ScaleUtil.threeScale(distanceOnFoot);
    }

    public void setDistanceOnSwim(BigDecimal distanceOnSwim) {
        this.distanceOnSwim = ScaleUtil.threeScale(distanceOnSwim);
    }

    public void setDistanceOnVehicle(BigDecimal distanceOnVehicle) {
        this.distanceOnVehicle = ScaleUtil.threeScale(distanceOnVehicle);
    }

    public void setDistanceOnParachute(BigDecimal distanceOnParachute) {
        this.distanceOnParachute = ScaleUtil.threeScale(distanceOnParachute);
    }

    public void setDistanceOnFreefall(BigDecimal distanceOnFreefall) {
        this.distanceOnFreefall = ScaleUtil.threeScale(distanceOnFreefall);
    }
}
