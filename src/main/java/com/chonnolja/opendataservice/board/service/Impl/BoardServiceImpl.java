package com.chonnolja.opendataservice.board.service.Impl;

import com.chonnolja.opendataservice.board.dto.BoardDto;
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

    @Override
    public List<Board> list() {
        return boardRepository.findAll();
    }

    @Override
    public ResBoardWriteDto write(BoardDto boardDto) {
        final Long id = boardRepository.save(
                Board.builder()
                        .id(boardDto.getId())
                        .title(boardDto.getTitle())
                        .writer(boardDto.getWriter())
                        .content(boardDto.getContent())
                        .build()
        ).getId();
        return new ResBoardWriteDto(id);
    }

    @Override
    public ResBoardUpdateDto update(Board board) {
        return null;
    }
}
