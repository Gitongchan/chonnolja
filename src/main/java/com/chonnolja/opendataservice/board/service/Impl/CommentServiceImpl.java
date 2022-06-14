package com.chonnolja.opendataservice.board.service.Impl;

import com.chonnolja.opendataservice.board.dto.request.ReqCommentDto;
import com.chonnolja.opendataservice.board.dto.response.ResCommentListDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.model.Board_Comment;
import com.chonnolja.opendataservice.board.repository.BoardRepository;
import com.chonnolja.opendataservice.board.repository.CommentRepository;
import com.chonnolja.opendataservice.board.service.CommentService;
import com.chonnolja.opendataservice.exception.CustomException;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final BoardRepository boardRepository;

    //댓글 조회
    @Override
    public ResCommentListDto commentList(Long id, Pageable pageable, int page) {

        //삭제여부가 "N"의 값만 가져오기 위한 변수
        final String deleted = "N";

        //해당 bdid값이 없다면 exception 값 전달
        Board board = boardRepository.findByBdId(id)
                .orElseThrow(() -> new CustomException.ResourceNotFoundException("해당 게시글 찾을 수 없습니다."));

        //bd_id, deleted = N 값인 댓글만 페이징해서 조회
        pageable = PageRequest.of(page, 10, Sort.by("cmCreated").descending());
        Page<Board_Comment> board_comments = commentRepository.findAllByBoardAndCmDeleted(board, deleted, pageable);
        
        //paging한 baord_comments를 .getContent()하여 리스트로 변환하여 담아줌
        List<Board_Comment> commentList = board_comments.getContent();

        //Dto에 값을 담아주기 위한 List<Map>
        List<Map<String, Object>> mapComments = new ArrayList<>();

        //return해줄 Dto 객체 생성
        ResCommentListDto resCommentListDto = new ResCommentListDto();

        //List 값을 반복문으로 Map에 담아줌
        commentList.forEach(entity -> {
            Map<String, Object> commentsMap = new HashMap<>();
            commentsMap.put("cm_id", entity.getCmId());
            commentsMap.put("cm_content", entity.getCmContent());
            commentsMap.put("cm_writer", entity.getCmWriter());
            commentsMap.put("cm_created", entity.getCmCreated());
            commentsMap.put("cm_modify", entity.getCmModified());
            mapComments.add(commentsMap);
        });

        //Dto 값 set
        resCommentListDto.setComments(mapComments);

        return resCommentListDto;
    }

    //댓글 작성
    @Override
    public Long commentWrite(UserInfo userInfo, ReqCommentDto reqCommentDto, Long id) {

        //게시글 먼저 있는지 확인 후 회원 정보와 게시글 db 가져옴
        if (boardRepository.findById(id).isPresent()) {

            if (userRepository.findById(userInfo.getId()).isPresent()) {

                Board recentBoard = boardRepository.findById(id).get();

                UserInfo recentUserInfo = userRepository.findById(userInfo.getId()).get();

                Board_Comment board_Comment;

                commentRepository.save(
                       board_Comment = Board_Comment.builder()
                                .cmContent(reqCommentDto.getCmContent())
                                .cmWriter(recentUserInfo.getUserid())
                                .board(recentBoard)
                                .userInfo(recentUserInfo)
                                .build()
                );
                return board_Comment.getCmId();
            }
        }
        return -1L;
    }

    //댓글 수정
    @Override
    public Long commentEdit(Long bd_id, Long cm_id, UserInfo userInfo, ReqCommentDto reqCommentDto) {

        //게시글 먼저 있는지 확인 후 회원 정보와 게시글 db 가져옴
        if (boardRepository.findByBdId(bd_id).isPresent()) {

            if (userRepository.findById(userInfo.getId()).isPresent()) {

                Board recentBoard = boardRepository.findByBdId(bd_id).get();

                UserInfo recentUserInfo = userRepository.findById(userInfo.getId()).get();

                Board_Comment board_Comment = commentRepository.findByBoardAndCmId(recentBoard,cm_id);

                commentRepository.save(
                        Board_Comment.builder()
                                .cmId(board_Comment.getCmId())
                                .cmContent(reqCommentDto.getCmContent())
                                .cmWriter(recentUserInfo.getUserid())
                                .cmDeleted(board_Comment.getCmDeleted())
                                .cmCreated(board_Comment.getCmCreated())
                                .cmModified(board_Comment.getCmModified())
                                .userInfo(recentUserInfo)
                                .board(recentBoard)
                                .build()
                );
                return board_Comment.getCmId();
            }
        }
        return -1L;
    }

    //댓글 삭제 여부 변경
    @Override
    public Long commentDelete(Long bd_id, Long cm_id, UserInfo userInfo) {

        //해당 id의 null값 체크 후 값이 있으면  deleted 값 변경
        if (boardRepository.findByBdId(bd_id).isPresent()) {

            Board board = boardRepository.findByBdId(bd_id).get();

            if(userRepository.findById(userInfo.getId()).isPresent()) {

                Board_Comment board_Comment = commentRepository.findByBoardAndCmId(board, cm_id);

                //deleted 값 변경
                commentRepository.updateDeleted(cm_id);

                return board_Comment.getCmId();
            }
        }
        return -1L;
    }
}

