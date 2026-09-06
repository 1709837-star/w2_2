package com.example.w2_2.domain;
/* VO는 DB의 데이터를 표현 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

/* Lombok */
@Getter // 데이터를 읽는 것 중심
@ToString
@Builder // 어떤 값이 어떤 필드인지 잘 보이게 객체를 만들 수 있게 해 줌
@AllArgsConstructor // 모든 필드를 받는 생성자를 만들어 줌
@NoArgsConstructor // 매개변수가 없는 생성자를 만들어 줌
public class MemberVO {

    // Todo 하나를 표현하는 객체에 필요한 데이터들
    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}
