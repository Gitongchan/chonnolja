package com.chonnolja.opendataservice.board.controller;


import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.board.dto.request.ReqCommentDto;
import com.chonnolja.opendataservice.board.service.CommentService;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 (게시글 id, 댓글 내용 받기)
    @PostMapping("/comment/write/{id}")
    public ResResultDto commentWrite(@LoginUser UserInfo userInfo,
                                     @Valid @RequestBody ReqCommentDto reqCommentDto,
                                     @PathVariable("id") Long id) {

        Long result = commentService.commentWrite(userInfo, reqCommentDto, id);
        return result == -1L ?
                new ResResultDto(result, "댓글 작성 실패") : new ResResultDto(result, "댓글 작성 성공");
    }

    // 댓글 수정 (게시글 id, 수정 댓글 id 받기)
    // 댓글의 id값도 받아서 정확히 해당 댓글만 수정
    @PutMapping("/comment/edit/{bd_id}/{cm_id}")
    public ResResultDto commentEdit(@LoginUser UserInfo userInfo,
                                    @PathVariable("bd_id") Long bd_id,
                                    @PathVariable("cm_id") Long cm_id,
                                    @RequestBody ReqCommentDto reqCommentDto) {

        Long result = commentService.commentEdit(bd_id, cm_id, userInfo, reqCommentDto);
        return result == -1L ?
                new ResResultDto(result, "댓글 수정 실패") : new ResResultDto(result, "댓글 수정 성공");
    }

    // 댓글 삭제 여부 변경 (게시글 id, 수정 댓글 id 받기)
    // 마찬가지로 댓글 id 받아서 해당 댓글만 삭제
    @PutMapping("/comment/delete/{bd_id}/{cm_id}")
    public ResResultDto commentDelete(@PathVariable(value = "bd_id") Long bd_id,
                                      @PathVariable(value = "cm_id") Long cm_id,
                                      @LoginUser UserInfo userInfo) {

        //게시글 삭제 여부 변경 로직 실행
        Long result = commentService.commentDelete(bd_id, cm_id, userInfo);

        return result == -1L ?
                new ResResultDto(result, "댓글 삭제 실패") : new ResResultDto(result, "댓글 삭제 성공");
    }
}
