package com.example.service;

import com.example.entity.data.ChessDetail;

public interface GameService {

    int WHITE = 0;

    int BLACK = 1;


    /**
     * @param x x坐标
     * @param y y坐标
     * @param z z坐标
     * @return 是否解析成功
     */
    boolean move(int x,int y,int z,int type);


    /**
     * 等待白棋落子
     *
     * @return 白棋是否落子
     */
    ChessDetail waitForWhiteMove();

    /**
     * 等待黑棋落子
     *
     * @return 黑棋是否落子
     */
    ChessDetail waitForBlackMove();


    /**
     * 等待黑棋对手加入房间
     * @return 是否进入房间
     */
    boolean whiteWaitForOpponent();

    /**
     * 等待白棋对手加入房间
     * @return 是否进入房间
     */
    boolean blackWaitForOpponent();

    /**
     * 棋子已经加入房间
     * @return 是否加入房间
     */
    boolean isIn(int type);

    /**
     * 游戏结束后操作数据库
     * @param isWin 是否胜利
     * @return 操作是否成功
     */
    boolean gameResult(boolean isWin);

    /**
     * 如果当前熄灯关卡高，修改数据库
     * @param number 熄灯关卡数
     * @return 操作是否成功
     */
    boolean changePassNumber(int number);

}
