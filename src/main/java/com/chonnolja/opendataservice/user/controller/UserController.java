package com.chonnolja.opendataservice.user.controller;

import com.chonnolja.opendataservice.user.dto.request.UserJoinDto;
import com.chonnolja.opendataservice.user.dto.response.ResDupliCheckDto;
import com.chonnolja.opendataservice.user.dto.response.ResUserJoinDto;
import com.chonnolja.opendataservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController{
    @Autowired private final UserService userService;

    //회원가입
    @PostMapping("")
    public ResUserJoinDto join(@Valid UserJoinDto userJoinDto){
        return userService.join(userJoinDto);
    }

    //userid체크
    @PostMapping("/userid_check")
    public ResDupliCheckDto userIdCheck(@RequestParam("userid") String userid){
        return userService.userIdCheck(userid);
    }

    //email체크
    @PostMapping("/email_check")
    public ResDupliCheckDto emailCheck(@RequestParam("email") String email){
        return userService.emailCheck(email);
    }
}
