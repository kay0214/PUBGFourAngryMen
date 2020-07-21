package com.pubg.analysis.entity.log;

import com.pubg.analysis.utils.ScaleUtil;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author yangy
 * @date 2020/7/10 9:20
 */
@Data
public class Location implements Serializable {

	/**
	 * x : 123399.4375
	 * y : 121450.4765625
	 * z : 1358.9000244140625
	 */

	private BigDecimal x;
	private BigDecimal y;
	private BigDecimal z;

	private BigDecimal xRatio;
	private BigDecimal yRatio;

    public void setX(BigDecimal x) {
        this.x = ScaleUtil.threeScale(x);
    }

    public void setY(BigDecimal y) {
        this.y = ScaleUtil.threeScale(y);
    }

    public void setZ(BigDecimal z) {
        this.z = ScaleUtil.threeScale(z);
    }

    public void setXRatio(BigDecimal xRatio) {
        this.xRatio = ScaleUtil.threeScale(xRatio);
    }

    public void setYRatio(BigDecimal yRatio) {
        this.yRatio = ScaleUtil.threeScale(yRatio);
    }
}
