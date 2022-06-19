package com.chonnolja.opendataservice.activity.repository;

import com.chonnolja.opendataservice.activity.model.Activity;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface ActivityRepository extends JpaRepository<Activity,Integer> {

    List<Activity> findByActivityVillageInfo(VillageInfo villageInfo);

    Optional<Activity> findByActivityId(Long ActivityId);

    //주문 수량 반영
    @Transactional
    @Modifying
    @Query("update Activity a set a.activityStock = a.activityStock - :activityStock where a.activityId = :activityId")
    void updateProductStock(@Param("activityStock") int activityStock, @Param("activityId") Long activityId);

}
