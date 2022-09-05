package com.example.controller.listener.event;

import org.springframework.context.ApplicationEvent;

/**
 * 加入房间事件
 */
public class AddRoomEvent extends ApplicationEvent {

    public AddRoomEvent(Object source) {
        super(source);
    }
}
