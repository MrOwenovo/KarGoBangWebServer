package service.Impl;

import dao.RoomAction;
import org.springframework.stereotype.Service;
import service.CreateRoomService;

import javax.annotation.Resource;

@Service
public class CreateRoomServiceImpl implements CreateRoomService {

    @Resource
    RoomAction roomAction;

    @Override
    public void createRoom(String roomNumber) {
        System.out.println("来到service方法");
        int i=roomAction.createRoomTable(roomNumber); //创建棋子表
    }
}
