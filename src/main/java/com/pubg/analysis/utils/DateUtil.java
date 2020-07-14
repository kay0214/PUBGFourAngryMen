/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sunpeikai
 * @version DateUtil, v0.1 2020/7/10 13:27
 * @description
 */
public class DateUtil {

    // 格式化
    private static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final String GET_FROM = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    /**
     * @description string -> date
     * @auth sunpeikai
     * @param
     * @return
     */
    public static Date getFromStr(String date){
        try{
            // 2020-07-12T14:56:52Z
            SimpleDateFormat format = new SimpleDateFormat(GET_FROM);
            return format.parse(date);
        }catch (Exception e){
            return null;
        }
    }

    /**
     * @description 格式化时间
     * @auth sunpeikai
     * @param date 时间
     * @return
     */
    public static String formatDateTime(Date date){
        return format(date,YYYY_MM_DD_HH_MM_SS);
    }

    /**
     * @description 格式化时间
     * @auth sunpeikai
     * @param date 时间
     * @param pattern 格式化
     * @return
     */
    public static String formatDateTime(Date date,String pattern){
        return format(date,pattern);
    }

    /**
     * @description 格式化日期
     * @auth sunpeikai
     * @param date 时间
     * @return
     */
    public static String formatDate(Date date){
        return format(date,YYYY_MM_DD);
    }

    /**
     * @description 格式化日期
     * @auth sunpeikai
     * @param date 时间
     * @param pattern 格式化
     * @return
     */
    public static String formatDate(Date date,String pattern){
        return format(date,pattern);
    }

    /**
     * @description 通用格式化
     * @auth sunpeikai
     * @param date 时间
     * @param pattern 格式化
     * @return
     */
    public static String format(Date date,String pattern){
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(date);
    }
}
