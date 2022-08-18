package com.example.service.impl;

import com.example.service.VerifyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class VerifyServiceImpl implements VerifyService{

    @Resource
    JavaMailSender javaMailSender;
    @Resource
    RedisTemplate<Object , Object > template;
    @Value("${spring.mail.username}")
    String username;

    private final static String EMAIL_TOKEN_KEY = "verify:code:";


    @Override
    public boolean sendVerifyCode(String mail) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setSubject("[三维四子棋]您的注册验证码");
            Random random = new Random();
            int code = random.nextInt(899999) + 100000;
            template.opsForValue().set(EMAIL_TOKEN_KEY + mail, code);
            template.expire(EMAIL_TOKEN_KEY + mail, 3, TimeUnit.MINUTES);
            message.setText("您的验证码为: " + code + ",三分钟内有效,请及时完成注册，如果不是本人操作，请忽略。");
            message.setTo(mail);
            message.setFrom(username);
            javaMailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;

    }

    @Override
    public boolean doVerify(String mail, String code) {
        Object redisCode = template.opsForValue().get(EMAIL_TOKEN_KEY + mail);
        if (redisCode == null) return false;
        if (!code.equals(redisCode.toString())) return false;
        template.delete(EMAIL_TOKEN_KEY + mail);
        return true;
    }

}
