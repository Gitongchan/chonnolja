package com.chonnolja.opendataservice.activity.repository;

import com.chonnolja.opendataservice.activity.model.Activity;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ActivityRepository extends JpaRepository<Activity,Integer> {

    List<Activity> findByActivityVillageInfo(VillageInfo villageInfo);

}
