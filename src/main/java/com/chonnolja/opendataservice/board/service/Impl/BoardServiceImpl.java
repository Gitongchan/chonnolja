package com.chonnolja.opendataservice.board.service.Impl;

import com.chonnolja.opendataservice.board.dto.request.ReqBoardWriteDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardUpdateDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.repository.BoardRepository;
import com.chonnolja.opendataservice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    @Override
    public ResBoardWriteDto write(ReqBoardWriteDto boardDto) {
        final Long id = boardRepository.save(
                Board.builder()
                        .bd_id(boardDto.getBd_id())
                        .bd_title(boardDto.getBd_title())
                        .bd_writer(boardDto.getBd_writer())
                        .bd_content(boardDto.getBd_content())
                        .build()
        ).getBd_id();
        return new ResBoardWriteDto(id);
    }

    @Override
    public ResBoardUpdateDto update(Board board) {
        return null;
    }
}
