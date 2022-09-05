package com.example.service.impl;

import com.alibaba.fastjson.JSON;
import com.example.controller.exception.AlreadyException;
import com.example.dao.AuthMapper;
import com.example.dao.UserMapper;
import com.example.entity.constant.ThreadDetails;
import com.example.entity.data.UserDetail;
import com.example.service.AuthService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    AuthMapper authMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    RedisTemplate<Object,Object> template;



    @Override
    public boolean register(String username, String password) {
        Optional<UserDetail> user = authMapper.findUserByUsername(username);
        if (user.isPresent()) throw new AlreadyException("该用户已经被注册");
        return authMapper.registerUser(username, password, "user");
    }



}


