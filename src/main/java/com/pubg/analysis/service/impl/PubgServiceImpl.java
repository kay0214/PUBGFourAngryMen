package com.pubg.analysis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.api.PubgApi;
import com.pubg.analysis.api.enums.PubgApiEnum;
import com.pubg.analysis.base.Page;
import com.pubg.analysis.constants.ApiConstant;
import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.PlayerPositionLog;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import com.pubg.analysis.repository.LogRepository;
import com.pubg.analysis.repository.MatchRepository;
import com.pubg.analysis.repository.MatchPlayerRepository;
import com.pubg.analysis.request.MatchRequest;
import com.pubg.analysis.response.MatchResponse;
import com.pubg.analysis.response.PositionResponse;
import com.pubg.analysis.service.IPubgService;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.PubgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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
    public PositionResponse getPubgLocation(String matchId) {

        log.info("获取LogPlayerPosition");
        List<String> type = Collections.singletonList("LogPlayerPosition");
        List<PlayerPositionLog> positionLogs = logRepository.getLog(PlayerPositionLog.class, matchId, type);

        TreeMap<Integer, List<PlayerPositionLog>> logs = new TreeMap<>();
        positionLogs
                .parallelStream()
                //计算位置
                .peek(e -> PubgUtil.calculateLocationRation(e.getCharacter().getLocation(), PubgConstant.Maps.SANHOK))
                //按时间分组
                .collect(Collectors.groupingByConcurrent(PlayerPositionLog::getElapsedTime))

                //每组内按用户去重
                .entrySet()
                .parallelStream()
                .peek(entry -> {
                    List<PlayerPositionLog> records = entry.getValue()
                            .stream()
                            //按角色分组
                            .collect(Collectors.groupingBy(v -> v.getCharacter().getAccountId()))
                            .entrySet()
                            .parallelStream()
                            .peek(value -> {
                                //只保留一个元素
                                List<PlayerPositionLog> same = value.getValue();
                                if (!same.isEmpty()) {
                                    value.setValue(Collections.singletonList(same.get(0)));
                                }
                            })
                            .map(Map.Entry::getValue)
                            .flatMap(List::stream)
                            .collect(Collectors.toList());
                    entry.setValue(records);

                })

                //添加到结果
                .forEachOrdered(e -> logs.put(e.getKey(), e.getValue()));


        log.debug("读取到LogPlayerPosition: {}", logs);

        PositionResponse response = new PositionResponse();
        response.setPositions(logs);
        if (logs.size() > 0) {
            response.setStart(logs.firstKey());
            response.setEnd(logs.lastKey());
        }
        return response;
    }

    /**
     * @description accountId查找对局列表
     * @auth sunpeikai
     * @param request 玩家账户ID
     * @return
     */
    @Override
    public Page<Match> findMatchPageByAccountId(MatchRequest request) {
        return findMatchPageByAccountId(false,request);
    }

    /**
     * @description accountId查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param request 玩家账户ID
     * @return
     */
    @Override
    public Page<Match> findMatchPageByAccountId(boolean fromRemote, MatchRequest request) {
        if(fromRemote){
            // 从pubg官网重新拉取数据
            updateMatchesFromRemote(request.getAccountId(),"");
        }
        return findMatchPageByAccountIdOrPlayerName(request);
    }

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param request 玩家昵称
     * @return
     */
    @Override
    public Page<Match> findMatchPageByPlayerName(MatchRequest request) {
        return findMatchPageByPlayerName(false,request);
    }

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param request 玩家昵称
     * @return
     */
    @Override
    public Page<Match> findMatchPageByPlayerName(boolean fromRemote, MatchRequest request) {
        if(fromRemote){
            // 从pubg官网重新拉取数据
            updateMatchesFromRemote("",request.getPlayerName());
        }
        return findMatchPageByAccountIdOrPlayerName(request);
    }

    /**
     * @description 从pubg拉取对局列表
     * @auth sunpeikai
     * @param
     * @return
     */
    private void updateMatchesFromRemote(String accountId, String playerName){
        JSONObject result = new JSONObject();
        if(!StringUtils.isEmpty(accountId)){
            result = PubgApi.useApi(PubgApiEnum.PUBG_API_PLAYER_ACCOUNT).call(accountId);
        }else if(!StringUtils.isEmpty(playerName)){
            result = PubgApi.useApi(PubgApiEnum.PUBG_API_PLAYER_NAME).call(playerName);
        }
        List<String> matches = new ArrayList<>();
        // 处理数据
        if(result != null && !result.isEmpty()){
            // 返回不为空
            JSONArray data = result.getJSONArray("data");
            if(data != null && !data.isEmpty()){
                // 返回data不为空
                int length = data.size();
                for(int i=0 ; i<length ; i++){
                    JSONObject player = data.getJSONObject(i);
                    // 对局列表
                    JSONArray remoteMatches = player.getJSONObject("relationships").getJSONObject("matches").getJSONArray("data");
                    // 对局列表数量
                    int matchLength = remoteMatches.size();
                    for(int j=0;j<matchLength;j++){
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
     * @description 对局ID查找对局详情
     * @auth sunpeikai
     * @param matchId 对局ID
     * @return
     */
    @Override
    public Match findMatchByMatchId(String matchId) {
        // TODO:sunpeikai 对局详情请求实现
        if(StringUtils.isEmpty(matchId)){
            return null;
        }else{
            Match exist = matchRepository.findByMatchId(matchId);
            if(exist == null){
                // 如果mongo中不存在就去pubg去查询
                fetchMatchAndPlayer(matchId);
                return matchRepository.findByMatchId(matchId);
            }else{
                return exist;
            }
        }
    }

    /**
     * @description 对局ID查找对局玩家信息
     * @auth sunpeikai
     * @param matchId 玩家昵称
     * @return
     */
    @Override
    public List<MatchPlayer> findMatchPlayersByMatchId(String matchId) {
        return matchPlayerRepository.findByMatchId(matchId);
    }

    /**
     * @description 从pubg获取对局和玩家结算信息
     * @auth sunpeikai
     * @param matchId 对局ID
     * @return
     */
    private void fetchMatchAndPlayer(String matchId){
        JSONObject result = PubgApi.useApi(PubgApiEnum.PUBG_API_MATCHES_ID).call(matchId);
        if(result != null && !result.isEmpty()){
            // 对局
            Match match = new Match();
            // 个人信息
            List<MatchPlayer> matchPlayers = new ArrayList<>();
            // 个人映射队伍
            Map<String,String> teamMap = new HashMap<>();
            // 返回不为空
            JSONObject data = result.getJSONObject("data");
            if(data != null && !data.isEmpty()){
                // 获取对局基本信息
                JSONObject matchInfo = data.getJSONObject("attributes");
                match.setMatchId(matchId);
                match.setDuration(matchInfo.getInteger("duration"));
                match.setCustomMatch(matchInfo.getBoolean("isCustomMatch")?1:0);
                match.setMapName(matchInfo.getString("mapName"));
                match.setGameMode(matchInfo.getString("gameMode"));
                match.setFetchLog(ApiConstant.MATCH_FETCH_LOG_NO);
                match.setCreateTime(DateUtil.getFromStr(matchInfo.getString("createdAt")));
            }

            JSONArray array = result.getJSONArray("included");
            if(array != null && !array.isEmpty()){
                // 获取对局个人信息
                int arraySize = array.size();
                for(int i=0 ; i<arraySize ; i++){
                    JSONObject include = array.getJSONObject(i);
                    if(ApiConstant.MATCH_INCLUDE_PARTICIPANT.equals(include.getString("type"))){
                        JSONObject stats = include.getJSONObject("attributes").getJSONObject("stats");
                        if(stats != null && !stats.isEmpty()){
                            // 个人信息
                            MatchPlayer matchPlayer = JSONObject.parseObject(stats.toJSONString(),MatchPlayer.class);
                            matchPlayer.setMatchId(matchId);
                            matchPlayer.setAccountId(stats.getString("playerId"));
                            matchPlayer.setPlayerName(stats.getString("name"));
                            matchPlayer.setMatchPlayerId(include.getString("id"));
                            matchPlayers.add(matchPlayer);
                        }
                    }else if(ApiConstant.MATCH_INCLUDE_ROSTER.equals(include.getString("type"))){
                        // 队伍信息
                        String teamId = include.getString("id");
                        JSONArray participants = include.getJSONObject("relationships").getJSONObject("participants").getJSONArray("data");
                        if(participants != null && !participants.isEmpty()){
                            int participantLength = participants.size();
                            for(int j=0 ; j<participantLength ; j++){
                                String participantId = participants.getJSONObject(j).getString("id");
                                if(!teamMap.containsKey(participantId)){
                                    teamMap.put(participantId,teamId);
                                }
                            }
                        }
                    }else if(ApiConstant.MATCH_INCLUDE_ASSET.equals(include.getString("type"))){
                        // 对局日志信息
                        match.setAssetsUrl(include.getJSONObject("attributes").getString("URL"));
                    }
                }
            }
            // 处理玩家与团队的映射关系
            matchPlayers.forEach(matchPlayer -> {
                matchPlayer.setTeamId(teamMap.getOrDefault(matchPlayer.getMatchPlayerId(), ""));
            });
            // 将play和match数据插入mongo
            matchRepository.insert(match);
            matchPlayerRepository.insertAll(matchPlayers);
        }
    }

    /**
     * @description 用账户ID或昵称查询对应的对局列表
     *     先从matchPlayer查询用户获得matchId,再去match查询比赛
     * @auth sunpeikai
     * @param
     * @return
     */
    private Page<Match> findMatchPageByAccountIdOrPlayerName(MatchRequest request){
        Query query = new Query();
        // 构建查询条件
        Criteria criteria = new Criteria();
        if(!StringUtils.isEmpty(request.getAccountId())){
            criteria.and("accountId").is(request.getAccountId());
        }
        if(!StringUtils.isEmpty(request.getPlayerName())){
            criteria.and("playerName").is(request.getPlayerName());
        }
        query.addCriteria(criteria);
        Page<MatchPlayer> matchPlayers = matchPlayerRepository.page(query,request.getCurrPage(),request.getPageSize());
        return matchPlayers.convert(matchPlayer -> matchRepository.findByMatchId(matchPlayer.getMatchId()));
    }
}
