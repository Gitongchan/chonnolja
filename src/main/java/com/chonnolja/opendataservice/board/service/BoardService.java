package com.chonnolja.opendataservice.board.service;

import com.chonnolja.opendataservice.board.dto.request.ReqBoardWriteDto;
import com.chonnolja.opendataservice.board.dto.request.ReqFileUploadDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardUpdateDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.user.model.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BoardService {

    //게시판 목록
    List<Board> list();

    //게시글 작성
    ResBoardWriteDto write(ReqBoardWriteDto reqBoardWriteDto, ReqFileUploadDto reqFileUploadDto, MultipartFile files, UserInfo userInfo);

    //게시글 수정
    ResBoardUpdateDto update(Board board);
}
