package com.example.dao;

import com.example.w2_2.dao.MemberDAO;
import com.example.w2_2.domain.MemberVO;
import com.example.w2_2.dto.MemberDTO;
import com.example.w2_2.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Log4j2
public class MemberDAOTests {

    private MemberDAO memberDAO;
    private MemberService memberService;

    @BeforeEach
    public void ready() {
        memberDAO = new MemberDAO();
    }

    @Test
    public void testGetWithPW() throws Exception {

        String mid = "hkd";       // 반드시 존재하는 아이디를 이용
        String mpw = "hkd1234";   // 반드시 존재하는 비밀번호를 이용

        MemberVO vo = memberDAO.getWithPassword(mid, mpw);

        log.info("멤버VO---->" + vo);
    }

    @Test
    public void testUpdateUuid() throws Exception {

        String mid = "hkd";       // 반드시 존재하는 아이디를 이용

        String uuid = UUID.randomUUID().toString();

        memberDAO.updateUuid(mid, uuid);

    }

    @Test
    public void testSelectUUID() throws Exception {

        // testUpdateUuid() 실행 후 DB에 저장된 값을 그대로 붙여 넣는다.
        String uuid = "bf4f6c76-ea4d-45b9-a5e9-9aa42761a55c";

        MemberVO vo = memberDAO.selectUUID(uuid);

        log.info("멤버VO---->" + vo);
    }

    @Test
    public void testGetByUUID() throws Exception {

        // DB에 저장돼 있는 uuid 값을 그대로 붙여 넣는다.
        String uuid = "d2f5a5e9-1338-42c0-9dce-9733d60130da";

        MemberDTO dto = memberService.getByUUID(uuid);

        log.info("멤버DTO---->" + dto);
    }
}