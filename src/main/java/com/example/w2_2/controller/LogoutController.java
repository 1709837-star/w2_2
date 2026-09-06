package com.example.w2_2.controller;

import lombok.extern.log4j.Log4j2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/logout")
@Log4j2
public class LogoutController extends HttpServlet {

    // 사용자가 post /logout 요청 -> LogoutController ->
    // -> "loginInfo" 삭제 -> Session 자체 삭제 -> /로 이동
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("log out.................");

        HttpSession session = req.getSession(); // Session 가져오기

        session.removeAttribute("loginInfo"); // 세션에서 "loginInfo" 삭제하기
        session.invalidate(); // 세션 자체를 확실하게 종료

        resp.sendRedirect("/");
    }
}
