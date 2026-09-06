package com.example.w2_2.domain;
/* VO는 DB의 데이터를 표현 */
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TodoVO {

    // Todo 하나를 표현하는 객체에 필요한 데이터들
    // 테이블의 한 행을 담당한다고 보면 됨
    private long tno;
    private String title;
    private LocalDate dueDate;
    private boolean finished;
}
