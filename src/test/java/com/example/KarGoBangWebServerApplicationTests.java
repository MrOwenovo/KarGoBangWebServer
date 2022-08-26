package com.example;

import com.example.config.ApplicationPro;
import com.example.dao.AuthMapper;
import com.example.service.AuthService;
import com.example.service.VerifyService;
import com.example.tool.PictureTools;
import com.example.tool.RedisTool;
import org.apache.ibatis.io.Resources;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.annotation.Commit;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.service.impl.RoomServiceImpl.ROOM_ID_TOKEN_KEY;

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

    @Test
    void contextLoads() throws Exception {
//        for (int i = 0; i < 10; i++) {
//            template.opsForValue().set(ROOM_ID_TOKEN_KEY + i, "123");
//        }
        String png = PictureTools.imageToString(ImageIO.read(Resources.getResourceAsStream("girl.png")), "png");
        System.out.println(png.length());


    }

}
