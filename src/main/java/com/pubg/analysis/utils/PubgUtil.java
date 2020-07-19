package com.pubg.analysis.utils;

import com.pubg.analysis.constants.LogTypes;
import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.BaseLog;
import com.pubg.analysis.entity.log.Character;
import com.pubg.analysis.entity.log.GameState;
import com.pubg.analysis.entity.log.Location;
import com.pubg.analysis.repository.LogRepository;
import com.pubg.analysis.repository.MatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.*;
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

    /**
     * 判断是否为机器人
     * 以 character.accountId的前缀作为判断依据
     *
     * @param accountId 账号
     * @return 是否机器人
     */
    public static boolean isAiAccount(String accountId) {

        return accountId.startsWith("ai.");
    }

    /**
     * 根据时间、玩家分组日志列表
     *
     * @param baseLogs       输入日志列表
     * @param startTimestamp 开始时间戳
     * @return 有序map
     */
    public static TreeMap<Long, Map<String, Character>> sortLogByTimeAndCharacter(List<BaseLog> baseLogs, long startTimestamp) {

        TreeMap<Long, Map<String, Character>> logs = new TreeMap<>();
        baseLogs.parallelStream()
                .filter(e -> e.getCharacter() != null && e.getCharacter().getLocation() != null)
                //按时间分组 秒级
                .collect(Collectors.groupingByConcurrent(e -> (e.get_D().getTime() - startTimestamp) / 1000))

                .entrySet()
                .parallelStream()
                //去除开始时间点前的事件
                .filter(e -> e.getKey() > 0)
                .map(entry -> new AbstractMap.SimpleEntry<Long, Map<String, List<Character>>>(
                        entry.getKey(),
                        entry.getValue()
                                .stream()
                                .map(BaseLog::getCharacter)
                                .collect(Collectors.groupingBy(Character::getAccountId))
                ))
                //每组内按用户去重
                .map(entry -> new AbstractMap.SimpleEntry<Long, Map<String, Character>>(
                        entry.getKey(),
                        entry.getValue()
                                .entrySet()
                                .stream()
                                .map(e -> {
                                    //确定要保留的元素
                                    Character keep = e.getValue().get(e.getValue().size() - 1);
                                    //计算比率位置
                                    PubgUtil.calculateLocationRation(keep.getLocation(), PubgConstant.Maps.SANHOK);
                                    e.setValue(Collections.singletonList(keep));
                                    return new AbstractMap.SimpleEntry<String, Character>(
                                            e.getKey(),
                                            keep);
                                })
                                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                ))

                //添加到结果
                .forEachOrdered(e -> logs.put(e.getKey(), e.getValue()));

        return logs;
    }

    /**
     * 取得玩家死亡时间
     *
     * @param matchId 比赛id
     * @return accountId, 时间戳
     */
    public static Map<String, Long> getDeathTimestamp(String matchId) {

        LogRepository logRepository = BeanUtil.getBean(LogRepository.class);
        return logRepository.getBaseLog(matchId, Collections.singletonList(LogTypes.LogPlayerKill))
                .parallelStream()
                .collect(Collectors.toMap(
                        e -> e.getVictim().getAccountId(),
                        e -> e.get_D().getTime()
                ));
    }

    /**
     * 获取玩家列表
     * 包括电脑
     * 依照accountId去重
     *
     * @param baseLogs 日志列表
     * @return 玩家列表
     */
    public static Map<String, Character> getDistinctCharacter(List<BaseLog> baseLogs) {

        return baseLogs
                .parallelStream()
                .filter(e -> e.getCharacter() != null && !e.getCharacter().getAccountId().isEmpty())
                .map(BaseLog::getCharacter)
                .distinct()
                .collect(Collectors.toMap(Character::getAccountId, c -> c));

    }

    /**
     * 取得角色维度位置跟踪数据
     *
     * @param baseLogs 日志列表
     * @return accountId, [[xRatio, yRatio]]
     */
    public static Map<String, List<List<Double>>> getPersonalTrack(List<BaseLog> baseLogs, PubgConstant.Maps mapType) {

        //获取落地时间
        Map<String, Long> landings = baseLogs
                .parallelStream()
                .filter(e -> LogTypes.LogParachuteLanding.name().equals(e.get_T()))
                .collect(Collectors.toMap(
                        e -> e.getCharacter().getAccountId(),
                        e -> e.get_D().getTime(),
                        (v1, v2) -> v2
                ));


        return baseLogs
                .parallelStream()
                //过滤无效数据
                .filter(e -> e.getCharacter() != null &&
                        e.getCharacter().getLocation() != null &&
                        !StringUtils.isEmpty(e.getCharacter().getAccountId())
                )
                //过滤落地前数据
                .filter(e -> {
                    String accountId = e.getCharacter().getAccountId();
                    if (!landings.containsKey(accountId)) {
                        return false;
                    }
                    return e.get_D().getTime() >= landings.get(accountId);
                })
                .collect(Collectors.groupingByConcurrent(e -> e.getCharacter().getAccountId()))
                .entrySet()
                .parallelStream()
                .map(e -> new AbstractMap.SimpleEntry<String, List<List<Double>>>(
                        e.getKey(),
                        e.getValue()
                                .stream()
                                .sorted(Comparator.comparing(BaseLog::get_D))
                                .map(l -> {
                                    Location location = l.getCharacter().getLocation();
                                    PubgUtil.calculateLocationRation(location, mapType);
                                    return Arrays.asList(location.getXRatio(), location.getYRatio());
                                })
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

    }

    /**
     * 取得游戏状态信息
     *
     * @param matchId        比赛id
     * @param startTimestamp 比赛开始时间戳
     * @return 时间，状态map
     */
    public static Map<Long, GameState> getGameStateByMatchId(String matchId, long startTimestamp, PubgConstant.Maps mapType) {

        LogRepository logRepository = BeanUtil.getBean(LogRepository.class);
        List<BaseLog> baseLogs = logRepository.getBaseLog(matchId, Collections.singletonList(LogTypes.LogGameStatePeriodic));

        return baseLogs
                .parallelStream()
                .filter(e -> e.getGameState() != null)
                .peek(e -> {
                    GameState gameState = e.getGameState();
                    //计算比率位置
                    calculateLocationRation(gameState.getSafetyZonePosition(), mapType);
                    calculateLocationRation(gameState.getPoisonGasWarningPosition(), mapType);
                    calculateLocationRation(gameState.getRedZonePosition(), mapType);
                    calculateLocationRation(gameState.getBlackZonePosition(), mapType);

                    //根据地图宽计算横半径比率
                    long width = mapType.getWidth();
                    gameState.setSafetyZoneRadius(gameState.getSafetyZoneRadius() / width);
                    gameState.setPoisonGasWarningRadius(gameState.getPoisonGasWarningRadius() / width);
                    gameState.setRedZoneRadius(gameState.getRedZoneRadius() / width);
                    gameState.setBlackZoneRadius(gameState.getBlackZoneRadius() / width);
                })
                .collect(Collectors.toMap(
                        e -> (e.get_D().getTime() - startTimestamp) / 1000,
                        e -> e.getGameState(),
                        (v1, v2) -> v2
                ));
    }
}
