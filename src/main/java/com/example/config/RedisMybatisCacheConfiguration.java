package com.example.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

@Configuration
@Slf4j
public class RedisMybatisCacheConfiguration {

    @Resource
    RedisTemplate<Object,Object> redisTemplate;

    @PostConstruct
    public void init() {
        log.info("存储Mybatis缓存的Redis配置加载");
        RedisMybatisCache.setTemplate(redisTemplate);
    }
}
