package com.chonnolja.opendataservice.board.repository;

import com.chonnolja.opendataservice.board.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
