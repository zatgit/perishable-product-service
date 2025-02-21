package com.zmart.api.cache;

import io.lettuce.core.RedisConnectionException;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

@Slf4j
@PropertySource("redis-local.properties")
@Component
@ConditionalOnProperty(prefix = "local.redis.server", name = "embedded", havingValue = "on")
public class EmbeddedRedis {

    @Value("${spring.cache.redis.port:6379}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        log.info("[REDIS] Starting Redis");
        Boolean redisPortAvailable = false;
        final int maxPortRange = 9999;
        while (Boolean.FALSE.equals(redisPortAvailable) && redisPort <= maxPortRange) {
            try {
                redisServer = new RedisServer(redisPort);
                redisServer.start();
                redisPortAvailable = true;
                log.trace("[REDIS] Connecting to port " + redisPort);

            } catch (final Exception ex) {
                if (ex.getMessage().contains("bind: Address already in use")) {
                    log.trace("[REDIS] Port " + redisPort + " already in use");
                    redisPort++;
                } else {
                    throw new RedisConnectionException("Embedded Redis failed to start");
                }
            }
        }
    }

    @PreDestroy
    public void stopRedis() {
        redisServer.stop();
    }
}
