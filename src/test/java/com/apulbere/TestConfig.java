package com.apulbere;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@Configuration
@Profile("useMocks")
public class TestConfig {

    @Bean
    public RedisTemplate<String, String> customTemplate(SetOperations<String, String> setOperations) {
        RedisTemplate<String, String> template = mock(RedisTemplate.class);
        when(template.opsForSet()).thenReturn(setOperations);
        return template;
    }

    @Bean
    public SetOperations<String, String> setOperations() {
        return mock(SetOperations.class);
    }
}
