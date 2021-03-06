package com.chonnolja.opendataservice.board.controller;

import com.chonnolja.opendataservice.board.dto.response.ResBoardListDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardPostDto;
import com.chonnolja.opendataservice.board.dto.response.ResCommentListDto;
import com.chonnolja.opendataservice.board.service.BoardService;
import com.chonnolja.opendataservice.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardPermitAllController {

    private final BoardService boardService;
    private final CommentService commentService;

    //게시판 목록
    @GetMapping("/list/{type}")
    public ResBoardListDto boardList(Pageable pageable,
                                     @PathVariable("type")
                                           String board_type, int page) {
        return boardService.boardList(pageable, board_type, page);
    }

    //게시글 조회
    @GetMapping("/post/{id}")
    public ResBoardPostDto getPost(@PathVariable("id") Long id,
                                   HttpServletRequest request, HttpServletResponse response) {
        return boardService.getPost(id, request, response);
    }

    //댓글 조회
    @GetMapping("/post/comments/{id}")
    public ResCommentListDto commnetList(@PathVariable("id") Long id,
                                         Pageable pageable, int page) {
        return commentService.commentList(id, pageable, page);
    }

}
