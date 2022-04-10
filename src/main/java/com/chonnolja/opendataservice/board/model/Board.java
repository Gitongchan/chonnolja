package com.chonnolja.opendataservice.board.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    //id auto_increment 사용
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bd_id")
    private Long bd_id;

    @Column(name= "bd_type")
    private String bd_type;

    @Column(name = "bd_number")
    private int bd_number;

    @Column(name = "bd_views")
    private int bd_views;

    //사용자 id 넣어야함
    @Column(name = "bd_writer")
    private String bd_writer;

    @Column(name = "bd_title")
    private String bd_title;

    @Column(name = "bd_content", columnDefinition = "TEXT")
    private String bd_content;

    @Column(name = "bd_filepath")
    private String bd_filepath;

    @Column(name = "bd_deleted")
    private boolean bd_deleted;

    //setter 대신 사용 , 나머지 매개변수들 넣어야함
    @Builder
    public Board(Long bd_id, String bd_writer,String bd_title, String bd_content) {
        this.bd_id = bd_id;
        this.bd_writer = bd_writer;
        this.bd_title = bd_title;
        this.bd_content = bd_content;
    }
}
