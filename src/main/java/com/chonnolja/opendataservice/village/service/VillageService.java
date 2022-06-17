package com.chonnolja.opendataservice.village.service;


import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.village.dto.request.CheckVillageDto;
import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.village.dto.request.VillageUserInfoDto;
import com.chonnolja.opendataservice.village.dto.response.ResVillageInfoDto;
import com.chonnolja.opendataservice.village.dto.response.ResVillageInfoListDto;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VillageService {
    //사업자탈퇴
    Long villageDeleted(UserInfo userInfo);

    //업체명 체크
    Integer villageNameCheck(String villageName);

    //사업자 회원 등록
    Long villageRegister(Long villageRegisterId,VillageUserInfoDto villageUserInfoDto);

    //사업자 회사 정보 수정
    Long villageUpdate(UserInfo userInfo, VillageInfoDto villageInfoDto);

    //사업자 회사 정보 조회
    VillageInfo villageInfoCheck(UserInfo userInfo);
    
    //체험마을 사업자 등록 전 확인
    List<ResVillageInfoDto> villageRegisterCheck(CheckVillageDto checkVillageDto);

    //체험마을 리스트
    List<ResVillageInfoListDto> villageList(Pageable pageable,int page,String villageActivity,String villageName,String address,
                                            String sort,int size);



}
