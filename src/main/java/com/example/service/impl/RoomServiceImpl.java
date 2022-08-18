package com.example.service.impl;

import com.example.controller.exception.AlreadyException;
import com.example.service.RoomService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class RoomServiceImpl implements RoomService {

    private final static String ROOM_ID_TOKEN_KEY = "game:room:id:";

    //对方加入房间后，会在redis中存储一个key,用于生产者知晓
    private final static String ROOM_ALREADY_IN_TOKEN_KEY = "game:room:success:id:";

    private final static String NO_PASSWORD_TOKEN_VALUE = "null";

    private final static ThreadLocal<String> ROOM_NUMBER = new ThreadLocal<>();

    @Resource
    RedisTemplate<Object, Object> redisTemplate;

    public static ThreadLocal<String> getRoomNumber() {
        return ROOM_NUMBER;
    }


    @Transactional
    @Override
    public boolean createRoom(String number) {
        //判断redis中是否存有该房间
        if (redisTemplate.opsForValue().get(ROOM_ID_TOKEN_KEY+number)!=null) throw new AlreadyException("该房间已经被创建!");
        //以账号为值存入redis
        redisTemplate.opsForValue().set(ROOM_ID_TOKEN_KEY+number,NO_PASSWORD_TOKEN_VALUE);
        redisTemplate.expire(ROOM_ID_TOKEN_KEY + number, 10, TimeUnit.MINUTES);
        //将账号存入ThreadLocal
        ROOM_NUMBER.set(number);
        return true;
    }

    @Transactional
    @Override
    public boolean createRoomSecret(String number, String password) {
        //判断redis中是否存有该房间
        if (redisTemplate.opsForValue().get(ROOM_ID_TOKEN_KEY+number)!=null) throw new AlreadyException("该房间已经被创建!");
        //以账号为值存入redis
        redisTemplate.opsForValue().set(ROOM_ID_TOKEN_KEY+number,password);
        redisTemplate.expire(ROOM_ID_TOKEN_KEY + number, 10, TimeUnit.MINUTES);
        //将账号存入ThreadLocal
        ROOM_NUMBER.set(number);
        return true;
    }


    @Transactional
    @Override
    public boolean addRoomAndCallBack(String number, String password) {
        if (addRoom(number, password)) {  //创建房间成功
            //获取玩家账号
            SecurityContext context = SecurityContextHolder.getContext();
            String name = context.getAuthentication().getName();
            //将key插入redis
            redisTemplate.opsForValue().set(ROOM_ALREADY_IN_TOKEN_KEY + number, name);
            redisTemplate.expire(ROOM_ALREADY_IN_TOKEN_KEY + number, 30, TimeUnit.SECONDS);
            //删除redis中创建的key
            redisTemplate.delete(ROOM_ID_TOKEN_KEY + number);
            //将账号存入ThreadLocal
            ROOM_NUMBER.set(number);
            return true;
        }
        return false;
    }


    private boolean addRoom(String number, String password) {
        //redis中判断能否加入房间
        Object value = redisTemplate.opsForValue().get(ROOM_ID_TOKEN_KEY + number);
        if (value == null) return false;
        if (value.toString().equals(password)) return true;
        return value.toString().equals("null") && "".equals(password);
    }

    @Override
    public boolean waitForOpponent() {
        //从ThreadLocal中获取账号
        String roomNumber = ROOM_NUMBER.get();
        //生产者查询redis中是否有消费者放入的key
        Object val = redisTemplate.opsForValue().get(ROOM_ALREADY_IN_TOKEN_KEY + roomNumber);
        if (val == null) return false;
        //如果对方已经加入成功，删除key
        redisTemplate.delete(ROOM_ALREADY_IN_TOKEN_KEY + roomNumber);
        return true;

    }
}
