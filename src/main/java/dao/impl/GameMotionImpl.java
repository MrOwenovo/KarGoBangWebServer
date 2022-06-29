package dao.impl;

import dao.GameMotion;
import dao.mapper.GameMotionMapper;
import entity.Move;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class GameMotionImpl implements GameMotion {

    @Resource
    GameMotionMapper mapper;

    @Override
    public void whiteMove(String poster,String x, String y, String z) {
        mapper.addMove(poster, x, y, "white", z);
    }

    @Override
    public void blackMove(int x, int y,int z) {

    }

    @Override
    public Move[] readMoves(String roomNumber) {
        return mapper.getMoves(roomNumber);
    }
}
