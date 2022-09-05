package com.example.controller.listener.event;

import org.springframework.context.ApplicationEvent;

/**
 * 创建房间事件
 */
public class CreateRoomEvent extends ApplicationEvent {

    public CreateRoomEvent(Object source) {
        super(source);
    }
}
