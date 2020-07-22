/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.matchs;

import com.pubg.analysis.response.MatchResponse;
import com.pubg.analysis.utils.DateUtil;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sunpeikai
 * @version Match, v0.1 2020/7/14 17:11
 * @description
 */
@Data
@Document(collection = "pubg_match")
@CompoundIndexes({
        @CompoundIndex(name = "matchId",def = "{'matchId':1}"),
        @CompoundIndex(name = "fetchLog",def = "{'fetchLog':1}")
})
public class Match implements Serializable {
    // 对局ID
    private String matchId;
    // 持续时间
    private Integer duration;
    // 是否自定义对局
    private Integer customMatch;
    // 地图名称
    private String mapName;
    // 模式 - squad - 四排
    private String gameMode;
    // 是否已经拉取了对局日志
    private Integer fetchLog;
    // 对局日志URL
    private String assetsUrl;
    // 对局创建时间
    private Date createTime;

    public MatchResponse getResponse(){
        // 格式化处理
        MatchResponse response = new MatchResponse();
        response.setMatchId(this.getMatchId());
        response.setDuration(this.getDuration());
        response.setDurationStr(this.getDuration() == null ? "0":this.getDuration().toString());
        response.setCustomMatch(this.getCustomMatch());
        response.setCustomMatchStr(null != this.getCustomMatch() && this.getCustomMatch()==1?"是":"否");
        response.setMapName(this.getMapName());
        response.setGameMode(this.getGameMode());
        response.setFetchLog(this.getFetchLog());
        response.setFetchLogStr(this.getFetchLog()==1?"已拉取":"未拉取");
        response.setAssetsUrl(this.getAssetsUrl());
        response.setCreateTime(this.getCreateTime() != null ? DateUtil.formatDateTime(this.getCreateTime()) : "");
        return response;
    }

    public MatchResponse getResponse(String playerName){
        // 格式化处理
        MatchResponse response = new MatchResponse();
        response.setMatchId(this.getMatchId());
        response.setPlayerName(playerName);
        response.setDuration(this.getDuration());
        response.setDurationStr(this.getDuration() == null ? "0":this.getDuration().toString());
        response.setCustomMatch(this.getCustomMatch());
        response.setCustomMatchStr(null != this.getCustomMatch() && this.getCustomMatch()==1?"是":"否");
        response.setMapName(this.getMapName());
        response.setGameMode(this.getGameMode());
        response.setFetchLog(this.getFetchLog());
        response.setFetchLogStr(this.getFetchLog()==1?"已拉取":"未拉取");
        response.setAssetsUrl(this.getAssetsUrl());
        response.setCreateTime(this.getCreateTime() != null ? DateUtil.formatDateTime(this.getCreateTime()) : "");
        return response;
    }
}
