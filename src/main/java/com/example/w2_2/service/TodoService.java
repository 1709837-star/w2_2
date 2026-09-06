package com.example.w2_2.service;
/* Controller와 DAO 사이에서 "이 기능을 어떻게 처리할지" 연결해주는 담당자 */
/* DTO <-> VO 변환 + DAO 호출 */
/* 출력할 때 : DB -> DAO -> VO -> Service -> DTO -> Controller -> JSP */
/* 입력할 때 : JSP -> Controller -> DTO -> Service -> VO -> DAO -> DB */
import com.example.w2_2.dao.TodoDAO;
import com.example.w2_2.domain.TodoVO;
import com.example.w2_2.dto.TodoDTO;
import com.example.w2_2.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2 // 콘솔에서 확인할 수 있게 -> log.로 작성
public enum TodoService {

    INSTANCE;

    private TodoDAO dao;
    private ModelMapper modelMapper;

    TodoService() {
        dao = new TodoDAO();
        modelMapper = MapperUtil.INSTANCE.get();
    }

    /* Todo 등록하기 : DAO야, DB에 할 일 등록해. */
    public void register(TodoDTO todoDTO) throws Exception {

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class);
        // 여기서는 MemberService와 반대로 DTO(Controller가 만듦) -> VO(DAO가 VO로 요구하기 때문)

        log.info(todoVO); // 콘솔에서 확인

        dao.insert(todoVO); // "DAO야, 니 insert 메소드 이용해서 DB에 등록해."
    }

    /* Todo 전체 목록 가져오기 : DAO야, DB에서 Todo 다 가져와." */
    public List<com.example.w2_2.dto.TodoDTO> listAll() throws Exception {

        List<TodoVO> voList = dao.selectAll(); // "DAO야, DB에서 Todo 전부 가져와!"

        log.info("voList...................");
        log.info(voList);

        // ★ TodoVO 리스트를 TodoDTO 리스트로 바꾸기 ★
        List<TodoDTO> dtoList = voList.stream()
                .map(vo -> modelMapper.map(vo, TodoDTO.class))
                .collect(Collectors.toList());
                // 리스트 안의 vo들을 하나씩 DTO로 바꿔서 새로운 리스트로 만듦

        return dtoList;
    }

    /* Todo 하나 가져오기 : DAO야, DB에서 tno=? 인 거 하나 가져와." */
    public TodoDTO get(Long tno) throws Exception {
        log.info("tno: " + tno);

        TodoVO todoVO = dao.selectOne(tno); // "DAO야, selectOne 메소드 이용해서 Todo 하나 가져와."
        TodoDTO todoDTO = modelMapper.map(todoVO, TodoDTO.class); // VO -> DTO 변환

        return todoDTO;
    }

    /* Todo 하나 수정하기 : DAO야, Todo 하나 DB에서 수정해." */
    public void modify(TodoDTO todoDTO) throws Exception {

        log.info("todoDTO: " + todoDTO);

        TodoVO todoVO = modelMapper.map(todoDTO, TodoVO.class); // DTO -> VO

        dao.updateOne(todoVO); // 등록과 흐름 같음
    }

    /* Todo 하나 삭제하기 : DAO야, Todo 하나 DB에서 삭제해." */
    public int remove(Long tno) throws Exception {

        log.info("tno: " + tno);

        int cnt = dao.deleteOne(tno);

        return cnt;
    }
}
