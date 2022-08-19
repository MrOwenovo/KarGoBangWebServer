package com.example.controller.filter;

import com.example.controller.exception.AlreadyException;
import com.example.controller.exception.NotExistInRedisException;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 */
@RestControllerAdvice
@Order
public class OverallExceptionHandler {

    @ExceptionHandler
    public RestBean<Object> handleException(Exception e) {
        e.printStackTrace();
        return RestBeanBuilder.builder().code(ResultCode.FAILURE).build().ToRestBean();
    }

    @ExceptionHandler(AlreadyException.class)
    public RestBean<Object> handleAlreadyException(AlreadyException e) {
        if ("".equals(e.getMessage()))
        return RestBeanBuilder.builder().code(ResultCode.ALREADY).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.ALREADY).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }

    @ExceptionHandler(NotExistInRedisException.class)
    public RestBean<Object> handleNotExistInRedisException(NotExistInRedisException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }
}
