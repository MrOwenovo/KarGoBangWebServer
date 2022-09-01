package com.example.controller.filter;

import com.example.controller.exception.NotExistInRedisException;
import com.example.controller.exception.ThreadLocalIsNullException;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class ExceptionFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (ThreadLocalIsNullException e) {
            //异常捕获，发送到error controller
            servletRequest.setAttribute("filter.error.ThreadLocalIsNullException", e);
            //将异常分发到/error/reThrow控制器
            servletRequest.getRequestDispatcher("/error/throwThreadLocalIsNullException").forward(servletRequest, servletResponse);
        } catch (NotExistInRedisException e) {
            servletRequest.setAttribute("filter.error.NotExistInRedisException", e);
            servletRequest.getRequestDispatcher("/error/throwNotExistInRedisException").forward(servletRequest, servletResponse);

        } catch (Exception e) {
            servletRequest.setAttribute("filter.error.Exception", e);
            servletRequest.getRequestDispatcher("/error/throwException").forward(servletRequest, servletResponse);

        }
    }


}
