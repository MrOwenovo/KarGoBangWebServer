package com.example.service.util;

import com.example.KarGoBangWebServerApplication;
import com.example.controller.exception.NotExistInRedisException;
import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.service.impl.RoomServiceImpl;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Component
public class RedisTools<T> {

    public final static int MINUTE = 0;

    public final static int SECONDS = 1;

    @Resource
    RedisTemplate<Object, Object> template;

    public T getFromRedis(String key,String reason) {
        Object o1 = template.opsForValue().get(key);
        if (o1 == null) throw new ThreadLocalIsNullException(reason);
        return (T) o1;

    }

    public Map<Object, Object> getHashMapFromRedis(String key,String reason) {
        Map<Object, Object> entries = template.opsForHash().entries(key);
        if (entries.isEmpty()) throw new NotExistInRedisException(reason);

        return entries;

    }

    public void setToRedis(String key,T value,int time,int type) {
        template.opsForValue().set(key,value);
        if (type ==MINUTE)
            template.expire(key, time, TimeUnit.MINUTES);
        if (type ==SECONDS)
            template.expire(key, time, TimeUnit.SECONDS);

    }

    public void setHashMapToRedis(String key,HashMap<?,?> value,int time,int type) {
        template.opsForHash().putAll(key,value);
        if (type ==MINUTE)
            template.expire(key, time, TimeUnit.MINUTES);
        if (type ==SECONDS)
            template.expire(key, time, TimeUnit.SECONDS);

    }
    public void setToRedis(String key,String value) {
        template.opsForValue().set(key,value);
        template.expire(key, 30, TimeUnit.MINUTES);
    }
    public void setHashMapToRedis(String key,HashMap<?,?> value) {
        template.opsForHash().putAll(key,value);
        template.expire(key, 30, TimeUnit.MINUTES);
    }

    public Set<Object> getKeys() {
        return template.keys("*");
    }

    public HashMap<Object,Object> getRedisValues() {
        //获得所有Key
        Set<Object> keys = template.keys("*");
        //创建集合
        HashMap<Object, Object> map = new HashMap<>();
        //循环
        for (Object key : keys) {
            Object value = template.opsForValue().get(key);
            map.put(key, value);
        }
        //返回集合
        return map;
    }
}
