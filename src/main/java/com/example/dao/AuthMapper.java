package com.example.dao;


import com.example.config.RedisMybatisCache;
import com.example.entity.data.UserDetail;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.Optional;

@Mapper
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface AuthMapper {

    @Select("select * from users where username=#{username}")
    Optional<UserDetail> findUserById(@Param("username") String username);

    @Insert("insert into study.users(username, password, role) values (#{username}, #{password},#{role})")
    boolean registerUser(@Param("username") String username, @Param("password") String password, @Param("role") String role);


}
