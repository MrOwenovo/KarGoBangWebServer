package com.example;

import com.example.anno.EnableFilterAutoRegister;
import com.example.controller.listener.RoomListener;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableFilterAutoRegister
@EnableScheduling
public class KarGoBangWebServerApplication {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(KarGoBangWebServerApplication.class, args);

    }

}
