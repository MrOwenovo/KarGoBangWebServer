package com.example.config;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;

import javax.servlet.Filter;
import java.util.Arrays;

public class WebFilterFactoryBean implements FactoryBean<FilterRegistrationBean> {

    private Filter filter;

    private int order;

    private String name;

    private String[] urlPatterns;

    @Override
    public FilterRegistrationBean getObject() {

        FilterRegistrationBean registration = new FilterRegistrationBean();

        registration.setOrder(order);                              //设置顺序
        registration.setFilter(filter);                            //设置过滤器对象
        registration.setName(name);                                //过滤器名称
        registration.setUrlPatterns(Arrays.asList(urlPatterns));                                //过滤器名称
        registration.addUrlPatterns("/*");                         //路径

        return registration;
    }

    @Override
    public Class<?> getObjectType() {
        return FilterRegistrationBean.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Filter getFilter() {
        return filter;
    }

    public WebFilterFactoryBean setFilter(final Filter filter) {
        this.filter = filter;
        return this;
    }

    public int getOrder() {
        return order;
    }

    public WebFilterFactoryBean setOrder(final int order) {
        this.order = order;
        return this;
    }

    public String getName() {
        return name;
    }

    public WebFilterFactoryBean setName(final String name) {
        this.name = name;
        return this;
    }

    public String[] getUrlPatterns() {
        return urlPatterns;
    }

    public WebFilterFactoryBean setUrlPatterns(final String[] urlPatterns) {
        this.urlPatterns = urlPatterns;
        return this;
    }
}