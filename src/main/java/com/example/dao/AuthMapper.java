package com.example.dao;


import com.example.config.RedisMybatisCache;
import com.example.entity.data.UserDetail;
import com.example.entity.data.UserInfoDetail;
import com.example.entity.data.UserScoreDetail;
import org.apache.ibatis.annotations.*;

import java.util.Optional;

@Mapper
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface AuthMapper {

    @Insert("insert into users(username, password, role) values (#{username}, #{password},#{role})")
    boolean registerUser(@Param("username") String username, @Param("password") String password, @Param("role") String role);

    @Results(id = "user-map" ,value = {
            @Result(column = "id",property = "id"),
            @Result(column = "username",property = "username"),
            @Result(column = "id",property = "userInfo",one = @One(select="findUserDetailsByUserId")),
            @Result(column = "id",property = "userScore",one = @One(select="findUserScoreByUserId"))
    })
    @Select("select * from users where username=#{username}")
    Optional<UserDetail> findUserByUsername(@Param("username") String username);

    @Select("select * from userdetails where user_id=#{user_id}")
    UserInfoDetail findUserDetailsByUserId(@Param("user_id") int user_id);

    @Select("select * from userscore where user_id=#{user_id}")
    UserScoreDetail findUserScoreByUserId(@Param("user_id") int  user_id);



}
