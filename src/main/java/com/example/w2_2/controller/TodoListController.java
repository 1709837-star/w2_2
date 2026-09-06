package com.example.w2_2.controller;
/* 목록 보기 */
import com.example.w2_2.dto.TodoDTO;
import com.example.w2_2.service.TodoService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "todoListController", value = "/todo/list") // 사용자로부터 /todo/list 요청이 오면 이 클래스를 실행해라
@Log4j2
public class TodoListController extends HttpServlet {

    private TodoService todoService = TodoService.INSTANCE; // TodoService 사용할 준비

    /* Get : 가져와 / 보여줘 -> 조회/화면 요청 */
    /* Post : 등록해줘 / 수정해줘 / 삭제해줘 -> 데이터 변경 요청*/

    /* forward : 서버 내부에서 넘김, 새로운 요청 x -> 화면을 보여줄 때 */
    /* sendRedirect : 브라우저에게 다시 요청하라고 함, 새로운 요청 o -> 등록수정삭제가 끝났을 때 */

    @SneakyThrows
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws SecurityException, IOException {

        log.info("todo list.....................");

        ServletContext servletContext = req.getServletContext();
        log.info("appName: " + servletContext.getAttribute("appName")); // 애플리케이션 전체에서 공유하는 값을 꺼내는 코드 (상세히x)

        try {
            List<TodoDTO> dtoList = todoService.listAll(); // "todoService야, 니가 갖고있는 listAll() 메소드 사용해서 Todo 목록 좀 가져와줘."
            // TodoService.listALl() -> TodoDAO.selectAll() -> DB -> (...) -> dtolist에 목록이 저장됨
            req.setAttribute("dtoList", dtoList); // req에 dtolist 넣음
            req.getRequestDispatcher("/WEB-INF/todo/list.jsp").forward(req, resp);
            // ★ "() 안 url을 화면으로 보여줘!" ★

        } catch (Exception e) {
            log.error(e.getMessage());
            throw new ServletException("list error");
        }

    }

}
