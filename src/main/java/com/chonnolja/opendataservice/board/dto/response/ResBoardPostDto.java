package com.chonnolja.opendataservice.board.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResBoardPostDto {
    
    //게시글 정보
    private Map<String, Object> post;

    //파일 정보
    private List<Map<String,Object>> files;

}
