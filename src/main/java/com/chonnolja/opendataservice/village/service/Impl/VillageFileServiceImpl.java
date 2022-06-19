package com.chonnolja.opendataservice.village.service.Impl;

import com.chonnolja.opendataservice.village.service.VillageFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class VillageFileServiceImpl implements VillageFileService {

    @Override
    public String thumbFile(MultipartFile thumb) {

        String thumbNailPath = System.getProperty("user.dir") + "\\src\\main\\resources\\static\\village_thumbNail";

        String originFileName = thumb.getOriginalFilename().toLowerCase();

        //UUID로 파일명 중복되지 않게 유일한 식별자 + 확장자로 저장
        UUID uuid = UUID.randomUUID();
        String saveFileName = uuid + "__" + originFileName;

        //파일 저장되는 폳더 없으면 생성
        if(!new File(thumbNailPath).exists()) {
            try{
                new File(thumbNailPath).mkdir();
            } catch (Exception e2) {
                e2.getStackTrace();
            }
        }

        //File로 저장 경로와 저장 할 파일명 합쳐서 transferTo() 사용하여 업로드하려는 파일을 해당 경로로 저장
        String filepath = thumbNailPath + "\\" + saveFileName;
        String dbFilePath = "..\\village_thumbNail" + "\\" + saveFileName;

        System.out.println(filepath);
        try {
            thumb.transferTo(new File(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dbFilePath;

    }

}
