/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.api.enums;

/**
 * @author sunpeikai
 * @version PubgApiEnum, v0.1 2020/7/10 17:03
 * @description
 */
public enum  PubgApiEnum {

    // 玩家
    PUBG_API_PLAYER_NAME(1,"https://api.pubg.com/shards/steam/players?filter[playerNames]="),
    PUBG_API_PLAYER_ACCOUNT(1,"https://api.pubg.com/shards/steam/players/"),

    // 对局
    PUBG_API_MATCHES_ID(1,"https://api.pubg.com/shards/steam/matches/"),

    // 枚举终结
    PUBG_API_END(0,"")
    ;

    PubgApiEnum(int method, String url) {
        this.method = method;
        this.url = url;
    }

    // 请求方式 - 1:GET,2:POST,3:OPTIONS
    private int method;
    private String url;

    public int getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }
}
