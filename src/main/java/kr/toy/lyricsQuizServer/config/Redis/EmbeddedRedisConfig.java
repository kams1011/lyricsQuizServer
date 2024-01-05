package kr.toy.lyricsQuizServer.config.Redis;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 로컬 환경일경우 내장 레디스가 실행됩니다.
 */
@Profile("local")
@RequiredArgsConstructor
@Configuration
public class EmbeddedRedisConfig {

    private final RedisProperties redisProperties;

    private RedisServer redisServer;

    @PostConstruct
    public void redisServer() {

        try{
            redisServer = new RedisServer(redisProperties.port);
            redisServer.start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @PreDestroy
    public void stopRedis() {
        if (redisServer != null) {
            redisServer.stop();
        }
    }
}
