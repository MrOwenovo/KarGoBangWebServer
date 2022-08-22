package com.example.service.impl;

import com.example.controller.exception.NotExistInRedisException;
import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.entity.constant.ThreadDetails;
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


    @Resource
    RedisTemplate<Object, Object> template;


    @Transactional
    @Override
    public boolean move(int x, int y, int z,int type) {
        //从redis获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");
        Object indexObject = null;
        //获得棋子步数
        indexObject = template.opsForValue().get((type==WHITE?WHITE_INDEX_TOKEN_KEY:BLACK_INDEX_TOKEN_KEY) + roomNumber);
        if(indexObject==null) throw new NotExistInRedisException("redis数据库中没有"+(type==WHITE?"白":"黑")+"棋步数数据");
        int index = (int)indexObject;

        //将棋子行动存储到Redis中
        HashMap<String, Integer> move = new HashMap<>();
        move.put("x", x);
        move.put("y", y);
        move.put("z", z);

        template.opsForHash().putAll((type==WHITE?WHITE_MOVE_TOKEN_KEY:BLACK_MOVE_TOKEN_KEY)+roomNumber+":"+index,move);
        template.expire((type==WHITE?WHITE_MOVE_TOKEN_KEY:BLACK_MOVE_TOKEN_KEY) + roomNumber + ":" + index, 3, TimeUnit.MINUTES);
        //让棋子步数+1
        template.opsForValue().set((type==WHITE?WHITE_INDEX_TOKEN_KEY:BLACK_INDEX_TOKEN_KEY) + roomNumber,index+1);
        template.expire((type==WHITE?WHITE_INDEX_TOKEN_KEY:BLACK_INDEX_TOKEN_KEY) + roomNumber,3,TimeUnit.MINUTES);
        return true;
    }


    @Override
    public ChessDetail waitForWhiteMove() {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");
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
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");
        //获取白棋步数
        Object whiteIndexObject = template.opsForValue().get(WHITE_INDEX_TOKEN_KEY + roomNumber);
        if(whiteIndexObject==null) throw new NotExistInRedisException("redis数据库中没有白棋步数数据");
        int whiteIndex = (int)whiteIndexObject;
        //从redis中查看是否黑棋有下子的消息
        Map<Object, Object> blackMoveInfo = template.opsForHash().entries(BLACK_MOVE_TOKEN_KEY + roomNumber + ":" + (whiteIndex-1));
        if (blackMoveInfo.isEmpty()) throw new NotExistInRedisException("黑棋尚未下子");
        //删除旧key
        template.delete(BLACK_MOVE_TOKEN_KEY + roomNumber + ":" + whiteIndex);
        return new ChessDetail((Integer) blackMoveInfo.get("x"),(Integer)blackMoveInfo.get("y"),(Integer)blackMoveInfo.get("z"));
    }

    @Override
    public boolean whiteWaitForOpponent() {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");
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
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");
        //从redis中查看是否白棋已经开始准备下子
        Object flag = template.opsForValue().get(OPPONENT_WHITE_IN_TOKEN_KEY + roomNumber);
        if (flag== null) throw new NotExistInRedisException("白棋尚未准备开始游戏");
        //删除旧key
        template.delete(OPPONENT_WHITE_IN_TOKEN_KEY + roomNumber);
        return true;
    }

    @Override
    public boolean isIn(int type) {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");
        //将自己已经进入房间的信息存储到Redis中
        template.opsForValue().set((type==WHITE?OPPONENT_WHITE_IN_TOKEN_KEY:OPPONENT_BLACK_IN_TOKEN_KEY)+roomNumber,"true");
        template.expire((type==WHITE?OPPONENT_WHITE_IN_TOKEN_KEY:OPPONENT_BLACK_IN_TOKEN_KEY) + roomNumber, 3, TimeUnit.MINUTES);
        //初始化棋子步数，防止拿取时空指针异常
        template.opsForValue().set((type==WHITE?WHITE_INDEX_TOKEN_KEY:BLACK_INDEX_TOKEN_KEY)+roomNumber,0);
        template.expire((type==WHITE?WHITE_INDEX_TOKEN_KEY:BLACK_INDEX_TOKEN_KEY) + roomNumber, 3, TimeUnit.MINUTES);
        return true;
    }
}








