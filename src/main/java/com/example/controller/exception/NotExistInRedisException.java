package com.example.controller.exception;

/**
 * 数据库中未查到相关信息的异常
 */
public class NotExistInRedisException extends RuntimeException{

    public NotExistInRedisException() {
        super("");
    }

    public NotExistInRedisException(String message) {
        super(message);
    }

    public NotExistInRedisException(String message,Throwable cause) {
        super(message,cause);
    }

    public NotExistInRedisException(Throwable cause) {
        super(cause);
    }


}
