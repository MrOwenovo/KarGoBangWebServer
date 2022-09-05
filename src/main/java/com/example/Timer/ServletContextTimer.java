package com.example.Timer;

import com.example.entity.data.HallInfoDetail;
import com.example.service.impl.RoomServiceImpl;
import com.example.service.util.RedisTools;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Map;

/**
 * 检查ServletContextTimer中的属性是否过期
 */
@Slf4j
@Component
public class ServletContextTimer implements ServletContextAware {

    private static ServletContext servletContext;
    @Resource
    RedisTemplate<Object,Object> redisTemplate;

    @Scheduled(fixedRate = 20000)
    public void reFreshRoomNumber() {
        HashMap<String, HallInfoDetail> rooms = (HashMap<String, HallInfoDetail>)servletContext.getAttribute("rooms");
        if (rooms!=null) {
            rooms.forEach((roomNumber, room) -> {
                System.out.println(roomNumber);
                Object o = redisTemplate.opsForValue().get(RoomServiceImpl.ROOM_ID_TOKEN_KEY + roomNumber);
                System.out.println(o == null);
                if (o == null) {
                    rooms.remove(roomNumber);
                    servletContext.setAttribute("rooms",rooms);
                }
            });
        }


    }


    @Override
    public void setServletContext(ServletContext servletContext) {
        ServletContextTimer.servletContext=servletContext;
    }
}
