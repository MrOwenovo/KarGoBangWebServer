//package dao.impl;
//
//import dao.RoomAction;
//import dao.mapper.RoomMapper;
//import entity.Room;
//import org.springframework.stereotype.Service;
//import org.springframework.web.context.annotation.RequestScope;
//
//import javax.annotation.Resource;
//
//@Service
//@RequestScope
//public class RoomActionImpl implements RoomAction {
//
//    @Resource
//    RoomMapper roomMapper;
//
//    @Override
//    public void addRoomNumber(String roomNumber, String password) {
//        roomMapper.createRoomNumber(roomNumber, "0", password);
//    }
//
//    @Override
//    public void deleteRoomNumber(String roomNumber) {
//        roomMapper.deleteRoomNumber(roomNumber);
//    }
//
//    @Override
//    public Room getRoomNumber(String roomNumber) {
//        return roomMapper.getRoomNumber(roomNumber);
//    }
//}
