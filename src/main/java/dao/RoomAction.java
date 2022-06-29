package dao;

import entity.Room;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.context.annotation.RequestScope;

@Component
public interface RoomAction {
    /**
     * 从数据库中加入新的房间信息
     */
    void addRoomNumber(String roomNumber, String password);


    /**
     * 从数据库中删除房间信息
     * @param roomNumber 房间号
     */
    void deleteRoomNumber(String roomNumber);

    /**
     * 获取数据库中的房间信息
     * @param roomNumber 房间号
     */
    Room getRoomNumber(String roomNumber);

    /**
     * 修改房间是否加入成功
     */
    int updateIsReady(String roomNumber);

    /**
     * 创建记录下棋步数的数据库表，以room+房间号的形式
     * @param roomNumber
     */
    int createRoomTable(String roomNumber);
}

