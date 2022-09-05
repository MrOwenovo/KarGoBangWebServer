package com.example.service.util;

import com.example.entity.constant.ThreadDetails;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * 处理Cookie用户的IP地址的工具类，主要用于处理nginx等反向代理导致的获取不到客户端真实ip
 */
@Slf4j
public class IpTools {

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (ThreadDetails.securityContext.get()!=null&&ThreadDetails.securityContext.get().getAuthentication()!=null&&ThreadDetails.securityContext.get().getAuthentication().getName()!=null)
        log.info("用户[{}]的真实ip为[{}]", ThreadDetails.securityContext.get().getAuthentication().getName(), ip);
        ip=ip.replace(",","").replace("127.0.0.1","").trim();
        return ip;

    }
}
