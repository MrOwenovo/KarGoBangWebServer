package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfiguration {

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().contact(new Contact("MrOwenovo", "https://github.com/MrOwenovo?tab=repositories", "lmq122677@qq.com"))
                .title("三维四子棋-后端交互接口")
                .version("1.2")
                .description("四子棋联机模式的联机接口,以及大厅操作相关接口")
                .build();
    }

    @Bean
    public Docket docket() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.controller"))
                .build();
    }
}
