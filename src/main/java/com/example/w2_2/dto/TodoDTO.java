package com.example.w2_2.dto;
// VO는 테이블과 1:1로 대응하고, DTO는 화면과 주고받는 데이터를 담음


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data /* 여러 어노테이션을 한번에 적용해주는 통합 단축키 ex) getter setter 등 */
@NoArgsConstructor
@AllArgsConstructor
public class TodoDTO {

    private Long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;

}
