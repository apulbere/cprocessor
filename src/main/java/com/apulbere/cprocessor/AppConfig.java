package com.apulbere.cprocessor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class AppConfig {

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(@Value("${redis.host}") String host,
                                                         @Value("${redis.port}") int port) {
        JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();
        jedisConFactory.setHostName(host);
        jedisConFactory.setPort(port);
        return jedisConFactory;
    }

    @Bean
    public RedisTemplate<String, String> customTemplate(JedisConnectionFactory jedisConnectionFactory) {
        RedisTemplate<String, String> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory);
        template.afterPropertiesSet();
        return template;
    }
}
