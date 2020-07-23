package com.pubg.analysis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.api.PubgApi;
import com.pubg.analysis.api.enums.PubgApiEnum;
import com.pubg.analysis.base.Page;
import com.pubg.analysis.constants.ApiConstant;
import com.pubg.analysis.constants.LogTypes;
import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.BaseLog;
import com.pubg.analysis.entity.log.Character;
import com.pubg.analysis.entity.log.GameState;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import com.pubg.analysis.repository.LogRepository;
import com.pubg.analysis.repository.MatchRepository;
import com.pubg.analysis.repository.MatchPlayerRepository;
import com.pubg.analysis.request.MatchRequest;
import com.pubg.analysis.response.*;
import com.pubg.analysis.service.IPubgService;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.EntityUtil;
import com.pubg.analysis.utils.PubgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.pubg.analysis.utils.PubgUtil.sortLogByTimeAndCharacter;

/**
 * @author yangy
 * @date 2020/7/12 9:22
 */
@Service
@Slf4j
public class PubgServiceImpl implements IPubgService {

    @Resource
    private LogRepository logRepository;
    @Resource
    private MatchRepository matchRepository;
    @Resource
    private MatchPlayerRepository matchPlayerRepository;


    @Override
    @Cacheable(value = "match:position", key = "#matchId", unless = "#result.positions.size()<=0")
    public PositionResponse getPubgLocation(String matchId) {

        log.info("获取位置时间维度位置追踪数据");

        //所有带character的日志
        List<String> fieldList = Collections.singletonList("character");
        List<BaseLog> baseLogs = logRepository.getBaseLog(matchId, null, fieldList);

        //确定游戏起始时间点
        long startTimestamp = PubgUtil.getMatchStartTime(matchId);
        PubgConstant.Maps mapType = PubgUtil.getMapType(matchId);

        //计算位置分组
        TreeMap<Long, Map<String, Character>> logs = sortLogByTimeAndCharacter(baseLogs, startTimestamp, mapType);
        // 转换位置分组
        TreeMap<Long, Map<String, CharacterPositionResponse>> positionLogs = new TreeMap<>();
        logs.forEach((time,mapValue) -> {
            Map<String,CharacterPositionResponse> map = new HashMap<>();
            mapValue.forEach((key,value) -> map.put(key,EntityUtil.copyBean(value,CharacterPositionResponse.class)));
            positionLogs.put(time,map);
        });
        log.debug("读取到LogPlayerPosition: {}", positionLogs);

        //读取死亡记录
        Map<String, Long> deathLog = PubgUtil.getDeathTimestamp(matchId);
        deathLog.entrySet().forEach(e -> e.setValue((e.getValue() - startTimestamp) / 1000));

        //玩家列表
        List<MatchPlayer> matchPlayers = matchPlayerRepository.findByMatchId(matchId);
        Map<String, TrackPlayerResponse> characters = matchPlayers
                .parallelStream()
                .collect(Collectors.toMap(MatchPlayer::getAccountId, c -> EntityUtil.copyBean(c,TrackPlayerResponse.class)));
        // AI列表
        PubgUtil.getDistinctCharacter(baseLogs).forEach((key,value) -> {
            // accountId是AI并且不包含在列表中
            if(PubgUtil.isAiAccount(value.getAccountId()) && !characters.containsKey(value.getAccountId())){
                characters.put(value.getAccountId(),new TrackPlayerResponse(value.getAccountId(),value.getName(),value.getTeamId()));
            }
        });

        //角色维度位置追踪
        Map<String, List<List<BigDecimal>>> playerTrack = PubgUtil.getPersonalTrack(baseLogs, mapType);

        //游戏状态信息
        Map<Long, GameState> gameStateMap = PubgUtil.getGameStateByMatchId(matchId, startTimestamp, mapType);
        // 游戏状态信息转换 - 为了降低网络流量
        Map<Long, GameStateResponse> gameStateResponseMap = gameStateMap.entrySet()
                .parallelStream()
                .collect(Collectors.toMap(Map.Entry::getKey, c -> EntityUtil.copyBean(c.getValue(),GameStateResponse.class)));

        //构建响应
        PositionResponse response = new PositionResponse();
        response.setPositions(positionLogs);
        response.setMapType(mapType.name());
        response.setDeathLog(deathLog);
        response.setCharacters(characters);
        response.setPlayerTrack(playerTrack);
        response.setGameState(gameStateResponseMap);
        if (logs.size() > 0) {
            response.setStart(logs.firstKey());
            response.setEnd(logs.lastKey());
        }

        return response;
    }


    /**
     * @param request 玩家账户ID
     * @return
     * @description accountId查找对局列表
     * @auth sunpeikai
     */
    @Override
    /*@Cacheable(value = "match:page:accountId", key = "#request.accountId + #request.page + #request.limit", unless = "#result.records.size()<=0")*/
    public Page<MatchResponse> findMatchPageByAccountId(MatchRequest request) {

        log.info("no cached");
        return findMatchPageByAccountId(false, request);
    }

    /**
     * @param fromRemote 从pubg官网更新
     * @param request    玩家账户ID
     * @return
     * @description accountId查找对局列表
     * @auth sunpeikai
     */
    @Override
    public Page<MatchResponse> findMatchPageByAccountId(boolean fromRemote, MatchRequest request) {

        if (fromRemote) {
            // 从pubg官网重新拉取数据
            updateMatchesFromRemote(request.getAccountId(), "");
        }
        return findMatchPageByAccountIdOrPlayerName(request);
    }

    /**
     * @param request 玩家昵称
     * @return
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     */
    @Override
    /*@Cacheable(value = "match:page:playerName", key = "#request.playerName + #request.page + #request.limit", unless = "#result.records.size()<=0")*/
    public Page<MatchResponse> findMatchPageByPlayerName(MatchRequest request) {

        log.info("no cached");
        return findMatchPageByPlayerName(false, request);
    }

    /**
     * @param fromRemote 从pubg官网更新
     * @param request    玩家昵称
     * @return
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     */
    @Override
    public Page<MatchResponse> findMatchPageByPlayerName(boolean fromRemote, MatchRequest request) {

        if (fromRemote) {
            // 从pubg官网重新拉取数据
            updateMatchesFromRemote("", request.getPlayerName());
        }
        return findMatchPageByAccountIdOrPlayerName(request);
    }

    /**
     * @param
     * @return
     * @description 从pubg拉取对局列表
     * @auth sunpeikai
     */
    private void updateMatchesFromRemote(String accountId, String playerName) {

        JSONObject result = new JSONObject();
        if (!StringUtils.isEmpty(accountId)) {
            result = PubgApi.useApi(PubgApiEnum.PUBG_API_PLAYER_ACCOUNT).call(accountId);
        } else if (!StringUtils.isEmpty(playerName)) {
            result = PubgApi.useApi(PubgApiEnum.PUBG_API_PLAYER_NAME).call(playerName);
        }
        List<String> matches = new ArrayList<>();
        // 处理数据
        if (result != null && !result.isEmpty()) {
            // 返回不为空
            JSONArray data = result.getJSONArray("data");
            if (data != null && !data.isEmpty()) {
                // 返回data不为空
                int length = data.size();
                for (int i = 0; i < length; i++) {
                    JSONObject player = data.getJSONObject(i);
                    // 对局列表
                    JSONArray remoteMatches = player.getJSONObject("relationships").getJSONObject("matches").getJSONArray("data");
                    // 对局列表数量
                    int matchLength = remoteMatches.size();
                    for (int j = 0; j < matchLength; j++) {
                        JSONObject match = remoteMatches.getJSONObject(j);
                        matches.add(match.getString("id"));
                    }
                }
            }
        }
        // 校验哪些对局需要更新 - 需要更新则请求并更新
        for (String matchId : matches) {
            findMatchByMatchId(matchId);
        }
    }

    /**
     * @param matchId 对局ID
     * @return
     * @description 对局ID查找对局详情
     * @auth sunpeikai
     */
    @Override
    public Match findMatchByMatchId(String matchId) {

        if (StringUtils.isEmpty(matchId)) {
            return null;
        } else {
            Match exist = matchRepository.findByMatchId(matchId);
            if (exist == null) {
                // 如果mongo中不存在就去pubg去查询
                fetchMatchAndPlayer(matchId);
                return matchRepository.findByMatchId(matchId);
            } else {
                return exist;
            }
        }
    }

    /**
     * @param matchId 玩家昵称
     * @return
     * @description 对局ID查找对局玩家信息
     * @auth sunpeikai
     */
    @Override
    @Cacheable(value = "match-players:matchId", key = "#matchId", unless = "#result.size()<=0")
    public List<MatchPlayer> findMatchPlayersByMatchId(String matchId) {

        log.info("no cached");
        return matchPlayerRepository.findByMatchId(matchId);
    }

    /**
     * @param matchId 对局ID
     * @return
     * @description 从pubg获取对局和玩家结算信息
     * @auth sunpeikai
     */
    private void fetchMatchAndPlayer(String matchId) {

        JSONObject result = PubgApi.useApi(PubgApiEnum.PUBG_API_MATCHES_ID).call(matchId);
        if (result != null && !result.isEmpty()) {
            // 对局
            Match match = new Match();
            // 个人信息
            List<MatchPlayer> matchPlayers = new ArrayList<>();
            // 个人映射队伍
            Map<String, String> teamMap = new HashMap<>();
            // 返回不为空
            JSONObject data = result.getJSONObject("data");
            if (data != null && !data.isEmpty()) {
                // 获取对局基本信息
                JSONObject matchInfo = data.getJSONObject("attributes");
                match.setMatchId(matchId);
                match.setDuration(matchInfo.getInteger("duration"));
                match.setCustomMatch(matchInfo.getBoolean("isCustomMatch") ? 1 : 0);
                match.setMapName(matchInfo.getString("mapName"));
                match.setGameMode(matchInfo.getString("gameMode"));
                match.setFetchLog(ApiConstant.MATCH_FETCH_LOG_NO);
                match.setCreateTime(DateUtil.add8Hours(DateUtil.getFromStr(matchInfo.getString("createdAt"))));
            }

            JSONArray array = result.getJSONArray("included");
            if (array != null && !array.isEmpty()) {
                // 获取对局个人信息
                int arraySize = array.size();
                for (int i = 0; i < arraySize; i++) {
                    JSONObject include = array.getJSONObject(i);
                    if (ApiConstant.MATCH_INCLUDE_PARTICIPANT.equals(include.getString("type"))) {
                        JSONObject stats = include.getJSONObject("attributes").getJSONObject("stats");
                        if (stats != null && !stats.isEmpty()) {
                            // 个人信息
                            MatchPlayer matchPlayer = JSONObject.parseObject(stats.toJSONString(), MatchPlayer.class);
                            matchPlayer.setMatchId(matchId);
                            matchPlayer.setMatchTime(match.getCreateTime());
                            matchPlayer.setAccountId(stats.getString("playerId"));
                            matchPlayer.setPlayerName(stats.getString("name"));
                            matchPlayer.setMatchPlayerId(include.getString("id"));
                            matchPlayers.add(matchPlayer);
                        }
                    } else if (ApiConstant.MATCH_INCLUDE_ROSTER.equals(include.getString("type"))) {
                        // 队伍信息
                        String teamId = include.getString("id");
                        JSONArray participants = include.getJSONObject("relationships").getJSONObject("participants").getJSONArray("data");
                        if (participants != null && !participants.isEmpty()) {
                            int participantLength = participants.size();
                            for (int j = 0; j < participantLength; j++) {
                                String participantId = participants.getJSONObject(j).getString("id");
                                if (!teamMap.containsKey(participantId)) {
                                    teamMap.put(participantId, teamId);
                                }
                            }
                        }
                    } else if (ApiConstant.MATCH_INCLUDE_ASSET.equals(include.getString("type"))) {
                        // 对局日志信息
                        match.setAssetsUrl(include.getJSONObject("attributes").getString("URL"));
                    }
                }
            }
            // 处理玩家与团队的映射关系
            matchPlayers.forEach(matchPlayer -> {
                matchPlayer.setTeamId(teamMap.getOrDefault(matchPlayer.getMatchPlayerId(), ""));
            });
            // 检查是否存在
            if (!matchRepository.isExistMatch(matchId)) {
                // 将play和match数据插入mongo
                matchRepository.insert(match);
                matchPlayerRepository.insertAll(matchPlayers);
            }

        }
    }

    /**
     * @param
     * @return
     * @description 用账户ID或昵称查询对应的对局列表
     * 先从matchPlayer查询用户获得matchId,再去match查询比赛
     * @auth sunpeikai
     */
    private Page<MatchResponse> findMatchPageByAccountIdOrPlayerName(MatchRequest request) {

        Query query = new Query();


        if(StringUtils.isEmpty(request.getAccountId()) && StringUtils.isEmpty(request.getPlayerName())){
            // 如果参数都为空 - 直接查询match表
            query.with(new Sort(Sort.Direction.DESC,"createTime"));
            return matchRepository.page(query,request.getPage(),request.getLimit()).convert(Match::getResponse);
        }else{
            // 构建查询条件
            Criteria criteria = new Criteria();
            // 如果有一个参数不为空 - 先查询matchPlayer再查询match
            if (!StringUtils.isEmpty(request.getAccountId())) {
                criteria.and("accountId").is(request.getAccountId());
            }
            if (!StringUtils.isEmpty(request.getPlayerName())) {
                criteria.and("playerName").is(request.getPlayerName());
            }
            query.addCriteria(criteria).with(new Sort(Sort.Direction.DESC,"matchTime"));
            Page<MatchPlayer> matchPlayers = matchPlayerRepository.page(query, request.getPage(), request.getLimit());
            return matchPlayers.convert(matchPlayer -> matchRepository.findByMatchId(matchPlayer.getMatchId()).getResponse(matchPlayer.getPlayerName()));
        }

    }

}
