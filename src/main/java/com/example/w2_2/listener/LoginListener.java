package com.example.w2_2.listener;
/* Listener : 특정 이벤트가 발생하면 "어? 무슨 일 났네! 하고 감지하는 애 */
/* 이 리스너 : 세션에 loginInfo가 추가되거나 삭제되는 것 감지 */
import lombok.extern.log4j.Log4j2;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

@WebListener
@Log4j2
public class LoginListener implements HttpSessionAttributeListener {

    /* 세션에 새로운 attribute가 추가될 때 */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {

        String name = event.getName();

        if (name.equals("loginInfo")) {
            log.info("=================================");
            log.info("login: " + event.getValue());
            log.info("=================================");
        }
    }

    /* 세션에 attribute가 삭제될 때 */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {

        String name = event.getName();

        if (name.equals("loginInfo")) {
            log.info("logout: " + event.getValue());
        }
    }
}
