package com.example.controller.exception;

/**
 * 请求头中Cookie不存在该值时抛出的异常
 */
public class NotExistInCookieException extends RuntimeException{
    public NotExistInCookieException() {
        super("");
    }

    public NotExistInCookieException(String message) {
        super(message);
    }

    public NotExistInCookieException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistInCookieException(Throwable cause) {
        super(cause);
    }
}
