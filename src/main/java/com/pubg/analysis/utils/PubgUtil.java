package com.pubg.analysis.utils;

import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.Location;
import lombok.extern.slf4j.Slf4j;

/**
 * @author yangy
 * @date 2020/7/12 0:47
 */
@Slf4j
public class PubgUtil {

	/**
	 * 有数值坐标转换为比率坐标
	 *
	 * @param location 位置
	 * @param mapType  地图类型
	 */
	public static void calculateLocationRation(Location location, PubgConstant.Maps mapType) {

		location.setXRatio(location.getX() / mapType.getWidth());
		location.setYRatio(location.getY() / mapType.getHeight());
		log.debug("计算坐标比率, location: {}, 地图: {}", location, mapType);
	}
}
