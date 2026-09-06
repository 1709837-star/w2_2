package com.example.w2_2.dto;
/* DTO는 DB의 데이터를 다른 곳으로 전달하기 위한 객체 "전달용 상자" */
/* 회원정보를 입력받아서 Controller가 들고다니는 게 아니라, 상자에 넣어서 전달 */
/* VO와 DTO는 둘 다 데이터가 들어있지만 사용 목적이 다름 */
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/* Lombok */
@Data // 여러 어노테이션을 한번에 적용해주는 통합 단축키 ex) getter setter 등 -> 데이터를 담고, 전달하고, 수정하는 것도 가능
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDTO {

    private String mid;
    private String mpw;
    private String mname;
    private String uuid;
}
