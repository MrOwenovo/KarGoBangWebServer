package dao.impl;

import dao.RoomAction;
import dao.mapper.RoomMapper;
import entity.Room;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

import javax.annotation.Resource;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class RoomActionImpl implements RoomAction {

    @Resource
    RoomMapper roomMapper;

    @Override
    public synchronized void addRoomNumber(String roomNumber, String password) {
        roomMapper.createRoomNumber(roomNumber, "0", password);
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
        roomMapper.deleteRoomNumber(roomNumber);
    }

    @Override
    public synchronized Room getRoomNumber(String roomNumber) {
        return roomMapper.getRoomNumber(roomNumber);
    }

    @Override
    public int updateIsReady(String roomNumber) {
        return roomMapper.updateIsReady(1, roomNumber);
    }
}
