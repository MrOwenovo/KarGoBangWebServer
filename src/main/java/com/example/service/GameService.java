package com.example.service;

import com.example.entity.data.ChessDetail;

public interface GameService {

    public final static int WHITE = 0;

    public final static int BLACK = 1;

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

}
