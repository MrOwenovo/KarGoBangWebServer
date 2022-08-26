package com.example.service.impl;

import com.example.entity.data.HallInfoDetail;
import com.example.service.HallService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

@Service
public class HallServiceImpl implements HallService {

    @Resource
    RedisTemplate<Object, Object> template;

    @Override
    public HallInfoDetail[] getHallInfo() {
        //从redis中取出以game:room:id:为前缀的keys和values
        Set<Object> keys = template.keys(RoomServiceImpl.ROOM_ID_TOKEN_KEY.concat("*"));

        return null;
    }
}
