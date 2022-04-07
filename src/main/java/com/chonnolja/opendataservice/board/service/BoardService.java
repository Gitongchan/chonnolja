package com.chonnolja.opendataservice.board.service;

import com.chonnolja.opendataservice.board.dto.BoardDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardUpdateDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;

import java.util.List;

public interface BoardService {

    //게시판 리스트
    List<Board> list();
    //게시글 보기

    //게시글 작성
    ResBoardWriteDto write(BoardDto boarddto);

    //게시글 수정
    ResBoardUpdateDto update(Board board);
}
