package com.springboot.hanbandobe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

public class RedisConfig {

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory(host,port);

        factory.afterPropertiesSet();
        return factory;
    }

    // RedisTemplate은 DB 서버에 Set, Get, Delete 등을 사용할 수 있음
    @Bean
    @Primary
    public RedisTemplate<String, String> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        /*
           - Redis는 바이트 배열로 데이터를 저장하므로, 직렬화/역직렬화 방식을 지정해야 함
           - StringRedisSerializer 사용 시, String <-> byte[] 변환을 UTF-8 방식으로 수행
         */
        // key, value에 대한 직렬화 방법 설정
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new StringRedisSerializer());

        // hash key, hash value에 대한 직렬화 방법 설정
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new StringRedisSerializer());


        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    @Bean
    public StringRedisTemplate stringRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
