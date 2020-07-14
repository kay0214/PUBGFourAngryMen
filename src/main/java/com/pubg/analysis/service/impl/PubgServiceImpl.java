package com.pubg.analysis.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.api.PubgApi;
import com.pubg.analysis.api.enums.PubgApiEnum;
import com.pubg.analysis.constants.PubgConstant;
import com.pubg.analysis.entity.log.PlayerPositionLog;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.entity.matchs.MatchPlayer;
import com.pubg.analysis.repository.LogRepository;
import com.pubg.analysis.repository.MatchRepository;
import com.pubg.analysis.repository.PlayerMatchRepository;
import com.pubg.analysis.response.PositionResponse;
import com.pubg.analysis.service.IPubgService;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.PubgUtil;
import lombok.extern.slf4j.Slf4j;
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
    private PlayerMatchRepository playerMatchRepository;


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
     * @param accountId 玩家账户ID
     * @return
     */
    @Override
    public List<Match> findByAccountId(String accountId) {
        return findByAccountId(false,accountId);
    }

    /**
     * @description accountId查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param accountId 玩家账户ID
     * @return
     */
    @Override
    public List<Match> findByAccountId(boolean fromRemote, String accountId) {
        if(fromRemote){
            // 从pubg官网重新拉取数据
            updateMatchesFromRemote(accountId,"");
        }
        List<MatchPlayer> result = new ArrayList<>();
        return null;
    }

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param playerName 玩家昵称
     * @return
     */
    @Override
    public List<Match> findByPlayerName(String playerName) {
        return findByPlayerName(false,playerName);
    }

    /**
     * @description 玩家昵称查找对局列表
     * @auth sunpeikai
     * @param fromRemote 从pubg官网更新
     * @param playerName 玩家昵称
     * @return
     */
    @Override
    public List<Match> findByPlayerName(boolean fromRemote, String playerName) {
        if(fromRemote){
            // 从pubg官网重新拉取数据
            updateMatchesFromRemote("",playerName);
        }
        return null;
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
        if(!result.isEmpty()){
            // 返回不为空
            JSONArray data = result.getJSONArray("data");
            if(!data.isEmpty()){
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
            Match exist = findByMatchId(matchId);
            if (exist != null) {
                break;
            }
        }
    }

    /**
     * @description 对局ID查找对局详情
     * @auth sunpeikai
     * @param matchId 玩家昵称
     * @return
     */
    @Override
    public Match findByMatchId(String matchId) {
        if(StringUtils.isEmpty(matchId)){
            return null;
        }else{
            Match exist = matchRepository.findByMatchId(matchId);
            if(exist == null){
                // 如果mongo中不存在就去pubg去查询
                JSONObject result = PubgApi.useApi(PubgApiEnum.PUBG_API_MATCHES_ID).call(matchId);
                if(!result.isEmpty()){
                    // 返回不为空
                    JSONObject data = result.getJSONObject("data");
                    if(!data.isEmpty()){
                        Match match = new Match();
                        // 获取对局基本信息
                        JSONObject matchInfo = data.getJSONObject("attributes");
                        match.setMatchId(matchId);
                        match.setDuration(matchInfo.getInteger("duration"));
                        match.setCustomMatch(matchInfo.getBoolean("isCustomMatch")?1:0);
                        match.setMapName(matchInfo.getString("mapName"));
                        match.setGameMode(matchInfo.getString("gameMode"));
                        match.setCreateTime(DateUtil.getFromStr(matchInfo.getString("createdAt")));
                    }
                }
                // 将列表插入
                // playerMatchRepository.insert(matchPlayer);
            }else{
                // mongo中存在
                return exist;
            }
        }
        return null;
    }

}
