package com.example.w2_2.dao;
/* DAO는 DB와 직접 대화하는 담당자 */
import com.example.w2_2.domain.MemberVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MemberDAO { /* 회원 테이블 DB 작업을 담당하는 클래스 */

    /* 아이디 + 비밀번호로 회원 조회 */
    public MemberVO getWithPassword(String mid, String mpw)
            throws Exception { // "아이디와 비번(매개변수)을 줄테니까 DB에서 해당회원을 찾아서 MemberVO로 주세요."
                               // getWithPassword("hong", "1234")로 호출됨
        String sql = "select mid, mpw, mname " +
                     "from tbl_member " +
                     "where mid = ? and mpw = ?";
                    // ? = 아직 값을 넣지 않은 상태. 나중에 입력

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
            // "(ConnectionUtill에게) DB에 연결할 수 있는 Connection 하나 주세요." -> Connection connection에 DB 연결 통로가 들어옴
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);
            // 위에서 만든 sql을 DB에 전달할 준비

        preparedStatement.setString(1, mid); // 실제 값 넣기 : mid = "hong"
        preparedStatement.setString(2, mpw); // 실제 값 넣기 : mpw = "1234"

        @Cleanup ResultSet resultSet =
                preparedStatement.executeQuery();
            // ★ sql 실행 -> executeQuery : DB 조회 후 select -> ResultSet에 결과 들어옴

        resultSet.next(); // 원래 ResultSet은 처음 만들어졌을 때 첫 번째 데이터 바로 위에 커서가 있어서, 다음 행(첫번째)으로 이동!

        // DB에서 가져온 데이터를 MemberVO로 포장, 순서는 sql select 순서
        MemberVO vo = MemberVO.builder()
                .mid(resultSet.getString(1)) // resultSet의 첫번째 칼럼의 값을 String으로 가져와서 mid 변수에 넣어라
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .build(); // 마무리. "이제 이 값들로 진짜 TodoVO 객체 만들어줘!"
        // build가 없다면 TodoVO vo = new TodoVO(1, "공부", 날짜, false); 이런식임

        return vo; // ★ "찾아봤더니 이 회원입니다." 하고 Service에게 MemberVO를 넘겨주는 것 ★
    }

    // @cleanup은 DB작업이 끝나면 자동으로 자원을 정리해줌. 직접 하려면 resultSet.close(); 같은 거 해줘야함


    /* 회원의 UUID 저장 & 수정 (return값 필요없어서 void) */
    public void updateUuid(String mid, String uuid)
            throws Exception {

        String sql = "update tbl_member " +
                     "set uuid = ? " +
                     "where mid = ?";

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setString(1, uuid); // 실제 값 넣기
        preparedStatement.setString(2, mid); // 실제 값 넣기

        preparedStatement.executeUpdate();
        // ★ sql 실행 -> executeUpdate : DB 데이터를 변경 (INSERT/UPDATE/DELETE)
    }


    /* UUID로 회원 조회 */
    public MemberVO selectUUID(String uuid)
            throws Exception {

        String sql = "select mid, mpw, mname, uuid " +
                     "from tbl_member " +
                     "where uuid = ?";

        @Cleanup Connection connection =
                ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement =
                connection.prepareStatement(sql);

        preparedStatement.setString(1, uuid); //  실제 값 넣기

        @Cleanup ResultSet resultSet
                = preparedStatement.executeQuery();
        // ★ sql 실행 -> executeQuery : DB 조회 후 select -> ResultSet에 결과 들어옴

        resultSet.next();

        // DB에서 가져온 데이터를 MemberVO로 포장, 순서는 sql select 순서
        MemberVO vo = MemberVO.builder()
                .mid(resultSet.getString(1))
                .mpw(resultSet.getString(2))
                .mname(resultSet.getString(3))
                .uuid(resultSet.getString(4))
                .build();

        return vo; // // ★ "찾아봤더니 이 회원입니다." 하고 Service에게 MemberVO를 넘겨주는 것 ★
    }

}
