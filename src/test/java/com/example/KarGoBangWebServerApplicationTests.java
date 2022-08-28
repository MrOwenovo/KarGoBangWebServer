package com.example;

import com.example.config.ApplicationPro;
import com.example.dao.AuthMapper;
import com.example.dao.UserMapper;
import com.example.service.VerifyService;
import com.example.tool.RedisTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;

@SpringBootTest
class KarGoBangWebServerApplicationTests {


    @Resource
    VerifyService service;
    @Resource
    AuthMapper authMapper;
    @Resource
    RedisTool tool;
    @Resource
    ApplicationPro pro;
    @Resource
    RedisTemplate<Object, Object> template;
    @Resource
    UserMapper userMapper;

    @Test
    public void contextLoads() throws Exception {
//        for (int i = 0; i < 10; i++) {
//            template.opsForValue().set(ROOM_ID_TOKEN_KEY + i, "123");
//        }
//        System.out.println(authMapper.findUserByUsername("666666"));
    }

}
