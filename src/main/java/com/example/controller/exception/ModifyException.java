package com.example.controller.exception;


/**
 * 修改时出现的的异常
 */
public class ModifyException extends RuntimeException{

    public ModifyException() {
        super("");
    }

    public ModifyException(String message) {
        super(message);
    }

    public ModifyException(String message, Throwable cause) {
        super(message,cause);
    }

    public ModifyException(Throwable cause) {
        super(cause);
    }

}
