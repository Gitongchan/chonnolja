package com.chonnolja.opendataservice.board.repository;

import com.chonnolja.opendataservice.board.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {

    //게시글 아이디 조회
    Optional<Board> findByBdId(Long id);

    //게시판 목록 페이징
    Page<Board> findAllByBdDeletedAndBdType(String deleted, String type, Pageable pageable);

    //조회수 증가
    @Transactional
    @Modifying
    @Query("update Board b set b.bdViews = b.bdViews + 1 where b.bdId = :id")
    void updateView(@Param("id") Long id);

    //게시글 삭제 여부 변경
    @Transactional
    @Modifying
    @Query("update Board b set b.bdDeleted = 'Y' where b.bdId = :id")
    void updateDeleted(@Param("id") Long id);
}
