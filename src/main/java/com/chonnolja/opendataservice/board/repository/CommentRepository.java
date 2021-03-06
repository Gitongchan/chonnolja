package com.chonnolja.opendataservice.board.repository;

import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.model.Board_Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface CommentRepository extends JpaRepository<Board_Comment, Long> {

    //댓글 수정 시 bd_id, cm_id에 맞는 댓글만 수정
    Board_Comment findByBoardAndCmId(Board board, Long id);

    //댓글 조회
    Page<Board_Comment> findAllByBoardAndCmDeleted(Board board, String Deleted, Pageable pageable);

    //댓글 삭제 여부 변경
    @Transactional
    @Modifying
    @Query("update Board_Comment cm set cm.cmDeleted = 'Y' where cm.cmId = :id")
    void updateDeleted(@Param("id") Long id);
}
