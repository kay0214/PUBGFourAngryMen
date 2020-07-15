/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.constants;

/**
 * @author sunpeikai
 * @version ApiConstant, v0.1 2020/7/10 17:37
 * @description
 */
public class ApiConstant {

    /**
     * 请求方式 - GET
     * */
    public static final Integer API_REQUEST_SEND_GET = 1;

    /**
     * 请求方式 - POST
     * */
    public static final Integer API_REQUEST_SEND_POST = 2;

    /**
     * match对局的include类型 - roster
     * */
    public static final String MATCH_INCLUDE_ROSTER = "roster";

    /**
     * match对局的include类型 - participant
     * */
    public static final String MATCH_INCLUDE_PARTICIPANT = "participant";

    /**
     * match对局的include类型 - asset
     * */
    public static final String MATCH_INCLUDE_ASSET = "asset";

    /**
     * match对局是否已经拉取了对局日志 - 是
     * */
    public static final Integer MATCH_FETCH_LOG_YES = 1;

    /**
     * match对局是否已经拉取了对局日志 - 否
     * */
    public static final Integer MATCH_FETCH_LOG_NO = 0;

    /**
     * match对局拉取对局日志数量 - 测试72秒钟拉取5条
     * */
    public static final Integer MATCH_FETCH_LOG_COUNT = 4;
}
