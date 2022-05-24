package com.chonnolja.opendataservice.village.controller;

import com.chonnolja.opendataservice.user.dto.response.ResDupliCheckDto;
import com.chonnolja.opendataservice.user.service.UserService;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import com.chonnolja.opendataservice.village.dto.request.VillageUserInfoDto;
import com.chonnolja.opendataservice.village.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/village")
public class VillagePermitAllController {
    @Autowired private final VillageService villageService;
    @Autowired private final UserService userService;

    //업체명 중복 체크
    @GetMapping("/name_check")
    public ResDupliCheckDto villageNameCheck(@RequestParam("villageName") String villageName){
        return new ResDupliCheckDto(villageService.villageNameCheck(villageName));
    }
    //사업자 회원가입
    @PostMapping("/signup")
    public ResResultDto villageRegister(@RequestBody VillageUserInfoDto villageUserInfoDto){

        Integer idCheckResult = userService.userIdCheck(villageUserInfoDto.getUserid());
        Integer emailCheckResult = userService.emailCheck(villageUserInfoDto.getEmail());

        //중복 id,email 검증
        if(idCheckResult.equals(-1)||emailCheckResult.equals(-1)) {
            return new ResResultDto(-1L,"회원가입 실패, 아이디,이메일을 다시 확인하세요.");
        }else{
            Long result = villageService.villageRegister(villageUserInfoDto);
            return result == -1L ?
                    new ResResultDto(result,"사업자 등록 실패.") : new ResResultDto(result,"사업자 등록 성공.");
        }
    }
}