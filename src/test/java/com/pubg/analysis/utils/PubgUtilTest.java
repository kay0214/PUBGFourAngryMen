package com.pubg.analysis.utils;

import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.Location;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author yangy
 * @date 2020/7/12 8:30
 */
public class PubgUtilTest {

	@Test
	public void calculateLocationRation() {

		Location location = new Location();
		location.setX(4 * 1000 * 100);
		location.setY(2 * 1000 * 100);

		PubgUtil.calculateLocationRation(location, PubgConstant.Maps.SANHOK);
		Assert.assertEquals(1, location.getXRatio(), 0.001);
		Assert.assertEquals(0.5, location.getYRatio(), 0.001);
	}
}