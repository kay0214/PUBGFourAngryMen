/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author sunpeikai
 * @version PUBGConfig, v0.1 2020/7/10 16:53
 * @description
 */
@Component
public class PUBGConfig {

    // api key
    public static String apiKey;

    @PostConstruct
    public void init(){
        System.out.println("properties init");
    }

    @Value("${pubg.api.key}")
    public void setApiKey(String apiKey) {
        PUBGConfig.apiKey = apiKey;
    }

}
