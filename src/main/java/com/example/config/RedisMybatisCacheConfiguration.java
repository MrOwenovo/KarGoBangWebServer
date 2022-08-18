package com.example.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
public class RedisMybatisCacheConfiguration {

    @Resource
    RedisTemplate<Object,Object> redisTemplate;

    @PostConstruct
    public void init() {
        RedisMybatisCache.setTemplate(redisTemplate);
    }
}
