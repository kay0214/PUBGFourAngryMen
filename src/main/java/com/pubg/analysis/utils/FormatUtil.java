/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.utils;

/**
 * @author sunpeikai
 * @version FormatUtil, v0.1 2020/7/22 10:59
 * @description
 */
public class FormatUtil {

    private static final int SECOND = 60;
    private static final String FORMAT_MINUTE = "分";
    private static final String FORMAT_SECOND = "秒";
    public static String formatDuration(int duration){
        String result = "";
        int minutes = duration / SECOND;
        if(minutes == 0){
            result = duration + FORMAT_SECOND;
        }else{
            int second = duration % SECOND;
            result = minutes + FORMAT_MINUTE + second + FORMAT_SECOND;
        }
        return result;
    }
}
