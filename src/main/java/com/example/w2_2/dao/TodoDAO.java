package com.example.w2_2.dao;
/* CRUD */
import com.example.w2_2.domain.TodoVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TodoDAO { /* getTime (DB 연결 테스트 용) */
    public String getTime() { //DB의 현재 시간을 가져오는 메소드

        String now = null;

        // @Cleanup과 try-with-resources는 '자원 자동 정리'라는 같은 목적. 다른 방식!
        try (Connection connection =
                     ConnectionUtil.INSTANCE.getConnection();
             PreparedStatement prepareStatement =
                    connection.prepareStatement("select now()");
                    // DB에 (sql)전달하는 것처럼 ("select now()") 전달할 준비
             ResultSet resultSet =
                     prepareStatement.executeQuery();
             // "select now()" 실행 -> executeQuery : 현재 시간 select -> ResultSet에 결과 반환
             ) {

                resultSet.next();
                now = resultSet.getString(1);
                // ★ ResultSet의 첫번째 컬럼의 값을 String으로 가져와서 now 변수에 넣어라 ★

            } catch (Exception e) {
            e.printStackTrace();
        }
        return now;
    }

    public String getTime2() throws Exception {
        // getTime1과 목적 같음. 방식 비교하려고 넣음
        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement("select now()");
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        resultSet.next();
        String now = resultSet.getString(1);

        return now;
    }

    /* Create -> insert */
    public int insert(TodoVO vo) throws Exception {
        // TodoVO를 받아서 DB에 넣기

        String sql = "insert into tbl_todo (title, dueDate, finished)" +
                     "values (?, ?, ?)";

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setString(1, vo.getTitle()); // 첫 번째 ?에 vo.getTitle() 넣기
        preparedStatement.setDate(2, Date.valueOf(vo.getDueDate())); // 'private LocalDate dueDate'를 DB에 넣을 수 있는 java.sql.Date로 변환하는 것
        preparedStatement.setBoolean(3, vo.isFinished()); // 완료 여부 넣기

        int cnt = preparedStatement.executeUpdate();

        return cnt; // 한 행이 정상적으로 추가되면 cnt=1
    }

    /* Read -> selectAll */
    public List<TodoVO> selectAll() throws Exception {

        String sql = "select * " +
                     "from tbl_todo";

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
        @Cleanup ResultSet resultSet =
                preparedStatement.executeQuery();

        List<TodoVO> list = new ArrayList<>(); // list=[] 빈 list 만들기

        while (resultSet.next()) { // Todo 여러 개를 가져올 거라서
                                   // (1번 째 행 -> TodoVO 생성 -> list에 추가) 을 반복
            TodoVO vo = TodoVO.builder()
                    .tno(resultSet.getLong("tno")) // 번호 대신 칼럼의 이름을 쓴 것! (MemberDAO와 작성 방식 차이)
                    .title(resultSet.getString("title"))
                    .dueDate(resultSet.getDate("dueDate").toLocalDate())
                    .finished(resultSet.getBoolean("finished"))
                    .build();
            list.add(vo);
        }

        return list;
    }

    /* Read -> selectOne */
    public TodoVO selectOne(Long tno) throws Exception {

        String sql = "select * " +
                     "from tbl_todo " +
                     "where tno = ?";

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno); // 실제 값 넣기

        @Cleanup ResultSet resultSet =
                preparedStatement.executeQuery();

        resultSet.next();

        TodoVO vo = TodoVO.builder()
                .tno(resultSet.getLong("tno"))
                .title(resultSet.getString("title"))
                .dueDate(resultSet.getDate("dueDate").toLocalDate())
                .finished(resultSet.getBoolean("finished"))
                .build();

        return vo;
    }

    /* Delete -> deleteOne */
    public int deleteOne(Long tno) throws Exception {

        String sql = "delete from tbl_todo " +
                     "where tno = ?";

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setLong(1, tno); // 실제 값 넣기

        int cnt = preparedStatement.executeUpdate();

        return cnt;
    }

    /* Update -> updateOne */
    public int updateOne(TodoVO todoVO) throws Exception {

        String sql = "update tbl_todo " +
                     "set title =?, dueDate = ?, finished = ? " +
                     "where tno = ?";

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setString(1, todoVO.getTitle());
        preparedStatement.setDate(2, Date.valueOf(todoVO.getDueDate()));
        preparedStatement.setBoolean(3, todoVO.isFinished());
        preparedStatement.setLong(4, todoVO.getTno());

        int cnt = preparedStatement.executeUpdate();

        return cnt;

    }

}
