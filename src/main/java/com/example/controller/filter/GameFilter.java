package com.example.controller.filter;

import com.example.anno.MyFilterOrder;
import com.example.entity.constant.ThreadDetails;
import com.example.service.impl.RoomServiceImpl;
import com.example.service.util.IpTools;
import com.example.service.util.RedisTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;



@Slf4j
//@MyFilterOrder(value = 100,urlPatterns ={"/api/game/*", "/api/chat/*"} )
public class GameFilter implements Filter {

    private static RedisTools<String> redisTools;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("游戏线程拦截器启用");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        if (!request.getServletPath().startsWith("/api/game/changePassNumber")) {

            //把roomNumber从redis中取出
            String roomNumber = redisTools.getFromRedis(RoomServiceImpl.IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), "roomNumber");
            ThreadDetails.redisRoomNumber.set(roomNumber);

            //把自己username从redis中取出
            String username = redisTools.getFromRedis(RoomServiceImpl.IP_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), "username");
            ThreadDetails.redisUsername.set(username);

            //把对方username从redis中取出
            String opponentUsername = redisTools.getFromRedis(RoomServiceImpl.ME_OPPONENT_USERNAME_TOKEN_KEY + username, "opponentUsername");
            ThreadDetails.redisOpponentUsername.set(opponentUsername);

            filterChain.doFilter(servletRequest, servletResponse);

        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }

    public static void setRedisTools(RedisTools<String> redisTools) {
        GameFilter.redisTools = redisTools;
    }
}
