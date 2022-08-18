package com.example.service;

import org.springframework.stereotype.Service;

public interface RoomService {

    /**
     * 根据房间号创建房间，不需要加密号
     * @param number 房间号
     * @return 是否成功
     */
    boolean createRoom(String number);

    /**
     * @param number 房间号
     * @param password 加密号
     * @return 是否成功
     */
    boolean createRoomSecret(String number,String password);

    /**
     * 根据房间号创建房间，不需要加密号
     * @param number 房间号
     * @param password 加密号
     * @return 是否成功
     */
    boolean addRoomAndCallBack(String number, String password);

    /**
     * 每隔3秒判断对手是否加入房间
     * @return 对手是否加入房间
     */
    boolean waitForOpponent();

}
