package com.chonnolja.opendataservice.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BoardWriteDto {

    private Long id;
    private String message;

    public BoardWriteDto(Long id) {
        this.id = id;
        this.message = "게시글 작성 성공";
    }
}
