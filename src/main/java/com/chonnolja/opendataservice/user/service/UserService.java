package com.chonnolja.opendataservice.user.service;


import com.chonnolja.opendataservice.user.dto.request.UserJoinDto;
import com.chonnolja.opendataservice.user.dto.response.ResDupliCheckDto;
import com.chonnolja.opendataservice.user.dto.response.ResUserJoinDto;

public interface UserService {
    //회원가입
    ResUserJoinDto join(UserJoinDto userJoinDto);

    //아이디 중복 체크
    ResDupliCheckDto userIdCheck(String userid);
    //이메일 중복 체크
    ResDupliCheckDto emailCheck(String email);
}
