package com.example.controller.exception;

/**
 * 从ThreadLocal中取出为空时抛出的异常
 */
public class ThreadLocalIsNullException extends RuntimeException{

    public ThreadLocalIsNullException() {
        super("");
    }

    public ThreadLocalIsNullException(String message) {
        super(message);
    }

    public ThreadLocalIsNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public ThreadLocalIsNullException(Throwable cause) {
        super(cause);
    }
}
