package com.chonnolja.opendataservice.village.model;


import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.village.dto.request.VillageStatus;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@DynamicUpdate
public class VillageInfo {

    /* 이미 등록된 데이터 */
    //사업자등록번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="village_id")
    private Long villageId;

    //유저 정보 외래키
    @OneToOne
    @JoinColumn(name="village_userInfo")
    private UserInfo userInfo;

    //마을명
    @Column(name = "village_name")
    private String villageName;

    //대표자 이름 (사업자가입시 체크할 때 사용됨)
    @Column(name = "village_rep_name")
    private String villageRepName;

    //대표 연락처
    @Column(name = "village_num")
    private String villageNum;

    //시도명
    @Column(name = "village_adr_main")
    private String villageAdrMain;

    //시군구명
    @Column(name = "village_adr_sub")
    private String villageAdrSub;

    //마을 도로명 주소
    @Column(name = "village_street_adr")
    private String villageStreetAdr;

    //위도
    @Column(name = "village_latitude")
    private String villageLatitude;

    //경도
    @Column(name = "village_longitude")
    private String villageLongitude;

    //공식홈페이지주소
    @Column(name = "village_url")
    private String villageUrl;

    //제공 기관 코드
    @Column(name = "village_provider_code")
    private String villageProviderCode;

    //제공기관명
    @Column(name = "village_provider_name")
    private String villageProviderName;

    //체험 활동 목록
    @Column(name = "village_activity")
    private String villageActivity;

    /* 매칭되어 새로 입력받는 데이터 */

    //마을 은행명
    @Column(name = "village_bank_name")
    private String villageBankName;

    //마을 계좌 번호
    @Column(name = "village_banknum")
    private String villageBankNum;

    //마을 상태 (회원가입 신청,정지,이용중,이용전 등등)
    @Enumerated(value = EnumType.STRING)
    @Column(name = "village_status")
    private VillageStatus villageStatus;

    //마을 사업자 탈퇴 날짜
    @Column(name = "village_deleted_date")
    private LocalDateTime villageDeletedDate;

    //체험마을 대표 사진
    @Column(name = "village_photo")
    private String villagePhoto;

    //체험마을소개
    @Column(name = "village_description")
    private String villageDescription;

    //체험마을안내사항
    @Column(name = "village_notify")
    private String villageNotify;

    //조회수
    @Column(name = "village_view_cnt")
    @ColumnDefault("0")
    private int villageViewCnt;

    @Builder
    public VillageInfo(Long villageId, UserInfo userInfo, String villageName, String villageRepName,
                       String villageNum, String villageAdrMain, String villageAdrSub,
                       String villageStreetAdr, String villageLatitude, String villageLongitude,
                       String villageUrl, String villageProviderCode, String villageProviderName,
                       String villageBankName,String villageBankNum, VillageStatus villageStatus, LocalDateTime villageDeletedDate,
                       String villageActivity, String villagePhoto, String villageDescription,
                       String villageNotify,int villageViewCnt) {
        this.villageId = villageId;
        this.userInfo = userInfo;
        this.villageName = villageName;
        this.villageRepName = villageRepName;
        this.villageNum = villageNum;
        this.villageAdrMain = villageAdrMain;
        this.villageAdrSub = villageAdrSub;
        this.villageStreetAdr = villageStreetAdr;
        this.villageLatitude = villageLatitude;
        this.villageLongitude = villageLongitude;
        this.villageUrl = villageUrl;
        this.villageProviderCode = villageProviderCode;
        this.villageProviderName = villageProviderName;
        this.villageBankName = villageBankName;
        this.villageBankNum = villageBankNum;
        this.villageStatus = villageStatus;
        this.villageDeletedDate = villageDeletedDate;
        this.villageActivity = villageActivity;
        this.villagePhoto = villagePhoto;
        this.villageDescription = villageDescription;
        this.villageNotify = villageNotify;
        this.villageViewCnt = villageViewCnt;
    }
}
