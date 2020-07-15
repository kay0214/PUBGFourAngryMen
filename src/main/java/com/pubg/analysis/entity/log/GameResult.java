/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import lombok.Data;

/**
 * @author sunpeikai
 * @version GameResult, v0.1 2020/7/14 10:29
 * @description
 */
@Data
public class GameResult {

    private Integer rank;
    private String gameResult;
    private Integer teamId;
    private Stats stats;
    private String accountId;
}
