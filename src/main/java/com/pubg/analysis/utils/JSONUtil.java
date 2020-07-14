/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunpeikai
 * @version JSONUtil, v0.1 2020/7/14 15:32
 * @description
 */
@Deprecated
public class JSONUtil {

    private static final String FORMAT_OBJECT = "#";
    private static final String FORMAT_ARRAY = "@";
    private static final String FORMAT_DOT = ".";

    public static String getObjExpWithDot(String content){
        return getObjExp(content) + FORMAT_DOT;
    }
    public static String getObjExp(String content){
        return FORMAT_OBJECT + content;
    }
    private static String getArrayExpWithDot(String content){
        return getArrayExp(content) + FORMAT_DOT;
    }
    private static String getArrayExp(String content){
        return FORMAT_ARRAY + content;
    }

    /**
     * @description 从json中获取表达式样式的值
     * @auth sunpeikai
     * @param content jsonString
     * @param expression 表达式
     * @return
     */
    public static String getFromJson(String content, String expression){
        String exp;
        if((exp = getNextExp(expression)) != null){
            String type = exp.substring(0,1);
            String express = exp.substring(1);
            if(type.equals(FORMAT_OBJECT)){
                JSONObject json = JSONObject.parseObject(content);
                if(json != null && json.containsKey(express)){
                    content = json.getString(express);
                }else {
                    return "";
                }
                return getFromJson(content,expression);
            }else if(type.equals(FORMAT_ARRAY)){
                // 功能有问题
                // content = JSONObject.parseArray(content);
                // return getFromJson();
                return null;
            }else{
                return null;
            }
        }else{
            return content;
        }
    }

    private static String getNextExp(String expression){
        String expStr = expression;
        if(StringUtils.isEmpty(expStr)){
            return null;
        }else{
            if(expStr.contains(FORMAT_DOT)){
                // 不是最后一次匹配
                List<String> exp = Arrays.asList(expStr.split(FORMAT_DOT));
                StringBuilder handleStr = new StringBuilder();
                int length = exp.size();
                for(int i=1;i<length;i++){
                    if(i == (length-1)){
                        handleStr.append(exp.get(i));
                    }else{
                        handleStr.append(exp.get(i)).append(FORMAT_DOT);
                    }
                }
                // 赋值给expression
                expression = handleStr.toString();
                return trimExp(exp.get(0));
            }else{
                // 是最后一次匹配
                expression = null;
                return trimExp(expStr);
            }

        }
    }

    private static String trimExp(String exp){
        return (exp.startsWith(FORMAT_OBJECT) || exp.startsWith(FORMAT_ARRAY)) ? exp.trim() : null;
    }


}
