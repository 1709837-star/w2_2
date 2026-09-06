package com.example.w2_2.controller;
/* 하나의 Todo 상세보기 */
import com.example.w2_2.dto.TodoDTO;
import com.example.w2_2.service.TodoService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@WebServlet(name="todoReadController", value="/todo/read")
@Log4j2
public class TodoReadController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE; // TodoService 사용할 준비

    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {

        try {
            Long tno = Long.parseLong(req.getParameter("tno"));
            // 2번 목록 누름 -> url: /todo/read?tno=2 -> ():2 -> Long.parseLong("2") = 2L로 숫자로 바꿈

            TodoDTO todoDTO = todoService.get(tno); // 최종적으로 DB에서 온 tno=2L인 데이터가 Controller로 전달, todoDTO에 저장

            // req에 todoDTO 담기
            req.setAttribute("dto", todoDTO);

            // 쿠키 찾기
            Cookie viewTodoCookie =
                    findCookie(req.getCookies(), "viewTodos");

            String todoListStr = viewTodoCookie.getValue();

            boolean exist = false;

            if (todoListStr != null
                    && todoListStr.indexOf(tno + "-") >= 0) {
                exist = true;
            }

            log.info("exist: " + exist);

            if (!exist) {
                todoListStr += tno + "-";
                viewTodoCookie.setValue(todoListStr);
                viewTodoCookie.setMaxAge(60 * 60 * 24); // 유효시간 (60초 60분 24시간 -> 하루)
                viewTodoCookie.setPath("/");
                resp.addCookie(viewTodoCookie);
            }

            req.getRequestDispatcher("/WEB-INF/todo/read.jsp")
                    .forward(req, resp); // 하나의 Todo 페이지로 이동

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("read error");
        }
    }

    private Cookie findCookie(Cookie[] cookies, String cookieName) {

        Cookie targetCookie = null;

        if (cookies != null && cookies.length > 0) {
            for (Cookie ck : cookies) {
                if (ck.getName().equals(cookieName)) {
                    targetCookie = ck;
                    break;
                }
            }
        }

        if (targetCookie == null) {
            targetCookie = new Cookie(cookieName, "");
            targetCookie.setPath("/");
            targetCookie.setMaxAge(60 * 60 * 24);
        }

        return targetCookie;
    }
}


