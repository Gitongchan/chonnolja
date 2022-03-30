package com.chonnolja.opendataservice.board.service;

import com.chonnolja.opendataservice.board.dto.BoardDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {
    @Autowired private BoardRepository boardRepository;

    public Long savePost(BoardDto boardDto) {
        return boardRepository.save(boardDto.toEntity()).getId();
    }

    public List<Board> postList() {
        return boardRepository.findAll();
    }
}
