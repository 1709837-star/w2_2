package com.example.w2_2.controller;
/* 수정하기 버튼 & 수정 완료 */
import com.example.w2_2.dto.TodoDTO;
import com.example.w2_2.service.TodoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@WebServlet(name = "todoModifyController", value = "/todo/modify")
@Log4j2
public class TodoModifyController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;
    private final DateTimeFormatter DATEFORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /* 사용자가 '수정하기' 버튼 누름(수정 전) -> doGet 실행 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            Long tno = Long.parseLong(req.getParameter("tno"));
            // 3번 목록 누름 -> url: /todo/read?tno=3 -> ():3 -> Long.parseLong("3") = 3L로 숫자로 바꿈

            TodoDTO todoDTO = todoService.get(tno); // DB에서 3번 Todo 가져오기

            req.setAttribute("dto", todoDTO); // req에 todoDTO 담기
            req.getRequestDispatcher("/WEB-INF/todo/modify.jsp")
                    .forward(req, resp); // 값을 modify.jsp로 보냄

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("modify get... error");
        }
    }

    /* 사용자가 '등록' 버튼 누름 -> doPost 실행 */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String finishedStr = req.getParameter("finished"); // modify.jsp의 체크박스 이름

        TodoDTO todoDTO = TodoDTO.builder()
                .tno(Long.parseLong(req.getParameter("tno")))
                .title(req.getParameter("title"))
                .dueDate(LocalDate.parse(
                        req.getParameter("dueDate"), DATEFORMATTER))
                .finished(finishedStr!=null && finishedStr.equals("on"))
                .build();
        // 브라우저에 사용자가 title에 수정한 "운동하기" dueDate에 수정한 "2026-09-10"이 TodoDTO로 만들어짐

        log.info("/todo/modify POST...");
        log.info(todoDTO);

        try {
            todoService.modify(todoDTO); // DB에 최종적으로 데이터 업데이트
        } catch (Exception e) {
            e.printStackTrace();
        }

        resp.sendRedirect("/todo/list"); // 수정 끝나면 목록으로 이동
    }
}