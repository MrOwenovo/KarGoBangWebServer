package com.example;

import com.example.config.ApplicationPro;
import com.example.dao.AuthMapper;
import com.example.service.AuthService;
import com.example.service.VerifyService;
import com.example.tool.RedisTool;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(format.format(new Date()));

    }

}
