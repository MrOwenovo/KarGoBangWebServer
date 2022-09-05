package com.example.controller.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@Slf4j
@WebListener
public class ContextListener implements ServletContextListener, ServletContextAttributeListener {

    @Override
    public void attributeAdded(ServletContextAttributeEvent scae) {
        log.info("ServletContext:属性: [{}],Value: [{}] 被注册", scae.getName(), scae.getValue());
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ServletContext被初始化,ServerInfo:[{}]",sce.getServletContext().getServerInfo());
    }
}
