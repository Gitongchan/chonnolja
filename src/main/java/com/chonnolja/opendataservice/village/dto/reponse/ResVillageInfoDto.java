package com.chonnolja.opendataservice.village.dto.reponse;


import com.chonnolja.opendataservice.village.model.VillageInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResVillageInfoDto {
    //회원명
    private String username;

    //사업자등록번호
    private Long villageId;

    //회사명
    private String villageName;

    //회사연락처
    private String villageNum;

    //회사 우편번호
    private String villageAdrNum;

    //회원 지번 주소
    private String villageLotAdr;

    //회원 도로명 주소
    private String villageStreetAdr;

    //회사 상세주소
    private String villageDetailAdr;

    //회사 계좌
    private String villageBanknum;



    public ResVillageInfoDto(VillageInfo villageInfo) {
        this.username = villageInfo.getUserInfo().getName();
        this.villageId = villageInfo.getVillageId();
        this.villageName = villageInfo.getVillageName();
        this.villageNum = villageInfo.getVillageNum();
        this.villageAdrNum = villageInfo.getVillageAdrNum();
        this.villageLotAdr = villageInfo.getVillageLotAdr();
        this.villageStreetAdr = villageInfo.getVillageStreetAdr();
        this.villageDetailAdr = villageInfo.getVillageDetailAdr();
        this.villageBanknum = villageInfo.getVillageBanknum();
    }


}
