package com.example.entity.constant;

import com.example.controller.exception.ThreadLocalIsNullException;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 * 接收请求后的线程储存的常量
 */
public class ThreadDetails {

    public static ThreadLocal<SecurityContext> securityContext = new ThreadLocal<>();

    public static ThreadLocal<ServletContext> servletContext = new ThreadLocal<>();

    public static ThreadLocal<String> roomNumber = new ThreadLocal<>();

    public static ThreadLocal<String> redisRoomNumber = new ThreadLocal<>();

    public static ThreadLocal<String> redisUsername = new ThreadLocal<>();

    public static ThreadLocal<String> redisOpponentUsername = new ThreadLocal<>();

    public static String getUsername() {
        //获取用户
        SecurityContext securityContext = ThreadDetails.securityContext.get();
        if (securityContext==null)
            throw new ThreadLocalIsNullException("ThreadDetails中没有securityContext!");
        if (securityContext.getAuthentication()==null)
            throw new ThreadLocalIsNullException("securityContext中没有登录信息!");
        if (securityContext.getAuthentication().getName()==null)
            throw new ThreadLocalIsNullException("securityContext中没有登录信息!");
        return securityContext.getAuthentication().getName();
    }





}
