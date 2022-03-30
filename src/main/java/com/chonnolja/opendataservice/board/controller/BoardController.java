package com.chonnolja.opendataservice.board.controller;
import com.chonnolja.opendataservice.board.dto.BoardDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public String BoardList(Model model) {
        List<Board> boards = boardService.postList();
        model.addAttribute("board", boards);
        return "board/list";
    }

    @GetMapping("/write")
    public String BoardWrite(BoardDto boardDto) {
        boardService.savePost(boardDto);
        return "redirect:list";
    }

}
