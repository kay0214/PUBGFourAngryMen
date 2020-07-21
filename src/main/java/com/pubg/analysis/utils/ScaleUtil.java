/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.utils;

import java.math.BigDecimal;

/**
 * @author sunpeikai
 * @version ScaleUtil, v0.1 2020/7/21 17:44
 * @description
 */
public class ScaleUtil {

    public static BigDecimal threeScale(BigDecimal number){
        return number.setScale(3,BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal twoScale(BigDecimal number){
        return number.setScale(2,BigDecimal.ROUND_DOWN);
    }
}
