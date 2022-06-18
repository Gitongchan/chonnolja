package com.chonnolja.opendataservice.village.repository;


import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.village.dto.request.VillageStatus;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

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
    
    //마을 리스트 검색
    @Query("Select v from VillageInfo v where v.villageActivity like %:villageActivity% " +
            "and v.villageName like %:villageName% " +
            "and v.villageStreetAdr like %:villageStreetAdr%")
    Page<VillageInfo> findByVillageList(Pageable pageable, @Param("villageActivity")String villageActivity,
                                        @Param("villageName")String villageName, @Param("villageStreetAdr")String villageStreetAdr);

    //조회수 증가
    @Transactional
    @Modifying
    @Query("update VillageInfo v set v.villageViewCnt = v.villageViewCnt + 1 where v.villageId =:villageId")
    void updateVillageView(@Param("villageId") Long villageId);
}
