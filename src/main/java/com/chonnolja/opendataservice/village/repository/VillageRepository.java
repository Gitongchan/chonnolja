package com.chonnolja.opendataservice.village.repository;


import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.village.dto.request.VillageStatus;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VillageRepository extends JpaRepository<VillageInfo,Integer> {

    Optional<VillageInfo> findByVillageName(String villageName);

    Optional<VillageInfo> findByUserInfo(UserInfo userInfo);

    Optional<VillageInfo> findByVillageId(Long villageId);

    Optional<VillageInfo> findByUserInfoAndVillageStatus(UserInfo userInfo, VillageStatus villageStatus);

    //외래키가 등록 되지 않은 마을만 검색한다
    List<VillageInfo> findByVillageRepNameAndVillageNumAndVillageStreetAdrAndUserInfo(
            String villageRepName, String villageNum, String villageStreetAdr, UserInfo userInfo
    );

}
