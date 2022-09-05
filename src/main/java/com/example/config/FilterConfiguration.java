package com.example.config;

import com.example.controller.filter.ExceptionFilter;
import com.example.controller.filter.GameFilter;
import com.example.controller.filter.RoomFilter;
import com.example.controller.filter.ThreadInitFilter;
import com.example.service.util.RedisTools;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.annotation.Resource;
import javax.servlet.Filter;

@Configuration
public class FilterConfiguration {

    @Resource
    RedisTools<String> redisTools;


    @Bean
    public FilterRegistrationBean<GameFilter> gameFilterRegistration() {
        GameFilter.setRedisTools(redisTools);  //设置工具类
        FilterRegistrationBean<GameFilter> registrationBean = new FilterRegistrationBean<GameFilter>();
        registrationBean.setFilter(new GameFilter());
        registrationBean.setName("gameFilter");
        registrationBean.addUrlPatterns("/api/game/*", "/api/chat/*");
        //此处尽量小，要比其他Filter靠前
        registrationBean.setOrder(20);
        return registrationBean;
    }

    @Bean
    public FilterRegistrationBean<RoomFilter> roomFilterRegistration() {
        FilterRegistrationBean<RoomFilter> registrationBean = new FilterRegistrationBean<RoomFilter>();
        registrationBean.setFilter(new RoomFilter());
        registrationBean.setName("roomFilter");
        registrationBean.addUrlPatterns("/api/room/waitForOpponent");
        //此处尽量小，要比其他Filter靠前
        registrationBean.setOrder(1);
        return registrationBean;
    }


    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

}
