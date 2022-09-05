package com.example.controller.filter;

import com.example.anno.MyFilterOrder;
import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.entity.constant.ThreadDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class RoomFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("房间线程拦截器启用");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //把roomNumber从Session中取出
        String roomNumber = (String) request.getSession().getAttribute("roomNumber");
        if (roomNumber == null) throw new ThreadLocalIsNullException("Session中没有RoomNumber!");

        ThreadDetails.roomNumber.set(roomNumber);

        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
