package com.example;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan("com.example.controller.filter")
public class KarGoBangWebServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(KarGoBangWebServerApplication.class, args);
    }

}
