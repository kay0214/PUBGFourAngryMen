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
import com.pubg.analysis.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
        List<Match> matches = matchRepository.findMatchToFetch();
        log.info("本次拉取对局日志start,count[{}]",matches.size());
        for(Match match : matches){
            String matchId = match.getMatchId();
            String url = match.getAssetsUrl();
            log.info("拉取对局日志,matchId[{}],url[{}]",matchId,url);
            String result = HttpUtil.sendGetGZIP(url);
            JSONArray array = JSONObject.parseArray(result);
            List<BaseLog> baseLogs = array
                    .parallelStream()
                    .map(e -> {
                        JSONObject json = (JSONObject) e;
                        BaseLog baseLog = json.toJavaObject(BaseLog.class);
                        baseLog.setMatchLogId(baseLog.getMatchId());
                        baseLog.setMatchId(matchId);
                        return baseLog;
                    })
                    .collect(Collectors.toList());
            // 插入对局日志
            logRepository.insertAll(baseLogs);
            // 更新状态
            matchRepository.updateFetchLogStatus(matchId);
        }
        log.info("本次拉取对局日志end,count[{}]",matches.size());
    }
}
