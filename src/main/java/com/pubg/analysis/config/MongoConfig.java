/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.config;

import com.mongodb.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author sunpeikai
 * @version MongoConfig, v0.1 2020/7/10 12:42
 * @description
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({MongoConfigProperties.class})
@ConditionalOnProperty(
        name = {"mongodb.host","mongodb.port"}
)
public class MongoConfig {

    @Autowired
    private ApplicationContext appContext;

    public MongoConfig() {
    }

    @Bean
    MongoDbFactory mongoDbFactory(MongoConfigProperties mongoConfig) {
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(mongoConfig.getConnectionsPerHost());
        builder.minConnectionsPerHost(mongoConfig.getMinConnectionsPerHost());
        builder.connectTimeout(mongoConfig.getConnectTimeout());
        builder.maxWaitTime(mongoConfig.getMaxWaitTime());
        builder.socketTimeout(mongoConfig.getSocketTimeout());
        builder.readPreference(ReadPreference.primaryPreferred());
        MongoClientOptions mongoClientOptions = builder.build();
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(mongoConfig.getUsername(), mongoConfig.getAuthenticationDatabase(), mongoConfig.getPassword().toCharArray());
        MongoClient mongoClient = new MongoClient(new ServerAddress(mongoConfig.getHost(), mongoConfig.getPort()), mongoCredential, mongoClientOptions);
        return new SimpleMongoDbFactory(mongoClient, mongoConfig.getDatabase());
    }

    @Bean
    public MongoTemplate mongoTemplate(@Qualifier("mongoDbFactory") MongoDbFactory mongoDbFactory) {
        MongoMappingContext mongoMappingContext = new MongoMappingContext();
        mongoMappingContext.setApplicationContext(this.appContext);
        MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), mongoMappingContext);
        converter.setTypeMapper(new DefaultMongoTypeMapper((String)null));
        log.info("Mongodb init OK");
        return new MongoTemplate(mongoDbFactory, converter);
    }
}
