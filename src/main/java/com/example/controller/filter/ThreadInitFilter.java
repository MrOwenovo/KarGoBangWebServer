package com.example.controller.filter;

import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.entity.constant.ThreadDetails;
import com.example.service.impl.RoomServiceImpl;
import com.example.service.util.IpTools;
import com.example.service.util.RedisTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order
@WebFilter(urlPatterns = "/api")
public class ThreadInitFilter implements Filter {

    @Resource
    RedisTools<String> redisTools;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("线程初始化拦截器启用");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        //初始化SecurityContext
        SecurityContext securityContext = SecurityContextHolder.getContext();
        ThreadDetails.securityContext.set(securityContext);

        //初始化ServletContext
        ServletContext servletContext = request.getServletContext();
        ThreadDetails.servletContext.set(servletContext);


        //如果为创建房间后的/api接口
        if ( request.getServletPath().startsWith("/api/room")) {
            //把roomNumber从Session中取出
            String roomNumber = (String) request.getSession().getAttribute("roomNumber");
            if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");

            ThreadDetails.roomNumber.set(roomNumber);
        }
        //如果为进入游戏后的/api/game,/api/chat接口
        if (request.getServletPath().startsWith("/api/game")||request.getServletPath().startsWith("/api/chat") ) {
            //把roomNumber从redis中取出
            String roomNumber = redisTools.getFromRedis(RoomServiceImpl.IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), "roomNumber");
            ThreadDetails.redisRoomNumber.set(roomNumber);
            System.out.println("game取出: ip:"+RoomServiceImpl.IP_ROOM_TOKEN_KEY+ IpTools.getIpAddress(request)+" values:"+roomNumber);

            //把自己username从redis中取出
            String username = redisTools.getFromRedis(RoomServiceImpl.IP_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), "username");
            ThreadDetails.redisUsername.set(username);

            //把对方username从redis中取出
            String opponentUsername = redisTools.getFromRedis(RoomServiceImpl.IP_OPPONENT_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), "opponentUsername");
            ThreadDetails.redisOpponentUsername.set(opponentUsername);

        }
        filterChain.doFilter(servletRequest,servletResponse);

    }

    @Override
    public void destroy() {
        ThreadDetails.servletContext.remove();
        ThreadDetails.securityContext.remove();
    }
}
