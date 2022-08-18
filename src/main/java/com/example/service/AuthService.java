package com.example.service;


public interface AuthService {


    /**
     * 注册用户
     * @param username 用户名
     * @param password 密码
     * @return 是否注册成功
     */
    boolean register(String username, String password);


}