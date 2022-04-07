package com.chonnolja.opendataservice.board.dto;

import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto {

    // @AllArgsConstructor로 모든 필드의 값을 파라미터로 받는 생성자 생성하여 form값 받아옴
    private Long id;
    private String title;
    private String writer;
    private String content;
}
