package com.example.service.impl;

import com.example.controller.exception.AlreadyException;
import com.example.dao.AuthMapper;
import com.example.entity.data.UserDetail;
import com.example.service.AuthService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;
import java.util.function.Consumer;

@Service
public class AuthServiceImpl implements AuthService {

    @Resource
    AuthMapper authMapper;

    @Override
    public boolean register(String username, String password) {
        Optional<UserDetail> user = authMapper.findUserById(username);
        if (user.isPresent()) throw new AlreadyException("该用户已经被注册");
        return authMapper.registerUser(username, password, "user");
    }
}


