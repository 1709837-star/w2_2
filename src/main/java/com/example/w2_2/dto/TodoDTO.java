package com.example.w2_2.dto;
/* DTO는 DB의 데이터를 다른 곳으로 전달하기 위한 객체 "전달용 상자" */
/* 회원정보를 입력받아서 Controller가 들고다니는 게 아니라, 상자에 넣어서 전달 */
/* VO와 DTO는 둘 다 데이터가 들어있지만 사용 목적이 다름 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long tno;
    // 정보를 입력받기 전엔 DB에서 번호를 부여받기 전이므로, tno=null으로 둘 수 있게 'Long'이라는 wrapper 객체형으로 설정
    private String title;
    private LocalDate dueDate;
    private boolean finished; // T/F 둘 중 하나만 가지는 값

}
