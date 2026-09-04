package com.example.w2_2.listener;

import lombok.extern.log4j.Log4j2;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
@Log4j2
public class W2AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {

        log.info("========================");
        log.info("Web Application Start...");
        log.info("========================");

        ServletContext servletContext = sce.getServletContext();

        servletContext.setAttribute("appName", "W2");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        log.info("Web Application End...");
    }
}
