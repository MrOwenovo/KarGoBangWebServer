package com.example.service;

import com.example.entity.data.UserDetail;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 发送信息，用户聊天
 */
public interface ChatService {


    /**
     * 用户发送信息
     * @param message 信息
     */
    void sendMessage(HttpServletRequest request, String message);

    /**
     * 获取对方发送的信息
     * @return 对方发送的信息
     */
    String getMessage();

    /**
     * 获取自己和对方的用户信息
     * @return 自己和对方的用户信息
     */
    List<UserDetail> getMyAndOpponentUserDetailsInGame();
}
