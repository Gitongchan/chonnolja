package com.chonnolja.opendataservice.board.service;

import com.chonnolja.opendataservice.board.model.Board;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface FilesService {

    //파일 저장
    Long saveFileUpload(List<MultipartFile> files, Board board);
}
