package com.chonnolja.opendataservice.board.service.Impl;

import com.chonnolja.opendataservice.board.dto.request.ReqBoardWriteDto;
<<<<<<< HEAD
=======
import com.chonnolja.opendataservice.board.dto.request.ReqFileUploadDto;
>>>>>>> develop
import com.chonnolja.opendataservice.board.dto.response.ResBoardUpdateDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.repository.BoardRepository;
import com.chonnolja.opendataservice.board.service.BoardService;
<<<<<<< HEAD
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
=======
import com.chonnolja.opendataservice.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

>>>>>>> develop
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired private final BoardRepository boardRepository;

    //게시판 목록
    @Override
    public List<Board> list() {
        return boardRepository.findAll();
    }

<<<<<<< HEAD
    @Override
    public ResBoardWriteDto write(ReqBoardWriteDto boardDto) {
        final Long id = boardRepository.save(
                Board.builder()
                        .bd_id(boardDto.getBd_id())
                        .bd_title(boardDto.getBd_title())
                        .bd_writer(boardDto.getBd_writer())
                        .bd_content(boardDto.getBd_content())
=======
    //jpa는 id값만 확인하기 때문에 외래키로 설정한 값에 그대로 넣어주면 DB 테이블에 id값 들어옴!
    //.userInfo(userInfo)안해주면 외래키로 설정된 컬럼에 null값이 박힘
    @Override
    public ResBoardWriteDto write(ReqBoardWriteDto reqBoardWriteDto, ReqFileUploadDto reqFileUploadDto, MultipartFile files, UserInfo userInfo) {
        final Long id = boardRepository.save(
                Board.builder()
                        .bd_id(reqBoardWriteDto.getBd_id())
                        .bd_type(reqBoardWriteDto.getBd_type())
                        .bd_views(reqBoardWriteDto.getBd_views())
                        .bd_writer(userInfo.getUserid())
                        .bd_title(reqBoardWriteDto.getBd_title())
                        .bd_content(reqBoardWriteDto.getBd_content())
                        .bd_deleted(reqBoardWriteDto.getBd_deleted())
                        .userInfo(userInfo)
>>>>>>> develop
                        .build()
        ).getBd_id();
        return new ResBoardWriteDto(id);
    }

    @Override
    public ResBoardUpdateDto update(Board board) {
        return null;
    }
}
