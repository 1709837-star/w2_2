package com.example.dao;

import com.example.w2_2.dao.TodoDAO;
import com.example.w2_2.domain.TodoVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class TodoDAOTests {

    private TodoDAO todoDAO;
// 밑에 TEST 시작하기 전 사용할 객체 만들기
    @BeforeEach
    public void ready() {
        todoDAO = new TodoDAO();
    }

    @Test
    public void testTime() throws Exception {
        System.out.println(todoDAO.getTime());
    }

    @Test
    public void testTime2() throws Exception {
        System.out.println("time2 ->" + todoDAO.getTime2());
    }
// 행 삽입
    @Test
    public void testInsert() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021, 12, 31))
                .build();

        todoDAO.insert(todoVO);
    }

    @Test
    public void testList() throws Exception {

        List<TodoVO> list = todoDAO.selectAll();

        list.forEach(vo -> System.out.println(vo));
    }

    @Test
    public void testSelectOne() throws Exception {
        Long tno = 1L;
        TodoVO vo = todoDAO.selectOne(tno);
        System.out.println(vo);
    }
// 행 삭제
    @Test
    public void testDelete() throws Exception {
        Long tno = 7L;
        int cnt = todoDAO.deleteOne(tno); /* 삭제된 갯수 정수 반환 */
        System.out.println("success count: " + cnt);
    }

    @Test // 행 수정
    public void testUpdateOne() throws Exception {
        TodoVO todoVO = TodoVO.builder()
                .tno(2L)
                .title("Sample Title...")
                .dueDate(LocalDate.of(2021, 12, 31))
                .finished(true)
                .build();

        int cnt = todoDAO.updateOne(todoVO);

        System.out.println("update success count: " + cnt);
    }
}
