package com.example.controller.exception;

/**
 * 数据库中未查到相关信息的异常
 */
public class NotExistInMysqlException extends RuntimeException{

    public NotExistInMysqlException() {
        super("");
    }

    public NotExistInMysqlException(String message) {
        super(message);
    }

    public NotExistInMysqlException(String message, Throwable cause) {
        super(message,cause);
    }

    public NotExistInMysqlException(Throwable cause) {
        super(cause);
    }


}
