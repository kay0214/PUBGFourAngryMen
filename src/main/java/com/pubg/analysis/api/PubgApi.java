/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.api;

import com.alibaba.fastjson.JSONObject;
import com.pubg.analysis.api.enums.PubgApiEnum;
import com.pubg.analysis.constants.ApiConstant;
import com.pubg.analysis.utils.HttpUtil;
import org.springframework.util.StringUtils;

/**
 * @author sunpeikai
 * @version PubgApi, v0.1 2020/7/10 17:01
 * @description
 */
public class PubgApi {

    private final PubgApiEnum apiEnum;

    private PubgApi(PubgApiEnum apiEnum) {
        this.apiEnum = apiEnum;
    }

    public static PubgApi useApi(PubgApiEnum apiEnum){
        return new PubgApi(apiEnum);
    }

    /**
     * 暂时不用
     * */
    @Deprecated
    public <T> T call(Class<T> cls){
        return null;
    }

    public JSONObject call(String urlParam){
        String result = sendApi(urlParam,"");
        return JSONObject.parseObject(result);
    }

    public <T> T call(String urlParam, Class<T> cls){
        String result = sendApi(urlParam,"");
        return JSONObject.parseObject(result,cls);
    }

    /**
     * 暂时不用
     * */
    @Deprecated
    public <T> T call(Object t, Class<T> cls){
        return null;
    }

    /**
     * 暂时不用
     * */
    @Deprecated
    public <T> T call(Object t, String param, Class<T> cls){
        return null;
    }

    private String sendApi(String urlParam, String content){
        if(ApiConstant.API_REQUEST_SEND_GET.equals(apiEnum.getMethod())){
            String url = StringUtils.isEmpty(urlParam) ? apiEnum.getUrl() : apiEnum.getUrl() + urlParam;
            return HttpUtil.sendGet(url,content);
        }else{
            // 发送post请求
            String url = StringUtils.isEmpty(urlParam) ? apiEnum.getUrl() : apiEnum.getUrl() + urlParam;
            // HttpUtil.sendPost(url,content);
            return "";
        }
    }
}
