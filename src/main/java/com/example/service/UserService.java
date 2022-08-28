package com.example.service;

/**
 * 修改用户信息接口
 */
public interface UserService {

    /**
     * 修改用户信息
     * @param username 新用户名
     * @param message 新简介
     * @param sex 性别
     * @return 是否成功
     */
    boolean modifyUserDetails(String username,String message,String sex);
}
