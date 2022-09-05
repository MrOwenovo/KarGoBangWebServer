package com.example.config;

import com.example.entity.data.UserDetail;
import lombok.AllArgsConstructor;
import org.apache.ibatis.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.data.redis.connection.RedisServerCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Mybatis 全局二级缓存，token储存在redis中
 */

public class RedisMybatisCache implements Cache {

    private final String id;

    private static RedisTemplate<Object, Object> template;

    private final static String MYBATIS_CACHE_KEY = "mybatis:cache:";

    //注意构造方法必须带一个String类型的参数接收id
    public RedisMybatisCache(String id) {
        this.id = id;
    }

    //初始化时通过配置类将RedisTemplate给过来
    public static void setTemplate(RedisTemplate<Object, Object> template) {
        RedisMybatisCache.template = template;
    }


    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        template.opsForValue().set(MYBATIS_CACHE_KEY+o, o1,60, TimeUnit.SECONDS);
    }

    @Override
    public Object getObject(Object o) {
        return template.opsForValue().get(MYBATIS_CACHE_KEY+o);
    }

    @Override
    public Object removeObject(Object o) {
        return template.delete(MYBATIS_CACHE_KEY+o);
    }

    @Override
    public void clear() {
        template.execute((RedisCallback<Void>) connection -> {
            connection.flushDb();
            return null;
        });
    }

    @Override
    public int getSize() {
        return template.execute(RedisServerCommands::dbSize).intValue();
    }

}
