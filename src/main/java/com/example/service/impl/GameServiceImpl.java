package com.example.service.impl;

import com.example.controller.exception.NotExistInMysqlException;
import com.example.controller.exception.NotExistInRedisException;
import com.example.controller.exception.ThreadLocalIsNullException;
import com.example.dao.AuthMapper;
import com.example.dao.UserMapper;
import com.example.entity.constant.ThreadDetails;
import com.example.entity.data.ChessDetail;
import com.example.entity.data.UserDetail;
import com.example.entity.data.UserScoreDetail;
import com.example.service.GameService;
import com.example.service.util.RedisTools;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Resource
    UserMapper userMapper;
    @Resource
    AuthMapper authMapper;
    @Resource
    RedisTools<Integer> redisTools;
    @Resource
    RedisTools<String> redisToolsString;


    @Transactional
    @Override
    public boolean move(int x, int y, int z,int type) {
        //从redis获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        Object indexObject = null;
        //获得棋子步数
        int index=redisTools.getFromRedis((type == WHITE ? WHITE_INDEX_TOKEN_KEY : BLACK_INDEX_TOKEN_KEY) + roomNumber, "redis数据库中没有" + (type == WHITE ? "白" : "黑") + "棋步数数据");

        //将棋子行动存储到Redis中
        HashMap<String, Integer> move = new HashMap<>();
        move.put("x", x);
        move.put("y", y);
        move.put("z", z);

        redisTools.setHashMapToRedis((type==WHITE?WHITE_MOVE_TOKEN_KEY:BLACK_MOVE_TOKEN_KEY)+roomNumber+":"+index,move,3,RedisTools.MINUTE);
        //让棋子步数+1
        redisTools.setToRedis((type==WHITE?WHITE_INDEX_TOKEN_KEY:BLACK_INDEX_TOKEN_KEY) + roomNumber,index+1,3,RedisTools.MINUTE);
        return true;
    }


    @Override
    public ChessDetail waitForWhiteMove() {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        //获取黑棋步数
        int blackIndex=redisTools.getFromRedis(BLACK_INDEX_TOKEN_KEY + roomNumber, "redis数据库中没有黑棋步数数据");
        //从redis中查看是否白棋有下子的消息
        Map<Object, Object> whiteMoveInfo = redisTools.getHashMapFromRedis(WHITE_MOVE_TOKEN_KEY + roomNumber + ":" + blackIndex, "白棋尚未下子");
        //删除旧key
        template.delete(WHITE_MOVE_TOKEN_KEY + roomNumber + ":" + blackIndex);
        return new ChessDetail((Integer) whiteMoveInfo.get("x"),(Integer)whiteMoveInfo.get("y"),(Integer)whiteMoveInfo.get("z"));
    }

    @Override
    public ChessDetail waitForBlackMove() {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        //获取白棋步数
        int whiteIndex = redisTools.getFromRedis(WHITE_INDEX_TOKEN_KEY + roomNumber, "redis数据库中没有白棋步数数据");
        //从redis中查看是否黑棋有下子的消息
        Map<Object, Object> blackMoveInfo =redisTools.getHashMapFromRedis(BLACK_MOVE_TOKEN_KEY + roomNumber + ":" + (whiteIndex - 1), "黑棋尚未下子");
        //删除旧key
        template.delete(BLACK_MOVE_TOKEN_KEY + roomNumber + ":" + whiteIndex);
        return new ChessDetail((Integer) blackMoveInfo.get("x"),(Integer)blackMoveInfo.get("y"),(Integer)blackMoveInfo.get("z"));
    }

    @Override
    public boolean whiteWaitForOpponent() {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        //从redis中查看是否黑棋已经开始准备下子
        redisTools.getFromRedis(OPPONENT_BLACK_IN_TOKEN_KEY + roomNumber, "黑棋尚未准备开始游戏");
        //删除旧key
        template.delete(OPPONENT_BLACK_IN_TOKEN_KEY + roomNumber);
        return true;
    }

    @Override
    public boolean blackWaitForOpponent() {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        //从redis中查看是否白棋已经开始准备下子
        redisTools.getFromRedis(OPPONENT_WHITE_IN_TOKEN_KEY + roomNumber,"白棋尚未准备开始游戏");
        //删除旧key
        template.delete(OPPONENT_WHITE_IN_TOKEN_KEY + roomNumber);
        return true;
    }

    @Transactional
    @Override
    public boolean isIn(int type) {
        //获取房间号
        String roomNumber = ThreadDetails.redisRoomNumber.get();
        //将自己已经进入房间的信息存储到Redis中
        redisToolsString.setToRedis((type == WHITE ? OPPONENT_WHITE_IN_TOKEN_KEY : OPPONENT_BLACK_IN_TOKEN_KEY) + roomNumber, "true", 3, RedisTools.MINUTE);
        //初始化棋子步数，防止拿取时空指针异常
        redisTools.setToRedis((type==WHITE?WHITE_INDEX_TOKEN_KEY:BLACK_INDEX_TOKEN_KEY)+roomNumber,0,3,RedisTools.MINUTE);
        return true;
    }

    @Override
    public boolean gameResult(boolean isWin) {
        String username = ThreadDetails.getUsername();
        UserDetail userDetail = authMapper.findUserByUsername(username).orElseThrow(() -> new NotExistInMysqlException("不存在该用户!"));
        UserScoreDetail userScore = userDetail.getUserScore();
        return isWin?
            userMapper.modifyUserScoreDetails(username, userScore.getWins() + 1, userScore.getSessions() + 1):
            userMapper.modifyUserScoreDetails(username,0, userScore.getSessions() + 1);
    }

    @Override
    public boolean changePassNumber(int number) {
        String username = ThreadDetails.getUsername();
        UserDetail userDetail = authMapper.findUserByUsername(username).orElseThrow(() -> new NotExistInMysqlException("不存在该用户!"));
        UserScoreDetail userScore = userDetail.getUserScore();
        if (userScore.getPassNumber()<number)
            return userMapper.modifyPassNumber(number, username);
        return true;
    }
}








