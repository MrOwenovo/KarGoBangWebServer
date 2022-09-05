package com.example.controller;

import com.example.controller.exception.NotExistInRedisException;
import com.example.controller.exception.ThreadLocalIsNullException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;

/**
 * 重新抛出异常
 */
@ApiIgnore
@RequestMapping("/error")
@RestController
public class ErrorController {

    @ApiIgnore
    @RequestMapping("/throwThreadLocalIsNullException")
    public void throwThreadLocalIsNullException(HttpServletRequest request) {
        throw (ThreadLocalIsNullException)request.getAttribute("filter.error.ThreadLocalIsNullException");
    }

    @ApiIgnore
    @RequestMapping("/throwNotExistInRedisException")
    public void throwNotExistInRedisException(HttpServletRequest request) {
        throw (NotExistInRedisException)request.getAttribute("filter.error.NotExistInRedisException");
    }

    @ApiIgnore
    @RequestMapping("/throwNullPointerException")
    public void throwNullPointerException(HttpServletRequest request) {
        throw (NullPointerException)request.getAttribute("filter.error.NullPointerException");
    }


    @ApiIgnore
    @RequestMapping("/throwException")
    public void throwException(HttpServletRequest request) throws Exception {
        throw (Exception)request.getAttribute("filter.error.Exception");
    }
}
