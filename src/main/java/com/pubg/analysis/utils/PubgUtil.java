package com.pubg.analysis.utils;

import com.pubg.analysis.constants.LogTypes;
import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.BaseLog;
import com.pubg.analysis.entity.log.Location;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

	/**
	 * 获取比赛开始时间戳
	 *
	 * @param logs 日志列表
	 * @return 开始时间戳
	 */
	public static long getMatchStartTime(List<BaseLog> logs) {

		Optional<Long> start = logs
				.parallelStream()
				.filter(e -> LogTypes.LogMatchStart.name().equals(e.get_T()))
				.map(e -> e.get_D().getTime())
				.findAny();
		if (start.isPresent()) {
			return start.get();
		} else {
			log.warn("获取比赛开始时间点异常: {}", logs);
			return 0L;
		}
	}
}
