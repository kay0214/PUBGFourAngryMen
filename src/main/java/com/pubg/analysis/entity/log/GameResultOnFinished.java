/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.entity.log;

import lombok.Data;

import java.util.List;

/**
 * @author sunpeikai
 * @version GameResultOnFinished, v0.1 2020/7/14 10:29
 * @description
 */
@Data
public class GameResultOnFinished {
    private List<GameResult> results;
}
