package com.example.service;


import org.springframework.security.core.Authentication;

public interface AuthService {


    /**
     * 注册用户
     * @param username 用户名
     * @param password 密码
     * @return 是否注册成功
     */
    boolean register(String username, String password);


//    /**
//     * 将springSecurity的Authentication存入redis
//     * @return 是否存入成功
//     */
//    void setAuthentication();
//
//    /**
//     * 从redis中取出Authentication
//     * @return Authentication
//     */
//    Authentication getAuthentication();


}