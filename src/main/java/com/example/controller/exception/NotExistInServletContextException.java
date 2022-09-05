package com.example.controller.exception;

/**
 * ServletContext不存在该内容时抛出的异常
 */
public class NotExistInServletContextException extends RuntimeException{
    public NotExistInServletContextException() {
        super("");
    }

    public NotExistInServletContextException(String message) {
        super(message);
    }

    public NotExistInServletContextException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotExistInServletContextException(Throwable cause) {
        super(cause);
    }
}
