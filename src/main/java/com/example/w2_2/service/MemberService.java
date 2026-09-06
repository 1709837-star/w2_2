package com.example.w2_2.service;
/* Controller와 DAO 사이에서 "이 기능을 어떻게 처리할지" 연결해주는 담당자, 통역사 */
/* DTO <-> VO 변환 + DAO 호출 */
/* 출력할 때 : DB -> DAO -> VO -> Service -> DTO -> Controller -> JSP */
/* 입력할 때 : JSP -> Controller -> DTO -> Service -> VO -> DAO -> DB */
import com.example.w2_2.dao.MemberDAO;
import com.example.w2_2.domain.MemberVO;
import com.example.w2_2.dto.MemberDTO;
import com.example.w2_2.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

@Log4j2
public enum MemberService {

    INSTANCE; // 하나의 Service 객체를 만들어서 사용
              // Controller에서 MemberService.INSTANCE.login(...) 으로 사용

    private MemberDAO dao; // DB작업을 하기 위해서 DAO가 필요
    private ModelMapper modelMapper; // VO <-> DTO 변환을 위해 필요

    MemberService() {
        dao = new MemberDAO(); // "회원 DB 작업 담당할 MemberVAO 하나 준비해."
        modelMapper = MapperUtil.INSTANCE.get(); // "MapperUtil에 있는 공용 ModelMapper 가져와."
    }

    /* 로그인 : DAO야, id+pw로 회원 정보 찾아줘. */
    public MemberDTO login(String mid, String mpw) throws Exception {
        // Controller로부터 mid, mpw 받으면 mid="hong", mpw="1234" 저장

        MemberVO vo = dao.getWithPassword(mid, mpw); // "MemberDAO, 니 메소드로 이 아이디랑 비밀번호로 회원 좀 찾아봐." -> 결과 vo에 저장

        MemberDTO dto = modelMapper.map(vo, MemberDTO.class); // "MemberVO를 MemberDTO로 변환해줘."
        // DAO는 DB와 가까운 계층이라 VO / Controller는 화면과 데이터 주고받는 계층이라 DTO 사용

        return dto;
    }

    /* 업데이트 : DAO야, 이 회원의 UUID 수정해. */
    public void updateUuid(String mid, String uuid) throws Exception {

        dao.updateUuid(mid, uuid); // "MemberDAO, 니 메소드로 이 회원 UUID 수정해."
    }

    /* 회원 찾기 : DAO야, UUID로 회원 찾아줘. */
    public MemberDTO getByUUID(String uuid) throws Exception {

        MemberVO vo = dao.selectUUID(uuid); // "MemberDAO, 니 메소드로 이 회원 UUID 수정해." -> 결과 vo에 저장

        MemberDTO dto = modelMapper.map(vo, MemberDTO.class);

        return dto;
    }
}