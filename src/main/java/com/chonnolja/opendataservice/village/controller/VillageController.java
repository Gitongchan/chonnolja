package com.chonnolja.opendataservice.village.controller;


import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.village.dto.response.ResVillageInfoDto;
import com.chonnolja.opendataservice.village.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/manager")
public class VillageController {

    @Autowired private final VillageService villageService;
    


    //마을 탈퇴
    @PutMapping("/deleted")
    public ResResultDto villageDeleted(@LoginUser UserInfo userInfo){

        Long result = villageService.villageDeleted(userInfo);
        return result == -1L ?
                new ResResultDto(result,"사업자 탈퇴 실패.") : new ResResultDto(result,"사업자 탈퇴 성공.");
    }
    
    //마을 정보 수정
    @PutMapping("/update/{item}")
    public ResResultDto villageUpdate(@LoginUser UserInfo userInfo,
                                      @PathVariable("item") Long productId,
                                      @RequestPart(value = "villageInfoDto") VillageInfoDto villageInfoDto,
                                      @RequestPart(value = "deletedThumb",required=false) String deletedThumb,
                                      @RequestPart(value = "thumbFile",required=false) MultipartFile thumb){
        Long result = villageService.villageUpdate(userInfo,productId,villageInfoDto,deletedThumb,thumb);
        return result == -1L ?
                new ResResultDto(result,"회사정보 수정 실패.") : new ResResultDto(result,"회사정보 수정 성공.");
    }
    
    //마을 정보 조회
    @GetMapping("")
    public ResVillageInfoDto villageInfoCheck(@LoginUser UserInfo userInfo){
        return villageService.villageInfoCheck(userInfo);
    }


}
