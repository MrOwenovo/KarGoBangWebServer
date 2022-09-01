package com.example.controller.filter;

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


@Component
@Order(2)
@WebFilter(urlPatterns = {"/api/game/*", "/api/chat/*"})
@Slf4j
public class GameFilter implements Filter {

    @Resource
    RedisTools<String> redisTools;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("游戏线程拦截器启用");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        //把roomNumber从redis中取出
        String roomNumber = redisTools.getFromRedis(RoomServiceImpl.IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), "roomNumber");
        ThreadDetails.redisRoomNumber.set(roomNumber);

        //把自己username从redis中取出
        String username = redisTools.getFromRedis(RoomServiceImpl.IP_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), "username");
        ThreadDetails.redisUsername.set(username);

        //把对方username从redis中取出
        String opponentUsername = redisTools.getFromRedis(RoomServiceImpl.IP_OPPONENT_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), "opponentUsername");
        ThreadDetails.redisOpponentUsername.set(opponentUsername);

    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
