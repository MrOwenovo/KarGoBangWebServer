package com.example.service.impl;

import com.example.controller.exception.ModifyException;
import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.dao.UserMapper;
import com.example.entity.constant.ThreadDetails;
import com.example.service.UserService;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;

    @Transactional
    @Override
    public boolean modifyUserDetails(String newUsername, String message, String sex) {
        if ("".equals(newUsername)||"".equals(message)||"".equals(sex)) throw new ModifyException("内容不能为空!");
        String name = ThreadDetails.getUsername();
        boolean a = true;
        if (message!=null||sex!=null)
            a = userMapper.modifyUserInfoDetails(name, message,sex);
        if (!Objects.equals(newUsername, name)&&newUsername!=null) {
            boolean b = userMapper.modifyUserDetails(newUsername, name);
            return a || b;
        }
        return a;



    }
}
