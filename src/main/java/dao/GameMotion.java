package dao;

import entity.Move;
import org.springframework.stereotype.Component;

@Component
public interface GameMotion {

    /**
     * 白棋走一步
     * @param x 棋盘x坐标
     * @param y 棋盘y坐标
     * @param z 棋盘z坐标
     */
    void whiteMove(String poster,String x, String y, String z);

    /**
     * 黑棋走一步
     * @param x 棋盘x坐标
     * @param y 棋盘y坐标
     * @param z 棋盘z坐标
     */
    void blackMove(String poster,String x, String y, String z);

    /**
     * 获取房间号为roomNumber的所有行动
     * @param roomNumber 房间号
     * @return 所有行动
     */
    Move[] readMoves(String roomNumber);
}
