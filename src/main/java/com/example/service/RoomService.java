package com.example.service;

import javax.servlet.http.HttpServletRequest;

public interface RoomService {

    /**
     * 根据房间号创建房间，不需要加密号
     * @param number 房间号
     * @return 是否成功
     */
    boolean createRoom(HttpServletRequest request,String number);

    /**
     * @param number 房间号
     * @param password 加密号
     * @return 是否成功
     */
    boolean createRoomSecret(HttpServletRequest request,String number,String password);

    /**
     * 根据房间号创建房间，不需要加密号
     * @param number 房间号
     * @param password 加密号
     * @return 是否成功
     */
    boolean addRoom(HttpServletRequest request,String number, String password);

    /**
     * 每隔3秒判断对手是否加入房间
     * @return 对手是否加入房间
     */
    void waitForOpponent(HttpServletRequest request);

}
