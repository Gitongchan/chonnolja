package com.chonnolja.opendataservice.village.repository;


import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VillageRepository extends JpaRepository<VillageInfo,Integer> {

    Optional<VillageInfo> findByvillageName(String villageName);

    Optional<VillageInfo> findByUserInfo(UserInfo userInfo);

}