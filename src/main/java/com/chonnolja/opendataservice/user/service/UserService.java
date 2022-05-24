package com.chonnolja.opendataservice.user.service;

import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.user.dto.request.UserInfoDto;
import com.chonnolja.opendataservice.user.model.UserInfo;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    //회원가입
    Long join(UserInfoDto userInfoDto);

    //회원정보 수정
    Long update(UserInfo userInfo , UserInfoDto userInfoDto);

    //아이디 중복 체크
    Integer userIdCheck(String userid);

    //이메일 중복 체크
    Integer emailCheck(String email);
    
    //회원 탈퇴
    Long userDeleted(UserInfo userInfo,UserInfoDto userInfoDto);

    //회원 정보 조회
    UserInfo userInfoCheck(UserInfo userInfo);

    //사업자 등록 (회원으로 등록된 사용자의 사업자 등록)
    Long villageRegister(UserInfo userInfo,UserInfoDto userInfoDto, VillageInfoDto villageInfoDto);

    //사업자 복구
    Long villageRestore(UserInfo userInfo, UserInfoDto userInfoDto, VillageInfoDto villageInfoDto);

    //아이디 찾기
    String useridFind(UserInfoDto userInfoDto);

}
