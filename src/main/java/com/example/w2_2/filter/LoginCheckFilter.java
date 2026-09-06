package com.example.w2_2.filter;
/* Filter = Controller가 실행되기 전에 중간에서 요청을 검사하는 문지기 */
/* 이 필더 : Todo 페이지에 들어가려는 사람이 로그인했는지 확인 */
import com.example.w2_2.dto.MemberDTO;
import com.example.w2_2.service.MemberService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@WebFilter(urlPatterns = {"/todo/*"}) // todo의 하위경로 전부 이 filter 거쳐감
@Log4j2
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        log.info("Login check filter.................");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        HttpSession session = req.getSession();

        if (session.getAttribute("loginInfo") == null) {

            // 자동 로그인이 있을 수 있으니 쿠키를 체크
            Cookie cookie = findCookie(req.getCookies(), "remember-me");

            if (cookie != null) {

                log.info("cookie는 존재하는 상황");
                String uuid = cookie.getValue(); // 쿠키 안에 들어있던 UUID를 꺼냄

                try {
                    MemberDTO memberDTO =
                            MemberService.INSTANCE.getByUUID(uuid);

                    log.info("쿠키의 값으로 조회한 사용자 정보: " + memberDTO);

                    session.setAttribute("loginInfo", memberDTO);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                chain.doFilter(request, response); // "검사 끝났어. 다음 단계(Controller)로 보내!"
                return;
            }

            resp.sendRedirect("/login"); // 로그인도 안 했고 쿠키도 없다면 다시 로그인창으로

            return;
        }

        chain.doFilter(request, response); // 처음부터 세션 있는 애들에게 "검사 끝났어. 다음 단계(Controller)로 보내!"
    }

    private Cookie findCookie(Cookie[] cookies, String name) {

        if (cookies == null || cookies.length == 0) {
            return null;
        }

        Optional<Cookie> result = Arrays.stream(cookies)
                .filter(ck -> ck.getName().equals(name))
                .findFirst();

        return result.orElse(null);
    }
}
