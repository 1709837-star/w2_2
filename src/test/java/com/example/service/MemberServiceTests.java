package com.example.service;

import com.example.w2_2.dto.MemberDTO;
import com.example.w2_2.service.MemberService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

@Log4j2
public class MemberServiceTests {

    private MemberService memberService;

    @BeforeEach
    public void ready() {
        memberService = MemberService.INSTANCE;
    }

    @Test
    public void testLogin() throws Exception {

        String mid = "hkd";       // 반드시 존재하는 아이디를 이용
        String mpw = "hkd1234";   // 반드시 존재하는 비밀번호를 이용

        MemberDTO memberDTO = memberService.login(mid, mpw);

        log.info("멤버 서비스 테스트--->" + memberDTO);
    }

    @Test
    public void testUpdateUuid() throws Exception {

        String mid = "hkd";       // 반드시 존재하는 아이디를 이용

        String uuid = UUID.randomUUID().toString();

        memberService.updateUuid(mid, uuid);
    }

    @Test
    public void testGetByUUID() throws Exception {

        // DB에 저장돼 있는 uuid 값을 그대로 붙여 넣는다.
        String uuid = "bf4f6c76-ea4d-45b9-a5e9-9aa42761a55c";

        MemberDTO dto = memberService.getByUUID(uuid);

        log.info("멤버DTO---->" + dto);
    }
}