package com.chonnolja.opendataservice.board.controller;
import com.chonnolja.opendataservice.board.dto.request.ReqBoardWriteDto;
<<<<<<< HEAD
import com.chonnolja.opendataservice.board.dto.response.ResBoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
=======
import com.chonnolja.opendataservice.board.dto.request.ReqFileUploadDto;
import com.chonnolja.opendataservice.board.dto.response.ResBoardWriteDto;
import com.chonnolja.opendataservice.board.model.Board;
import com.chonnolja.opendataservice.board.service.BoardService;
import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.util.List;
import java.util.UUID;
>>>>>>> develop

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/board")
public class BoardController {

    @Autowired
    private final BoardService boardService;

<<<<<<< HEAD
=======
    //게시판 목록
>>>>>>> develop
    @GetMapping("/list")
    public List<Board> list() {
        return boardService.list();
    }

<<<<<<< HEAD
    @PostMapping("/write")
    public ResBoardWriteDto write(ReqBoardWriteDto BoardDto) {
        return boardService.write(BoardDto);
    }
    
    // 4/4 게시판 수정페이지 이동 (수정해야함 이거 아닌거같음)
    @GetMapping("/post/edit/{id}")
    public String edit(@PathVariable Long id, Board board) {
        return "/post/edit";
    }

=======
    //게시글 작성
    @PostMapping("/postwrite")
    public ResBoardWriteDto write(@Valid @RequestBody ReqBoardWriteDto reqBoardWriteDto, ReqFileUploadDto reqFileUploadDto,
                                  MultipartFile files, @LoginUser UserInfo userInfo) {


        //이 작업을 service에서 해줘야 할거같은데 그럼 글 작성과 동시에 build로 db에 값을 넣을 수 있나?, file의 Long id만 넣어주는게 되나?
        //file은 다른 테이블에 저장되기 때문에 따로 mapping을 따서 fileupload 메소드를 만들어줘야 할 거 같은 생각
        try {
            //UUID로 파일명 중복되지 않게 유일한 식별자로 파일명 저장
            UUID uuid = UUID.randomUUID();
            //실제 첨부파일로 등록된 파일명
            String originFileName = files.getOriginalFilename();
            //db에 저장 할 파일명
            String fileName = uuid + "__" + originFileName;
            //파일 저장 경로
            String savepath = System.getProperty("user.dir") + "\\files";

            //파일 저장되는 폳더 없으면 생성
            if(!new File(savepath).exists()) {
                try{
                    new File(savepath).mkdir();
                } catch (Exception e2) {
                    e2.getStackTrace();
                }
            }

            //File로 저장 경로와 저장 할 파일명 합쳐서 multipartfile의 transferTo() 사용하여 업로드하려는 파일을 해당 경로로 저장
            File filepath = new File(savepath + fileName);
            files.transferTo(filepath);

            } catch (Exception e1) {
                e1.getStackTrace();
            }
        System.out.println("commit test");
        return boardService.write(reqBoardWriteDto, reqFileUploadDto, files, userInfo);
    }

    //게시글 수정
//    @PutMapping("/post/edit/{id}")
//    public String edit(@PathVariable Long id, Board board) {
//        return "/post/edit";
//    }

>>>>>>> develop
}
