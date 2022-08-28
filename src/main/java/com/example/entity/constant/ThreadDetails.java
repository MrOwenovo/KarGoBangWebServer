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

    public static String getUsername() {
        //获取用户
        String name = ThreadDetails.securityContext.get().getAuthentication().getName();
        if (name == null) throw new ThreadLocalIsNullException("ThreadDetails中没有securityContext!");
        return name;
    }



}
