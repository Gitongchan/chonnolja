package com.chonnolja.opendataservice.board.controller;
import com.chonnolja.opendataservice.board.dto.request.ReqBoardWriteDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

    @GetMapping("/list")
    public List<Board> list() {
        return boardService.list();
    }

    @PostMapping("/write")
    public ResBoardWriteDto write(ReqBoardWriteDto BoardDto) {
        return boardService.write(BoardDto);
    }
    
    // 4/4 게시판 수정페이지 이동 (수정해야함 이거 아닌거같음)
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable Long id, Board board) {
        return "/post/edit";
    }

}
