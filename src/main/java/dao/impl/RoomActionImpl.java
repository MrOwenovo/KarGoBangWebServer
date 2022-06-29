package dao.impl;

import dao.RoomAction;
import dao.mapper.RoomActionMapper;
import entity.Room;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class RoomActionImpl implements RoomAction {

    @Resource
    RoomActionMapper roomActionMapper;

    @Override
    public synchronized void addRoomNumber(String roomNumber, String password) {
        roomActionMapper.createRoomNumber(roomNumber, "0", password);
        new Thread(){
            @Override
            public void run() {
                new Timer().schedule(new TimerTask() {  //5分钟后删除该邀请
                    @Override
                    public void run() {
                        if (getRoomNumber(roomNumber)!=null)
                        deleteRoomNumber(roomNumber);
                    }
                },300000);
            }
        }.start();
    }


    @Override
    public synchronized void deleteRoomNumber(String roomNumber) {
        roomActionMapper.deleteRoomNumber(roomNumber);
    }

    @Override
    public synchronized Room getRoomNumber(String roomNumber) {
        return roomActionMapper.getRoomNumber(roomNumber);
    }

    @Override
    public int updateIsReady(String roomNumber) {
        return roomActionMapper.updateIsReady(1, roomNumber);
    }

    @SneakyThrows
    @Override
    public int createRoomTable(String roomNumber) {
        System.out.println("来到dao方法");
        Thread.sleep(1000);
        return roomActionMapper.createFriendsTable("room_"+roomNumber);  //创建表

    }
}
