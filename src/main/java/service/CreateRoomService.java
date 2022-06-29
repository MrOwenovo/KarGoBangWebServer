package service;

import org.springframework.stereotype.Component;

@Component
public interface CreateRoomService {

    /**
     * 创建房间
     * @param roomNumber 房间后面的房间号
     */
    public void createRoom(String roomNumber);
}
