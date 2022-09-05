package com.example.service.impl;

import com.example.controller.exception.NotExistInMysqlException;
import com.example.controller.exception.NotExistInServletContextException;
import com.example.dao.AuthMapper;
import com.example.entity.data.HallInfoDetail;
import com.example.entity.data.UserDetail;
import com.example.service.HallService;
import com.example.service.util.RedisTools;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ServletContextAware;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class HallServiceImpl implements HallService, ServletContextAware {

    private static ServletContext servletContext;
    @Resource
    RedisTemplate<Object, Object> template;
    @Resource
    RedisTools<String> redisTools;
    @Resource
    AuthMapper authMapper;

    @Override
    public HallInfoDetail[] getHallInfo() {
        //从ServletContext中取出所有room
        HashMap<String, HallInfoDetail> rooms = (HashMap<String, HallInfoDetail>)servletContext.getAttribute("rooms");
        if (rooms==null||rooms.isEmpty())
            throw new NotExistInServletContextException("当前还没有房间!");
        return rooms.values().toArray(new HallInfoDetail[0]);
    }

    @Override
    public void setServletContext(final ServletContext servletContext) {
        HallServiceImpl.servletContext = servletContext;
    }
}
