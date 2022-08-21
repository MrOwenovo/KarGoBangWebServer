package com.example.controller.exception;

import com.example.entity.repo.RestBean;
import com.example.entity.repo.RestBeanBuilder;
import com.example.entity.repo.ResultCode;


/**
 * 数据库中已经存在该信息的异常
 */
public class AlreadyException extends RuntimeException{

    public AlreadyException() {
        super("");
    }

    public AlreadyException(String message) {
        super(message);
    }

    public AlreadyException(String message,Throwable cause) {
        super(message,cause);
    }

    public AlreadyException(Throwable cause) {
        super(cause);
    }

}
