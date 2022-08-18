package com.example.service;

import org.springframework.stereotype.Service;

public interface VerifyService{


    /**
     * 发送邮箱验证码到对应邮箱
     * @param mail 邮箱地址
     */
    boolean sendVerifyCode(String mail);


    /**
     * 验证邮箱验证码是否正确
     * @param mail 邮箱地址
     * @param code 验证码
     * @return 是否正确
     */
    boolean doVerify(String mail,String code) ;
}
