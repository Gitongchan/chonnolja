package com.chonnolja.opendataservice.board.repository;

import com.chonnolja.opendataservice.board.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, Long> {
}
