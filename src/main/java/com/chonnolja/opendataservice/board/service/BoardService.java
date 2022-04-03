package com.chonnolja.opendataservice.board.service;

import com.chonnolja.opendataservice.board.dto.BoardDto;
import com.chonnolja.opendataservice.board.dto.response.BoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;

public interface BoardService {

    //게시글 작성
    BoardWriteDto write(Board board);
}
