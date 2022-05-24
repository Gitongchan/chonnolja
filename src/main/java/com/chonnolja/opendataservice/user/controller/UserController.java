package com.chonnolja.opendataservice.user.controller;

import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.village.service.VillageService;
import com.chonnolja.opendataservice.user.dto.request.UserInfoDto;
import com.chonnolja.opendataservice.user.dto.response.ResUserInfoDto;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.service.UserService;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController{
    @Autowired private final UserService userService;
    @Autowired private final VillageService villageService;

    /**              마이페이지 기능               **/
    //회원정보 조회
    @GetMapping("")
    public ResUserInfoDto check(@LoginUser UserInfo userInfo){
        return new ResUserInfoDto(userService.userInfoCheck(userInfo));
    }

    //회원정보 수정
    @PutMapping("")
    public ResResultDto update(@LoginUser UserInfo userInfo, @Valid @RequestBody UserInfoDto userInfoDto){

        System.out.println("userInfo = " + userInfo.getName());
        System.out.println("userInfoDto = " + userInfoDto.getName());
        Long result = userService.update(userInfo,userInfoDto);
        return result == -1L ?
                new ResResultDto(result,"회원정보 변경 실패.") : new ResResultDto(result,"회원정보 변경 성공.");
    }

    //회원 탈퇴
    @PutMapping("/user_deleted")
    public ResResultDto userDeleted(@LoginUser UserInfo userInfo, @RequestBody UserInfoDto userInfoDto){
        Long result = userService.userDeleted(userInfo,userInfoDto);

        if(result.equals(-1L)){
            return new ResResultDto(result,"회원 탈퇴 실패");
        }
        else if(result.equals(-2L)){
            return new ResResultDto(result,"일반회원만 탈퇴할 수 있습니다");
        }
        else return new ResResultDto(result,"회원 탈퇴 성공");

    }

    //사업자 회원 가입
    @PostMapping("/village_register")
    public ResResultDto villageRegister(@LoginUser UserInfo userInfo ,
                                        UserInfoDto userInfoDto, @RequestBody VillageInfoDto villageInfoDto){

        //회사명 중복 체크
        if(villageService.villageNameCheck(villageInfoDto.getVillageName()).equals(-1)){
            return new ResResultDto(-3L,"사업자 등록 실패, 이미 사용되고있는 회사명입니다");
        }

        Long result = userService.villageRegister(userInfo,userInfoDto, villageInfoDto);

        if(result == -1L) {
            return new ResResultDto(result,"사업자 등록 실패, 유저 정보를 받아올 수 없습니다.");
        }
        else if(result == -2L) {
            return new ResResultDto(result,"사업자 등록 실패, 이미 사업자로 등록되었습니다.");
        }
        else{
            return new ResResultDto(result,"사업자 등록 성공.");
        }

    }

    //탈퇴한 사업자 복구
    @PutMapping("/villageinfo_restore")
    public ResResultDto villageRestore(@LoginUser UserInfo userInfo , UserInfoDto userInfoDto, VillageInfoDto villageInfoDto){

        Long result = userService.villageRestore(userInfo,userInfoDto, villageInfoDto);
        return result == -1L ?
                new ResResultDto(result,"사업자 정보 복구 실패.") : new ResResultDto(result,"사업자 정보 복구 성공.");
    }


}
