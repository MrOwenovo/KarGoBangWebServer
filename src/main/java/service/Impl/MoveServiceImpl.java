package service.Impl;

import dao.GameMotion;
import entity.Move;
import org.springframework.stereotype.Service;
import service.MoveService;

import javax.annotation.Resource;

@Service
public class MoveServiceImpl implements MoveService {

    @Resource
    GameMotion gameMotion;

    @Override
    public void whiteMove(String poster,String x, String  y, String  z) {
        gameMotion.whiteMove(poster,x,y,z); //移动白棋dao层方法
    }

    @Override
    public Move[] getMoves(String roomNumber) {
        return gameMotion.readMoves(roomNumber);
    }
}
