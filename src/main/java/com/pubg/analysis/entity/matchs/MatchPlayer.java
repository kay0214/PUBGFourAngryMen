/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.matchs;

import com.pubg.analysis.utils.ScaleUtil;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author sunpeikai
 * @version PlayerMatch, v0.1 2020/7/14 14:29
 * @description
 */
@Data
@Document(collection = "pubg_player_match")
@CompoundIndexes({
        @CompoundIndex(name = "matchId_accountId_playerName",def = "{'matchId':1,'accountId':1,'playerName':1}")
})
public class MatchPlayer implements Serializable {
    // 对局ID
    private String matchId;
    // 玩家ID
    private String accountId;
    // 玩家昵称
    private String playerName;
    // team id
    private String teamId;
    // matchPlayerId - 团队与个人映射关系
    private String matchPlayerId;
    // 个人击杀
    private Integer kills;
    // 个人爆头击杀
    private Integer headshotKills;
    // 团队排名
    private Integer winPlace;
    // 治疗次数
    private Integer heals;
    // 跑步移动距离
    private BigDecimal walkDistance;
    // 最远击杀距离
    private BigDecimal longestKill;
    // 摧毁载具
    private Integer vehicleDestroys;
    // 不知道什么意思
    private Integer DBNOs;
    // 驾车移动距离
    private BigDecimal rideDistance;
    // 死亡原因 - byplayer
    private String deathType;
    // 复活次数?还是扶起倒地队友次数 - 倾向后者
    private Integer revives;
    // 助攻数
    private Integer assists;
    // 不知道什么意思
    private Integer killPlace;
    // 连杀
    private Integer killStreaks;
    // 游泳移动距离
    private BigDecimal swimDistance;
    // 获得武器
    private Integer weaponsAcquired;
    // 开车撞死人数
    private Integer roadKills;
    // 加速数 - 饮料
    private Integer boosts;
    // 存活时间
    private BigDecimal timeSurvived;
    // 造成伤害
    private BigDecimal damageDealt;
    // 团队击杀
    private Integer teamKills;

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public void setWalkDistance(BigDecimal walkDistance) {
        this.walkDistance = ScaleUtil.threeScale(walkDistance);
    }

    public void setLongestKill(BigDecimal longestKill) {
        this.longestKill = ScaleUtil.threeScale(longestKill);
    }

    public void setRideDistance(BigDecimal rideDistance) {
        this.rideDistance = ScaleUtil.threeScale(rideDistance);
    }

    public void setSwimDistance(BigDecimal swimDistance) {
        this.swimDistance = ScaleUtil.threeScale(swimDistance);
    }

    public void setTimeSurvived(BigDecimal timeSurvived) {
        this.timeSurvived = ScaleUtil.threeScale(timeSurvived);
    }

    public void setDamageDealt(BigDecimal damageDealt) {
        this.damageDealt = ScaleUtil.threeScale(damageDealt);
    }
}
