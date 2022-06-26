package dao;

import org.springframework.stereotype.Component;

@Component
public interface GameMotion {

    /**
     * 白棋走一步
     * @param x 棋盘x坐标
     * @param y 棋盘y坐标
     */
    void whiteMove(int x,int y);

    /**
     * 黑棋走一步
     * @param x 棋盘x坐标
     * @param y 棋盘y坐标
     */
    void blackMove(int x,int y);
}
