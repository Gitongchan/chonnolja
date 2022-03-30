package com.chonnolja.opendataservice.board.dto;

import com.chonnolja.opendataservice.board.model.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class BoardDto {

    private Long id;
    private String title;
    private String content;

    public Board toEntity() {
        Board build = Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
        return build;
    }

    public BoardDto(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
