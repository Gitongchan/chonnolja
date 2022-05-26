package com.chonnolja.opendataservice.village.dto.request;


import com.chonnolja.opendataservice.user.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VillageInfoDto {

    //userinfo
    private UserInfo userInfo;

    //사업자등록번호
    private Long villageId;

    //마을명
    private String villageName;

    //대표자 이름 (사업자가입시 체크할 때 사용됨)
    private String villageRepName;

    //대표 연락처
    private String villageNum;

    //시도명
    private String villageAdrMain;

    //시군구명
    private String villageAdrSub;

    //마을 도로명 주소
    private String villageStreetAdr;

    //위도
    private String villageLatitude;

    //경도
    private String villageLongitude;

    //공식홈페이지주소
    private String villageUrl;

    //제공 기관 코드
    private String villageProviderCode;

    //제공기관명
    private String villageProviderName;

    //마을 계좌
    private String villageBanknum;

    //마을 상태
    private VillageStatus villageStatus;

    //마을 사업자 탈퇴 날짜
    private LocalDateTime villageDeletedDate;

    //체험 활동 목록
    private String villageActivity;

    //체험마을 대표 사진
    private String villagePhoto;

    //체험마을소개
    private String villageDescription;

    //체험마을안내사항
    private String villageNotify;

}
