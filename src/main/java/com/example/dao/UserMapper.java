package com.example.dao;

import com.example.config.RedisMybatisCache;
import com.example.entity.data.UserDetail;
import com.example.entity.data.UserInfoDetail;
import com.example.entity.data.UserScoreDetail;
import org.apache.ibatis.annotations.*;

import java.util.Optional;
@Mapper
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface UserMapper {

    @Update("update users set username=#{newUsername} where username=#{username}")
    boolean modifyUserDetails(@Param("newUsername") String newUsername, @Param("username") String username);

    boolean modifyUserInfoDetails(@Param("username") String username,@Param("message") String message,@Param("sex") String sex);

}
