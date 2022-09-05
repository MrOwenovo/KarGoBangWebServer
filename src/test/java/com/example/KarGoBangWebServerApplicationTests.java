package com.example;

import com.example.config.ApplicationPro;
import com.example.controller.exception.NotExistInMysqlException;
import com.example.dao.AuthMapper;
import com.example.dao.UserMapper;
import com.example.entity.data.UserDetail;
import com.example.service.ChatService;
import com.example.service.UserService;
import com.example.service.VerifyService;
import com.example.service.util.RedisTools;
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
    ApplicationPro pro;
    @Resource
    RedisTemplate<Object, Object> template;
    @Resource
    UserMapper userMapper;
    @Resource
    RedisTools<String> redisTools;
    @Resource
    UserService chatService;

    @Test
    public void contextLoads() throws Exception {
        UserDetail userDetail = authMapper.findUserByUsername("1251031190").orElseThrow(() -> new NotExistInMysqlException("不存在该用户!"));
        System.out.println(userDetail.getUserInfo().getMessage());
        chatService.modifyUserDetails(null, "123", null);
        UserDetail userDetail2 = authMapper.findUserByUsername("1251031190").orElseThrow(() -> new NotExistInMysqlException("不存在该用户!"));
        System.out.println(userDetail2.getUserInfo().getMessage());


    }

}
