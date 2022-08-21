package com.example.entity.constant;

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



}
