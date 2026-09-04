package com.example.w2_2.listener;

import lombok.extern.log4j.Log4j2;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@WebListener
@Log4j2
public class LoginListener implements HttpSessionAttributeListener {

    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        String name = event.getName();

        if (name.equals("loginInfo")) {
            log.info("=================================");
            log.info("login: " + event.getValue());
            log.info("=================================");
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

        String name = event.getName();

        if (name.equals("loginInfo")) {
            log.info("logout: " + event.getValue());
        }
    }
}
