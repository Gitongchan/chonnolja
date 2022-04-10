package com.chonnolja.opendataservice.user.controller;

import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.user.dto.request.UserInfoDto;
import com.chonnolja.opendataservice.user.dto.response.ResDupliCheckDto;
import com.chonnolja.opendataservice.user.dto.response.ResUserInfoDto;
import com.chonnolja.opendataservice.user.dto.response.ResUserResultDto;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController{
    @Autowired private final UserService userService;

    @GetMapping(value = "/token")
    public CsrfToken getToken(CsrfToken token) {
        System.out.println("token = " + token);
        return token;
    }

    //회원가입
    @PostMapping("")
    public ResUserResultDto join(@Valid @RequestBody UserInfoDto userInfoDto){
        System.out.println("userInfoDto = " + userInfoDto);
        Long result = userService.join(userInfoDto);
        return new ResUserResultDto(result,"회원가입 성공.");
    }

    //회원정보 조회
    @GetMapping("")
    public ResUserInfoDto check(@LoginUser UserInfo userInfo){
        return new ResUserInfoDto(userInfo);
    }

    //회원정보 수정
    @PutMapping
    public ResUserResultDto update(@LoginUser UserInfo userInfo, @Valid @RequestBody UserInfoDto userInfoDto){
        System.out.println("userInfo = " + userInfo.getName());
        System.out.println("userInfoDto = " + userInfoDto.getName());
        Long result = userService.update(userInfo,userInfoDto);
        return result == -1L ?
                new ResUserResultDto(result,"회원정보 변경 실패.") : new ResUserResultDto(result,"회원정보 변경 성공.");
    }


    //userid체크
    @GetMapping("/userid_check")
    public ResDupliCheckDto userIdCheck(@RequestParam("userid") String userid){
        return new ResDupliCheckDto(userService.userIdCheck(userid));
    }

    //email체크
    @GetMapping("/email_check")
    public ResDupliCheckDto emailCheck(@RequestParam("email") String email){
        return new ResDupliCheckDto(userService.userIdCheck(email));
    }
}
