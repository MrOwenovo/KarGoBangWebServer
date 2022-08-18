package com.example;

import com.example.config.ApplicationPro;
import com.example.dao.AuthMapper;
import com.example.service.AuthService;
import com.example.service.VerifyService;
import com.example.tool.RedisTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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

    @Test
    void contextLoads() throws Exception {
        System.out.println(ApplicationPro.crossOriginValue);
    }

}
