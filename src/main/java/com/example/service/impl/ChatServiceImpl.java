package com.example.service.impl;

import com.example.controller.exception.NotExistInMysqlException;
import com.example.controller.exception.NotExistInRedisException;
import com.example.dao.AuthMapper;
import com.example.dao.UserMapper;
import com.example.entity.constant.ThreadDetails;
import com.example.entity.data.UserDetail;
import com.example.service.ChatService;
import com.example.service.util.IpTools;
import com.example.service.util.RedisTools;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ChatServiceImpl implements ChatService {

    @Resource
    AuthMapper authMapper;
    @Resource
    RedisTools<String> redisTools;
    @Resource
    RedisTemplate<Object, Object> template;

    public final static String SEND_OPPONENT_USERNAME_MESSAGE_TOKEN_KEY = "game:room:user:send:opponent-username-message:";

    @Override
    public void sendMessage(HttpServletRequest request, String message) {
        //获取对方username
        String opponentUsername = ThreadDetails.redisOpponentUsername.get();

        //将信息以对方username为key存入redis
        redisTools.setToRedis(SEND_OPPONENT_USERNAME_MESSAGE_TOKEN_KEY + opponentUsername,opponentUsername);
    }

    @Override
    public String getMessage() {
        String username = ThreadDetails.redisUsername.get();
        //获取redis中储存的对方的信息

        String message = redisTools.getFromRedis(SEND_OPPONENT_USERNAME_MESSAGE_TOKEN_KEY + username, "对方没有发送信息!");
        template.delete(SEND_OPPONENT_USERNAME_MESSAGE_TOKEN_KEY + username);
        return message;


    }

    @Override
    public List<UserDetail> getMyAndOpponentUserDetailsInGame() {
        List<UserDetail> result = new ArrayList<UserDetail>();
        String username = ThreadDetails.redisUsername.get();
        String opponentUsername = ThreadDetails.redisOpponentUsername.get();
        //获取自己的用户信息
        UserDetail me = authMapper.findUserByUsername(username).orElseThrow(() -> new NotExistInMysqlException("数据库中没有该用户"));
        UserDetail opponent = authMapper.findUserByUsername(opponentUsername).orElseThrow(() -> new NotExistInMysqlException("数据库中没有该用户"));
        result.add(me);
        result.add(opponent);
        return result;
    }
}
