package com.example.controller.exception;


/**
 * 前后端处理文件的异常
 */
public class MyFileException extends RuntimeException{

    public MyFileException() {
        super("");
    }

    public MyFileException(String message) {
        super(message);
    }

    public MyFileException(String message, Throwable cause) {
        super(message,cause);
    }

    public MyFileException(Throwable cause) {
        super(cause);
    }

}
