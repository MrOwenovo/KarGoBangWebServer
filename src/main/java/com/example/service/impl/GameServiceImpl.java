package com.example.service.impl;

import com.example.controller.exception.NotExistInRedisException;
import com.example.entity.data.ChessDetail;
import com.example.service.GameService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class GameServiceImpl implements GameService {

    private final static String WHITE_INDEX_TOKEN_KEY = "game:index:white:";

    private final static String BLACK_INDEX_TOKEN_KEY = "game:index:black:";

    private final static String WHITE_MOVE_TOKEN_KEY = "game:move:white:";

    private final static String BLACK_MOVE_TOKEN_KEY = "game:move:black:";

    private final static String OPPONENT_WHITE_IN_TOKEN_KEY = "game:opponent:white:";

    private final static String OPPONENT_BLACK_IN_TOKEN_KEY = "game:opponent:black:";

    private ThreadLocal<String> ROOM_NUMBER ;

    @Resource
    RedisTemplate<Object, Object> template;

    @PostConstruct
    public void init() {
        ROOM_NUMBER = RoomServiceImpl.getRoomNumber();

    }

    @Transactional
    @Override
    public boolean whiteMove(int x, int y, int z) {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //获取白棋目前步数
        Object whiteIndexObject = template.opsForValue().get(WHITE_INDEX_TOKEN_KEY + roomNumber);
        if(whiteIndexObject==null) throw new NotExistInRedisException("redis数据库中没有白棋步数数据");
        int whiteIndex = (int)whiteIndexObject;
        //将棋子行动存储到Redis中
        HashMap<String, Integer> whiteMove = new HashMap<>();
        whiteMove.put("x", x);
        whiteMove.put("y", y);
        whiteMove.put("z", z);
        template.opsForHash().putAll(WHITE_MOVE_TOKEN_KEY+roomNumber+":"+whiteIndex,whiteMove);
        template.expire(WHITE_MOVE_TOKEN_KEY + roomNumber + ":" + whiteIndex, 3, TimeUnit.MINUTES);
        //让白棋步数+1
        template.opsForValue().set(WHITE_INDEX_TOKEN_KEY + roomNumber,whiteIndex+1);
        template.expire(WHITE_INDEX_TOKEN_KEY + roomNumber,3,TimeUnit.MINUTES);
        return true;
    }

    @Transactional
    @Override
    public boolean blackMove(int x, int y, int z) {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //获取黑棋目前步数
        Object blackIndexObject = template.opsForValue().get(BLACK_INDEX_TOKEN_KEY + roomNumber);
        if(blackIndexObject==null) throw new NotExistInRedisException("redis数据库中没有黑棋步数数据");
        int blackIndex = (int) blackIndexObject;
        //将棋子行动存储到Redis中
        HashMap<String, Integer> blackMove = new HashMap<>();
        blackMove.put("x", x);
        blackMove.put("y", y);
        blackMove.put("z", z);
        template.opsForHash().putAll(BLACK_MOVE_TOKEN_KEY+roomNumber+":"+blackIndex,blackMove);
        template.expire(BLACK_MOVE_TOKEN_KEY+roomNumber+":"+blackIndex, 3, TimeUnit.MINUTES);
        //让黑棋步数+1
        template.opsForValue().set(BLACK_INDEX_TOKEN_KEY + roomNumber,blackIndex+1);
        template.expire(BLACK_INDEX_TOKEN_KEY + roomNumber,3,TimeUnit.MINUTES);
        return true;
    }

    @Override
    public ChessDetail waitForWhiteMove() {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //获取黑棋步数
        Object blackIndexObject = template.opsForValue().get(BLACK_INDEX_TOKEN_KEY + roomNumber);
        if(blackIndexObject==null) throw new NotExistInRedisException("redis数据库中没有黑棋步数数据");
        int blackIndex = (int) blackIndexObject;
        //从redis中查看是否白棋有下子的消息
        Map<Object, Object> whiteMoveInfo = template.opsForHash().entries(WHITE_MOVE_TOKEN_KEY + roomNumber + ":" + blackIndex);
        if (whiteMoveInfo.isEmpty()) throw new NotExistInRedisException("白棋尚未下子");
        //删除旧key
        template.delete(WHITE_MOVE_TOKEN_KEY + roomNumber + ":" + blackIndex);
        return new ChessDetail((Integer) whiteMoveInfo.get("x"),(Integer)whiteMoveInfo.get("y"),(Integer)whiteMoveInfo.get("z"));
    }

    @Override
    public ChessDetail waitForBlackMove() {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //获取白棋步数
        Object whiteIndexObject = template.opsForValue().get(WHITE_INDEX_TOKEN_KEY + roomNumber);
        if(whiteIndexObject==null) throw new NotExistInRedisException("redis数据库中没有白棋步数数据");
        int whiteIndex = (int)whiteIndexObject;
        //从redis中查看是否黑棋有下子的消息
        Map<Object, Object> blackMoveInfo = template.opsForHash().entries(BLACK_MOVE_TOKEN_KEY + roomNumber + ":" + whiteIndex);
        if (blackMoveInfo.isEmpty()) throw new NotExistInRedisException("黑棋尚未下子");
        //删除旧key
        template.delete(BLACK_MOVE_TOKEN_KEY + roomNumber + ":" + whiteIndex);
        return new ChessDetail((Integer) blackMoveInfo.get("x"),(Integer)blackMoveInfo.get("y"),(Integer)blackMoveInfo.get("z"));
    }

    @Override
    public boolean whiteWaitForOpponent() {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //从redis中查看是否黑棋已经开始准备下子
        Object flag = template.opsForValue().get(OPPONENT_BLACK_IN_TOKEN_KEY + roomNumber);
        if (flag== null) throw new NotExistInRedisException("黑棋尚未准备开始游戏");
        //删除旧key
        template.delete(OPPONENT_BLACK_IN_TOKEN_KEY + roomNumber);
        return true;
    }

    @Override
    public boolean blackWaitForOpponent() {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //从redis中查看是否白棋已经开始准备下子
        Object flag = template.opsForValue().get(OPPONENT_WHITE_IN_TOKEN_KEY + roomNumber);
        if (flag== null) throw new NotExistInRedisException("白棋尚未准备开始游戏");
        //删除旧key
        template.delete(OPPONENT_WHITE_IN_TOKEN_KEY + roomNumber);
        return true;
    }

    @Override
    public boolean whiteIsIn() {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //将自己已经进入房间的信息存储到Redis中
        template.opsForValue().set(OPPONENT_WHITE_IN_TOKEN_KEY+roomNumber,"true");
        template.expire(OPPONENT_WHITE_IN_TOKEN_KEY + roomNumber, 3, TimeUnit.MINUTES);
        //初始化白棋步数，防止拿取时空指针异常
        template.opsForValue().set(WHITE_INDEX_TOKEN_KEY+roomNumber,0);
        template.expire(WHITE_INDEX_TOKEN_KEY + roomNumber, 3, TimeUnit.MINUTES);
        return true;
    }
    @Override
    public boolean blackIsIn() {
        //获取房间号
        String roomNumber = ROOM_NUMBER.get();
        //将自己已经进入房间的信息存储到Redis中
        template.opsForValue().set(OPPONENT_BLACK_IN_TOKEN_KEY+roomNumber,"true");
        template.expire(OPPONENT_BLACK_IN_TOKEN_KEY + roomNumber, 3, TimeUnit.MINUTES);
        //初始化黑棋步数，防止拿取时空指针异常
        template.opsForValue().set(BLACK_INDEX_TOKEN_KEY+roomNumber,0);
        template.expire(BLACK_INDEX_TOKEN_KEY + roomNumber, 3, TimeUnit.MINUTES);
        return true;
    }
}








