package com.example.controller.filter;

import com.example.controller.exception.*;
import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.PostConstruct;

/**
 * 全局异常处理
 */
@Slf4j
@RestControllerAdvice
@Order(Ordered.LOWEST_PRECEDENCE)
public class OverallExceptionHandler {

    @PostConstruct
    private void init(){
        log.info("全局异常拦截器启用");
    }


    @ExceptionHandler
    public RestBean<Object> handleException(Exception e) {
        e.printStackTrace();
        return RestBeanBuilder.builder().code(ResultCode.FAILURE).messageType(RestBeanBuilder.USER_DEFINED).message("发生了未知的错误!请联系管理员!").build().ToRestBean();
    }

    @ExceptionHandler(AlreadyException.class)
    public RestBean<Object> handleAlreadyException(AlreadyException e) {
        if ("".equals(e.getMessage()))
        return RestBeanBuilder.builder().code(ResultCode.ALREADY).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.ALREADY).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }

    @ExceptionHandler(NotExistInMysqlException.class)
    public RestBean<Object> handleNotExistInMysqlException(NotExistInMysqlException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }

    @ExceptionHandler(NotExistInRedisException.class)
    public RestBean<Object> handleNotExistInRedisException(NotExistInRedisException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }
    @ExceptionHandler(ModifyException.class)
    public RestBean<Object> handleModifyException(ModifyException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.MODIFY_FAILURE).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.MODIFY_FAILURE).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }

    @ExceptionHandler(ThreadLocalIsNullException.class)
    public RestBean<Object> handleThreadLocalIsNullException(ThreadLocalIsNullException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.THREAD_LOCAL_IS_NULL).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.THREAD_LOCAL_IS_NULL).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }

    @ExceptionHandler(PasswordWrongException.class)
    public RestBean<Object> handlePasswordWrongException(PasswordWrongException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.PASSWORD_IS_WRONG).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.PASSWORD_IS_WRONG).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }

    @ExceptionHandler(NotExistInCookieException.class)
    public RestBean<Object> handleNotExistInCookieException(NotExistInCookieException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST_IN_COOKIE).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST_IN_COOKIE).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }
    @ExceptionHandler(MyFileException.class)
    public RestBean<Object> handleNotExistInCookieException(MyFileException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.UPLOAD_FAILURE).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.UPLOAD_FAILURE).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }


    @ExceptionHandler(NotExistInServletContextException.class)
    public RestBean<Object> handleNotExistInServletContextException(NotExistInServletContextException e) {
        if ("".equals(e.getMessage()))
            return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).build().ToRestBean();
        return RestBeanBuilder.builder().code(ResultCode.NOT_EXIST).messageType(RestBeanBuilder.USER_DEFINED).message(e.getMessage()).build().ToRestBean();

    }


}
