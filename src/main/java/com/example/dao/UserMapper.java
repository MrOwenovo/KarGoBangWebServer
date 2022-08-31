package com.example.dao;

import com.example.config.RedisMybatisCache;
import com.example.entity.data.UserDetail;
import com.example.entity.data.UserInfoDetail;
import com.example.entity.data.UserScoreDetail;
import org.apache.ibatis.annotations.*;

import java.sql.Blob;
import java.util.Optional;
@Mapper
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface UserMapper {

    @Update("update users set username=#{newUsername} where username=#{username}")
    boolean modifyUserDetails(@Param("newUsername") String newUsername, @Param("username") String username);

    boolean modifyUserInfoDetails(@Param("username") String username,@Param("message") String message,@Param("sex") String sex);

    boolean modifyUserScoreDetails(@Param("username") String username,@Param("wins") int wins,@Param("sessions") int sessions);

    @Update("update userscore set passNumber=#{passNumber} where username=#{username}")
    boolean modifyPassNumber(@Param("passNumber") int passNumber, @Param("username") String username);

    @Update("update userdetails set icon=#{file} where username=#{username}")
    int modifyIcon(@Param("username") String username,@Param("file") String file);

}
