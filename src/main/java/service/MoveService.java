package service;

import entity.Move;
import org.springframework.stereotype.Component;

@Component
public interface MoveService {

    /**
     * 白棋子走一步
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     */
    void whiteMove(String poster,String  x,String y,String z);

    /**
     * 获取该房间号下所有的行动
     * @param roomNumber 房间号
     * @return 所有的行动
     */
    Move[] getMoves(String roomNumber);
}
