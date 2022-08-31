package com.example.dao;

import com.example.config.RedisMybatisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.mybatis.spring.annotation.MapperScan;

@Mapper
@CacheNamespace(implementation = RedisMybatisCache.class)
public interface ChatMapper {


}
