package com.example.config;

import com.example.controller.filter.ExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public FilterRegistrationBean<ExceptionFilter> exceptionFilterRegistration() {
        FilterRegistrationBean<ExceptionFilter> registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ExceptionFilter());
        registrationBean.setName("exceptionFilter");
        //此处尽量小，要比其他Filter靠前
        registrationBean.setOrder(-1);
        return registrationBean;
    }

}
