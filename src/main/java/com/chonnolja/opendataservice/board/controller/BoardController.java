package com.chonnolja.opendataservice.board.controller;
import com.chonnolja.opendataservice.board.dto.BoardDto;
import com.chonnolja.opendataservice.board.dto.response.BoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired private final BoardService boardService;

    @GetMapping("/write")
    public BoardWriteDto write(Board board) {
        return boardService.write(board);
    }

    @GetMapping("/list")
    public List<Board> list() {
        return boardService.list();
    }
}
