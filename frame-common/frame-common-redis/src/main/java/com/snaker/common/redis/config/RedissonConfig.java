package com.snaker.common.redis.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class RedissonConfig {

    @Value("${spring.redis.host}")
    private String redisHost;

    @Value("${spring.redis.port}")
    private String redisPort;

    @Value("${spring.redis.database}")
    private Integer redisDatabase;

    @Bean
    public Config config() {
        Config config = new Config();
        SingleServerConfig singleServerConfig = config.useSingleServer()
                .setAddress(String.format("redis://%s:%s", redisHost, redisPort))
                .setDatabase(redisDatabase)
                .setConnectionMinimumIdleSize(10)
                .setConnectionPoolSize(60)
                .setSubscriptionConnectionPoolSize(60)
                .setIdleConnectionTimeout(5000)
                .setTimeout(5000)
                .setConnectTimeout(5000)
                .setSslEnableEndpointIdentification(false);
        return config;
    }

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redisson() {
        RedissonClient redissonClient = Redisson.create(config());
        return redissonClient;
    }


}
