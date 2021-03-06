package com.chonnolja.opendataservice.board.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board_Files {

    @Id @GeneratedValue
    @Column(name = "f_id")
    private Long fileId;

    @Column(name="f_origin")
    private String fileOrigin;

    @Column(name="f_savename")
    private String fileSaveName;

    @Column(name="f_path")
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "bd_id")
    private Board board;

    @Builder
    public Board_Files(Long fileId, String fileOrigin, String fileSaveName, String filePath, Board board) {
        this.fileId = fileId;
        this.fileOrigin = fileOrigin;
        this.fileSaveName = fileSaveName;
        this.filePath = filePath;
        this.board = board;
    }

}
