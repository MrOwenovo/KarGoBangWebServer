package com.example.service.impl;

import com.example.controller.exception.*;
import com.example.entity.constant.ThreadDetails;
import com.example.service.RoomService;
import com.example.service.util.IpTools;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.TimeUnit;

@Service
public class RoomServiceImpl implements RoomService {

    public final static String ROOM_ID_TOKEN_KEY = "game:room:id:";

    public final static String IP_ROOM_TOKEN_KEY = "game:room:user:ip-roomNumber:";

    public final static String IP_SESSION_TOKEN_KEY = "game:room:user:ip-session:";

    //对方加入房间后，会在redis中存储一个key,用于生产者知晓
    private final static String ROOM_ALREADY_IN_TOKEN_KEY = "game:room:success:id:";

    private final static String NO_PASSWORD_TOKEN_VALUE = "null";


    @Resource
    RedisTemplate<Object, Object> redisTemplate;


    @Transactional
    @Override
    public boolean createRoom(HttpServletRequest request,String number) {
        return createRoomSecret(request, number, null);
    }

    @Transactional
    @Override
    public boolean createRoomSecret(HttpServletRequest request,String number, String password) {
        //判断redis中是否存有该房间
        if (redisTemplate.opsForValue().get(ROOM_ID_TOKEN_KEY+number)!=null) throw new AlreadyException("该房间已经被创建!");
        //以账号为值存入redis
        if (password == null) {
            redisTemplate.opsForValue().set(ROOM_ID_TOKEN_KEY + number, NO_PASSWORD_TOKEN_VALUE);
        } else {
            redisTemplate.opsForValue().set(ROOM_ID_TOKEN_KEY + number, password);
        }
        redisTemplate.expire(ROOM_ID_TOKEN_KEY + number, 10, TimeUnit.MINUTES);
        //将roomNumber以ip为key存入redis
        redisTemplate.opsForValue().set(IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), number);
        redisTemplate.expire(IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), 10, TimeUnit.MINUTES);
        System.out.println("createRoom: keys: "+IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request)+" values: "+number);

        //获取session
        HttpSession session = request.getSession();
        //将roomNumber存入session
        session.setAttribute("roomNumber",number);
        //将ip与session对应放到redis中
        redisTemplate.opsForValue().set(IP_SESSION_TOKEN_KEY +IpTools.getIpAddress(request),session.getId());
        redisTemplate.expire(IP_SESSION_TOKEN_KEY +IpTools.getIpAddress(request), 10, TimeUnit.MINUTES);


        return true;
    }


    @Transactional
    @Override
    public boolean addRoom(HttpServletRequest request,String number, String password) {
        if (addRoomVerify(number, password)) {  //创建房间成功
            //获取玩家账号
            String name = ThreadDetails.securityContext.get().getAuthentication().getName();
            //将key插入redis
            redisTemplate.opsForValue().set(ROOM_ALREADY_IN_TOKEN_KEY + number, name);
            redisTemplate.expire(ROOM_ALREADY_IN_TOKEN_KEY + number, 30, TimeUnit.SECONDS);
            //删除redis中创建的key
            redisTemplate.delete(ROOM_ID_TOKEN_KEY + number);
            //将roomNumber以ip为key存入redis
            redisTemplate.opsForValue().set(IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), number);
            redisTemplate.expire(IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), 10, TimeUnit.MINUTES);
            System.out.println("addRoom: keys: "+IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request)+" values: "+number);


            //将roomNumber存入session
            request.getSession().setAttribute("roomNumber",number);

            return true;
        }
        return false;
    }


    private boolean addRoomVerify(String number, String password) {
        //redis中判断能否加入房间
        Object value = redisTemplate.opsForValue().get(ROOM_ID_TOKEN_KEY + number);
        if (value == null) throw new NotExistInRedisException("加入房间失败，不存在该房间!");
        if (value.toString().equals(password)&&!password.equals("null")) return true;
        return value.toString().equals("null") && "".equals(password);
    }

    @Override
    public void waitForOpponent() {
        //从session中获取账号
        String roomNumber = ThreadDetails.roomNumber.get();
        if (roomNumber == null) throw new ThreadLocalIsNullException("ThreadDetails中没有RoomNumber!");
        //生产者查询redis中是否有消费者放入的key
        Object val = redisTemplate.opsForValue().get(ROOM_ALREADY_IN_TOKEN_KEY + roomNumber);
        if (val == null)  throw new NotExistInRedisException("对方还未加入房间!");;
        //如果对方已经加入成功，删除key
        redisTemplate.delete(ROOM_ALREADY_IN_TOKEN_KEY + roomNumber);

    }
}
