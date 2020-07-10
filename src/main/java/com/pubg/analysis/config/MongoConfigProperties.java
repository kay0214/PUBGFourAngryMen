/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author sunpeikai
 * @version MongoConfigProperties, v0.1 2020/7/10 12:42
 * @description
 */
@Data
@ConfigurationProperties(prefix = "mongodb")
public class MongoConfigProperties {

    private String host;
    private Integer port;
    private String username;
    private String password;
    private String authenticationDatabase;
    private String database;
    private Integer minConnectionsPerHost = 10;
    private Integer connectionsPerHost = 100;
    private Integer connectTimeout;
    private Integer maxWaitTime;
    private Integer socketTimeout;
}
