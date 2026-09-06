package com.example.w2_2.controller;
/* 등록하기 버튼 & 등록 완료 */
import com.example.w2_2.dto.TodoDTO;
import com.example.w2_2.service.TodoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
@WebServlet(name = "todoRegisterController", value = "/todo/register")
@Log4j2
public class TodoRegisterController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE; // TodoService 사용할 준비
    private final DateTimeFormatter DATEFORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd"); // LocalDate 객체에게 문자열 알려주는 용도

    /* 사용자가 '등록하기' 버튼 누름(등록 전) -> doGet 실행 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        log.info("/todo/register GET ...........");

        HttpSession session = req.getSession(); // 로그인 한 사람만 Todo 등록하게 하려고 세션 확인

        // 기존에 JSESSIONID가 없는 로그인 안 한 사용자인 경우
        if (session.isNew()) {
            log.info("JSESSIONID 쿠키가 새로 만들어진 사용자");
            resp.sendRedirect("/login"); // "로그인 페이지로 보내."
            return;
        }

        // JESSIONID는 있지만 세션에 loginInfo가 없는 경우
        if (session.getAttribute("loginInfo") == null) {
            log.info("로그인한 정보가 없는 사용자");
            resp.sendRedirect("/login"); // "로그인 페이지로 보내."
            return;
        }

        // 정상적인 경우라면 등록 화면으로
        req.getRequestDispatcher("/WEB-INF/todo/register.jsp")
                .forward(req, resp);
    }

    /* 사용자가 '등록' 버튼 누름 -> doPost 실행 */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        TodoDTO todoDTO = TodoDTO.builder()
                .title(req.getParameter("title"))
                .dueDate(LocalDate.parse(
                        req.getParameter("dueDate"), DATEFORMATTER))
                .build();
        // 브라우저에 사용자가 title에 입력한 "공부하기" dueDate에 입력한 "2026-09-10"이 TodoDTO로 만들어짐

        log.info("/todo/register POST...");
        log.info(todoDTO);

        try {
            todoService.register(todoDTO); // "TodoService야, 니가 갖고있는 register() 메소드 사용해서 이거 좀 DB에 등록해줘."
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 성공하면 목록으로 이동
        resp.sendRedirect(req.getContextPath()+"/todo/list");
        // 등록 완료 -> /todo/list -> TodoListController -> 최신 Todo 목록
        // getContextPath() : 내 웹 애플리케이션의 시작 경로부터, 안전하게 작동
    }


}
