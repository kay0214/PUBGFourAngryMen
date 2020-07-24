/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.schedule;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.entity.log.BaseLog;
import com.pubg.analysis.entity.matchs.Match;
import com.pubg.analysis.repository.LogRepository;
import com.pubg.analysis.repository.MatchRepository;
import com.pubg.analysis.utils.DateUtil;
import com.pubg.analysis.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunpeikai
 * @version FetchLogSchedule, v0.1 2020/7/15 17:28
 * @description
 */
@Slf4j
@Component
public class FetchLogSchedule {

    @Resource
    private MatchRepository matchRepository;
    @Resource
    private LogRepository logRepository;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void fetchLogRemote(){
        long startTime = System.currentTimeMillis();
        List<Match> matches = matchRepository.findMatchToFetch();
        log.info("Fetch match log from pubg remote start,fetch count is [{}]",matches.size());
        for(Match match : matches){
            String matchId = match.getMatchId();
            String url = match.getAssetsUrl();
            if(logRepository.isExistMatchLog(matchId)){
                // 判断是否已经入库过,避免重复入库
                log.error("This match is already put into the mongoDB,matchId is [{}]",matchId);
                // 更新状态
                matchRepository.updateFetchLogStatus(matchId);
            }else{
                log.info("Fetch match log from pubg remote,matchId is [{}],url is [{}]",matchId,url);
                String result = HttpUtil.sendGetGZIP(url);
                if(StringUtils.isEmpty(result)){
                    log.error("Fetch match log from pubg remote error,it could be timeout or return null,matchId is [{}],url is [{}]",matchId,url);
                }else{
                    JSONArray array = JSONObject.parseArray(result);
                    List<BaseLog> baseLogs = array
                            .parallelStream()
                            .map(e -> {
                                JSONObject json = (JSONObject) e;
                                BaseLog baseLog = json.toJavaObject(BaseLog.class);
                                baseLog.set_D(DateUtil.add8Hours(baseLog.get_D()));
                                baseLog.setMatchId(matchId);
                                return baseLog;
                            })
                            .collect(Collectors.toList());
                    // 插入对局日志
                    logRepository.insertAll(baseLogs);
                    // 更新状态
                    matchRepository.updateFetchLogStatus(matchId);
                }
            }

        }
        log.info("Fetch match log from pubg remote end,fetch count is [{}],elapsed time is [{}]",matches.size(),(System.currentTimeMillis() - startTime)/1000 + "s");
    }
}
