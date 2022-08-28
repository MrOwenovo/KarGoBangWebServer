package com.example.service.relative;

import com.example.dao.AuthMapper;
import com.example.dao.UserMapper;
import com.example.entity.data.UserDetail;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserAuthService implements UserDetailsService {

    @Resource
    UserMapper userMapper;
    @Resource
    AuthMapper authMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetail userDetail = authMapper.findUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException("用户" + username + "不存在!"));
        return User
                .withUsername(username)
                .password(new BCryptPasswordEncoder()
                        .encode(userDetail.getPassword()))
                .roles(userDetail.getRole())
                .build();
    }
}
