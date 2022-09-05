package com.example.service.impl;

import com.example.controller.exception.AlreadyException;
import com.example.controller.listener.event.AddRoomEvent;
import com.example.controller.listener.event.CreateRoomEvent;
import com.example.entity.constant.ThreadDetails;
import com.example.service.RoomService;
import com.example.service.util.IpTools;
import com.example.service.util.RedisTools;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class RoomServiceImpl implements RoomService , ApplicationEventPublisherAware {

    private static ApplicationEventPublisher applicationEventPublisher;

    public final static String ROOM_ID_TOKEN_KEY = "game:room:id:";

    public final static String ROOM_ID_USERNAME_TOKEN_KEY = "game:room:id-username:";

    public final static String IP_USERNAME_TOKEN_KEY = "game:room:user:ip-username:";

    public final static String IP_OPPONENT_USERNAME_TOKEN_KEY = "game:room:user:ip-opponent-username:";

    public final static String ME_OPPONENT_USERNAME_TOKEN_KEY = "game:room:user:me-opponent-username:";

    public final static String IP_ROOM_TOKEN_KEY = "game:room:user:ip-roomNumber:";

    public final static String IP_SESSION_TOKEN_KEY = "game:room:user:ip-session:";

    //对方加入房间后，会在redis中存储一个key,用于生产者知晓
    private final static String ROOM_ALREADY_IN_TOKEN_KEY = "game:room:success:id:";

    private final static String NO_PASSWORD_TOKEN_VALUE = "null";


    @Resource
    RedisTemplate<Object, Object> redisTemplate;
    @Resource
    RedisTools<String> redisTools;



    @Transactional
    @Override
    public boolean createRoom(HttpServletRequest request, String number) {
        return createRoomSecret(request, number, null);
    }

    @Transactional
    @Override
    public boolean createRoomSecret(HttpServletRequest request, String number, String password) {
        //获取username
        String username = ThreadDetails.getUsername();
        //判断redis中是否存有该房间
        if (redisTemplate.opsForValue().get(ROOM_ID_TOKEN_KEY + number) != null)
            throw new AlreadyException("该房间已经被创建!");

        CompletableFuture<Void> set = CompletableFuture.supplyAsync(() -> {
            //以账号为key,密码为value存入redis
            if (password == null) {
                redisTools.setToRedis(ROOM_ID_TOKEN_KEY + number, NO_PASSWORD_TOKEN_VALUE);
            } else {
                redisTools.setToRedis(ROOM_ID_TOKEN_KEY + number, password);
            }
            return null;
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            //以账号为key,username为value存入redis
            redisTools.setToRedis(ROOM_ID_USERNAME_TOKEN_KEY + number, username);
            return null;
        }), (a,b)->{}).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            //将roomNumber以ip为key存入redis
            redisTools.setToRedis(IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), number);
            return null;
        }), (a, b) -> {}).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            //将username以ip为key存入redis
            redisTools.setToRedis(IP_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), username);
            return null;
        }), (a, b) -> {}).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            //获取session
            HttpSession session = request.getSession();
            //将roomNumber存入session
            session.setAttribute("roomNumber", number);
            //将ip与session对应放到redis中
            redisTools.setToRedis(IP_SESSION_TOKEN_KEY + IpTools.getIpAddress(request), session.getId());
            return null;
        }), (a, b) -> {}).thenApply(a->{
            applicationEventPublisher.publishEvent(new CreateRoomEvent(number));
            return null;
        });
        set.join();

        return true;
    }


    @Transactional
    @Override
    public boolean addRoom(HttpServletRequest request, String number, String password) {
        if (addRoomVerify(number, password)) {  //加入房间成功
            try {
                //获取玩家账号
                String username = ThreadDetails.getUsername();

                CompletableFuture<Object> set = CompletableFuture.supplyAsync(() -> {
                    //将key插入redis
                    redisTools.setToRedis(ROOM_ALREADY_IN_TOKEN_KEY + number, username, 30, RedisTools.SECONDS);
                    //删除redis中创建的key
                    redisTemplate.delete(ROOM_ID_TOKEN_KEY + number);
                    return null;
                }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
                    //将roomNumber以ip为key存入redis
                    redisTools.setToRedis(IP_ROOM_TOKEN_KEY + IpTools.getIpAddress(request), number);
                    return null;
                }), (a, b) -> {
                }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
                    //将username以ip为key存入redis
                    redisTools.setToRedis(IP_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), username);

                    return null;
                }), (a, b) -> {
                }).thenCombine(CompletableFuture.supplyAsync(() -> {
                    //获取对手username
                    String opponentUsername = redisTools.getFromRedis(ROOM_ID_USERNAME_TOKEN_KEY + number, "对方的username没有读取到!");
//                //删除redis中创建的key
//                redisTemplate.delete(ROOM_ID_USERNAME_TOKEN_KEY + number);
                    return opponentUsername;
                }), (a, opponentUsername) -> opponentUsername).thenCompose(opponentUsername -> {
                    //将对手username以ip为key存入redis
                    redisTools.setToRedis(IP_OPPONENT_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), opponentUsername);
                    redisTools.setToRedis(ME_OPPONENT_USERNAME_TOKEN_KEY + username, opponentUsername);

                    return null;
                }).thenApply(a -> {
                    applicationEventPublisher.publishEvent(new AddRoomEvent(number));
                    return null;
                });

                //将roomNumber存入session
                request.getSession().setAttribute("roomNumber", number);
                set.join();
            } catch (CompletionException e) {
                //空指针异常
            }

            return true;
        } return false;
    }


    private boolean addRoomVerify(String number, String password) {
        //redis中判断能否加入房间
        String value = redisTools.getFromRedis(ROOM_ID_TOKEN_KEY + number, "加入房间失败，不存在该房间!");
        if (value.equals(password) && !password.equals("null")) return true;
        return value.equals("null") && ("null".equals(password)||"".equals(password));
    }

    @Override
    public void waitForOpponent(HttpServletRequest request) {
        //获取username
        String username = ThreadDetails.getUsername();
        //从session中获取账号
        String roomNumber = ThreadDetails.roomNumber.get();
        //生产者查询redis中是否有消费者放入的key
        String opponentUsername = redisTools.getFromRedis(ROOM_ALREADY_IN_TOKEN_KEY + roomNumber, "对方还未加入房间!");
        //将对方username以ip为key存入redis
        redisTools.setToRedis(IP_OPPONENT_USERNAME_TOKEN_KEY + IpTools.getIpAddress(request), opponentUsername);
        redisTools.setToRedis(ME_OPPONENT_USERNAME_TOKEN_KEY + username, opponentUsername);

        //如果对方已经加入成功，删除key
        redisTemplate.delete(ROOM_ALREADY_IN_TOKEN_KEY + roomNumber);

    }


    @Override
    public void setApplicationEventPublisher(final ApplicationEventPublisher applicationEventPublisher) {
        RoomServiceImpl.applicationEventPublisher = applicationEventPublisher;
    }
}
