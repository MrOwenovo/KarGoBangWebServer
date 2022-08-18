package com.example.tool;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Set;

@Component
public class RedisTool {

    @Resource
    private RedisTemplate<Object,Object> template;

    public void getRedis() {
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
        System.out.println(map);
    }

}
