package cc.filescanner.config;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

import redis.embedded.RedisServer;


@Configuration
@EnableRedisRepositories(basePackages = { "cc.filescanner.redis" })
public class RedisConfig {

    private static final Logger LOG = LoggerFactory.getLogger(RedisConfig.class);

    RedisServer redisServer;


    public RedisConfig() {
        try {
            redisServer = new RedisServer(6379);
            redisServer.start();
            LOG.info("started embedded redis server");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Bean
    public JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }


    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory());
        return redisTemplate;
    }


    @PreDestroy
    public void stopServer() {
        if (redisServer != null) {
            redisServer.stop();
            LOG.info("stopped redis server");
        }
    }
}
