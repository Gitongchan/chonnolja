package com.chonnolja.opendataservice.village.dto.response;


import com.chonnolja.opendataservice.village.model.VillageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResVillageInfoListDto {
    //사업자등록번호
    private Long villageId;

    //마을명
    private String villageName;

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

    //체험 활동 목록
    private String villageActivity;

    //체험마을 대표 사진
    private String villagePhoto;

    //체험마을 조회수
    private Integer villageViewCnt;

    private int totalPage;

    private long totalElement;

    public ResVillageInfoListDto(VillageInfo villageInfo) {
        this.villageId = villageInfo.getVillageId();
        this.villageName = villageInfo.getVillageName();
        this.villageAdrMain = villageInfo.getVillageAdrMain();
        this.villageAdrSub = villageInfo.getVillageAdrSub();
        this.villageStreetAdr = villageInfo.getVillageStreetAdr();
        this.villageLatitude = villageInfo.getVillageLatitude();
        this.villageLongitude = villageInfo.getVillageLongitude();
        this.villageActivity = villageInfo.getVillageActivity();
        this.villagePhoto = villageInfo.getVillagePhoto();
        this.villageViewCnt = villageInfo.getVillageViewCnt();
    }
}
