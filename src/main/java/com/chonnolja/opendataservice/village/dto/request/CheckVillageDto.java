package com.chonnolja.opendataservice.village.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckVillageDto { //사업자 등록전 확인 할때 사용

    //대표자 이름 (사업자가입시 체크할 때 사용됨)
    private String villageRepName;

    //대표 연락처
    private String villageNum;

    //마을 도로명 주소
    private String villageStreetAdr;
}
