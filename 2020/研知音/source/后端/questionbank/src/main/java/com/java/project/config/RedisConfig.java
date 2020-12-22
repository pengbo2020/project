package com.java.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.io.Serializable;

@Configuration
public class RedisConfig {
    //redis连接工厂
    private RedisConnectionFactory redisConnectionFactory;

    /*调用实例时使用*/
    @Bean
    public RedisTemplate<String, Serializable> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String,Serializable> redisTemplate = new RedisTemplate<String,Serializable>();
        redisTemplate.setKeySerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new GenericJackson2JsonRedisSerializer());// Hash key序列化
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());// Hash value序列化
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /*注解时使用*/
     /*@Bean
    public RedisCacheConfiguration redisCacheConfiguration(){
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration.serializeValuesWith(RedisSerializationContext.SerializationPair
                .fromSerializer(new Jackson2JsonRedisSerializer<Object>(Object.class)))
                .entryTtl(Duration.ofSeconds(10));
                return redisCacheConfiguration;
    }*/
}
