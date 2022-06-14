package com.chonnolja.opendataservice.board.service;

import com.chonnolja.opendataservice.board.dto.request.ReqCommentDto;
import com.chonnolja.opendataservice.board.dto.response.ResCommentListDto;
import com.chonnolja.opendataservice.user.model.UserInfo;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    //댓글 조회
    ResCommentListDto commentList (Long id, Pageable pageable, int page);

    //댓글 작성
    Long commentWrite(UserInfo userInfo, ReqCommentDto reqCommentDto, Long id);
    
    //댓글 수정
    Long commentEdit(Long bd_id, Long cm_id, UserInfo userInfo, ReqCommentDto reqCommentDto);

    //댓글 삭제 여부 변경
    Long commentDelete(Long bd_id, Long cm_id, UserInfo userInfo);
}
