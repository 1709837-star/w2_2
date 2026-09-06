package com.example.w2_2.controller;
/* 삭제 버튼 */
import com.example.w2_2.service.TodoService;
import lombok.extern.log4j.Log4j2;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "todoRemoveController", value = "/todo/remove")
@Log4j2
public class TodoRemoveController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        Long tno = Long.parseLong(req.getParameter("tno"));
        // 3번 목록 누름 -> url: /todo/remove?tno=3 -> ():3 -> Long.parseLong("3") = 3L로 숫자로 바꿈

        log.info("tno: " + tno);

        try {
            todoService.remove(tno);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("read error");
        }

        resp.sendRedirect("/todo/list");
    }
}
