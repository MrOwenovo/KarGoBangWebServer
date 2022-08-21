package com.example.service.impl;

import com.example.controller.exception.AlreadyException;
import com.example.controller.exception.NotExistInRedisException;
import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.dao.AuthMapper;
import com.example.entity.data.UserDetail;
import com.example.service.AuthService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    AuthMapper authMapper;
    @Resource
    RedisTemplate<Object,Object> template;



    @Override
    public boolean register(String username, String password) {
        Optional<UserDetail> user = authMapper.findUserById(username);
        if (user.isPresent()) throw new AlreadyException("该用户已经被注册");
        return authMapper.registerUser(username, password, "user");
    }

//    @Override
//    public void setAuthentication() {
//        //获取验证信息
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        //取出roomNumber
//        String roomNumber = ROOM_NUMBER.get();
//        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadLocal中没有RoomNumber!");
//        //存入redis
//        template.opsForValue().set(USER_AUTHENTICATION_KEY+":"+roomNumber,authentication);
//        template.expire(USER_AUTHENTICATION_KEY + ":" + roomNumber, 10, TimeUnit.MINUTES);
//
//    }
//
//    @Override
//    public Authentication getAuthentication() {
//        //取出roomNumber
//        String roomNumber = ROOM_NUMBER.get();
//        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadLocal中没有RoomNumber!");
//        //取出Authentication
//        Object o = template.opsForValue().get(USER_AUTHENTICATION_KEY + ":" + roomNumber);
//        if (o == null) throw new NotExistInRedisException("Redis中不存在Authentication!");
//        return (Authentication)o;
//    }
}


