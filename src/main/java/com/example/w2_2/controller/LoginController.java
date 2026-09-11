package com.example.w2_2.controller;
/* 사용자가 /login 요청 -> LoginController -> MemberService -> ... */
import com.example.w2_2.dao.MemberDAO;
import com.example.w2_2.dto.MemberDTO;
import com.example.w2_2.service.MemberService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.util.UUID;


@WebServlet("/login") // ★ 사용자가 (/login) 요청 -> 이 class(LoginController) 실행하라는 뜻
@Log4j2
public class LoginController extends HttpServlet {

    /* 처음 로그인 페이지에 들어갈 때 */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
            // req : 사용자 -> 서버로 오는 것 <-> resp : 서버 -> 사용자에게 보내는 것

        log.info("login get...........");

        req.getRequestDispatcher("/WEB-INF/login.jsp").forward(req, resp);
        // ★ "() 안 url을 화면으로 보여줘!" ★
        // 브라우저 -> get /login -> LoginController.doGet() -> login.jsp -> 사용자에게 로그인 화면 출력
    }

    /* 사용자가 id+pw 입력 후 로그인 버튼 누르면 */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        log.info("login post........");

        // req.getParameter("a") : "이번 요청에서 이름이 "a"인 데이터를 꺼내줘."
        String mid = req.getParameter("mid"); // 로그인 jsp에서 받은 "mid" = "hong"
        String mpw = req.getParameter("mpw"); // 로그인 jsp에서 받은 "mpw" = "1234"

        String auto = req.getParameter("auto"); // 로그인 화면에 자동 로그인 체크박스
        boolean rememberMe = (auto!=null && auto.equals("on")); // 체크하면 auto=on

        log.info("-----------------------------");
        log.info(rememberMe);


        /* Service와 연결되는 핵심 */
        try {
            MemberDTO memberDTO = MemberService.INSTANCE.login(mid, mpw);
            // ★ MemberService의 login()을 거쳐 MemberDAO의 getWithPassword를 실행
            // -> DB 결과 MemberVO로 돌려줌 -> 다시 Service에서 MemberDTO로 Controller로 돌아옴 ★

            HttpSession session = req.getSession();
            session.setAttribute("loginInfo", memberDTO); // ★ Session이라는 공간의 "loginInfo"에 회원 정보 저장 ★

            if (rememberMe) { // rememberMe가 true일 때만

                String uuid = UUID.randomUUID().toString(); // 랜덤 문자열이 생김 (Java 제공 클래스)

                MemberService.INSTANCE.updateUuid(mid, uuid);
                // -> MemberDAO의 updateUuid() 실행 -> DB에 uuid 저장
                memberDTO.setUuid(uuid); // 현재 DTO에도 저장 (DB와 동일하게)

                Cookie rememberCookie = new Cookie("remember-me", uuid); // remember-me에 랜덤문자열의 쿠키 저장
                rememberCookie.setMaxAge(60 * 60 * 24 * 7); // 7일동안 유지
                rememberCookie.setPath("/"); // 사이트 전체에서 사용할 수 있게

                resp.addCookie(rememberCookie); // 브라우저에 쿠키 보내기
            }

            resp.sendRedirect("/todo/list");
            // ★ 다른 url로 다시 요청하라고 브라우저에게 시키는 것 ★
            // LoginController -> sendRedirect("/todo/list") -> 브라우저 -> get /todo/list -> TodoListController

        } catch (Exception e) {
            resp.sendRedirect("/login?result=error");
        }
    }
}
