package com.example.controller.exception;

/**
 * 密码错误时抛出的异常
 */
public class PasswordWrongException extends RuntimeException{
    public PasswordWrongException() {
        super("");
    }

    public PasswordWrongException(String message) {
        super(message);
    }

    public PasswordWrongException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordWrongException(Throwable cause) {
        super(cause);
    }
}
