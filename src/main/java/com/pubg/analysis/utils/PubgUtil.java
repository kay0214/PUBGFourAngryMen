package com.pubg.analysis.utils;

import com.pubg.analysis.constants.LogTypes;
import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.BaseLog;
import com.pubg.analysis.entity.log.Character;
import com.pubg.analysis.entity.log.Location;
import com.pubg.analysis.repository.LogRepository;
import com.pubg.analysis.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
     * @param matchId 比赛id
     * @return 开始时间戳
     */
    public static long getMatchStartTime(String matchId) {

        LogRepository logRepository = BeanUtil.getBean(LogRepository.class);
        Optional<Date> start = logRepository.getBaseLog(matchId, Collections.singletonList(LogTypes.LogMatchStart))
                .parallelStream()
                .filter(e -> LogTypes.LogMatchStart.name().equals(e.get_T()))
                .map(BaseLog::get_D)
                .findAny();
        if (start.isPresent()) {
            long time = start.get().getTime();
            log.info("比赛{}开始时间: {}", matchId, time);
            return time;
        } else {
            log.warn("获取比赛开始时间点异常: {}", matchId);
            return 0L;
        }
    }

    /**
     * 获取地图类型
     *
     * @param matchId 比赛id
     * @return 地图类型枚举值
     */
    public static PubgConstant.Maps getMapType(String matchId) {

        MatchRepository matchRepository = BeanUtil.getBean(MatchRepository.class);
        String mapName = matchRepository.findByMatchId(matchId).getMapName();
        PubgConstant.Maps map = PubgConstant.Maps.getByLongName(mapName);
        log.info("获取地图类型: {} -> {}", matchId, map.name());
        return map;
    }

    /**
     * base log 转 character
     * 不保证顺序
     *
     * @param baseLogs baselog列表
     * @return character列表
     */
    public static List<Character> baseLogsToCharacters(List<BaseLog> baseLogs) {

        if (baseLogs == null) {
            return null;
        }

        return baseLogs
                .parallelStream()
                .map(BaseLog::getCharacter)
                .collect(Collectors.toList());
    }
}
