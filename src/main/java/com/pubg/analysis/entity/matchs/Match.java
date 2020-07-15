/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.matchs;

import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

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
public class Match {
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
}
