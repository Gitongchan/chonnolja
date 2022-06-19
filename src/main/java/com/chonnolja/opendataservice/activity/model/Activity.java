package com.chonnolja.opendataservice.activity.model;

import com.chonnolja.opendataservice.village.model.VillageInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@DynamicUpdate  //더티체킹 , 변경된 값만 확인한 이후 업데이트 쿼리 전송
public class Activity {

    //공통부분
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "activity_id")
    private Long activityId;

    //로그인 아이디
    @ManyToOne
    @JoinColumn(name="activity_villageInfo")
    private VillageInfo activityVillageInfo;

    //활동명
    @Column(name = "activity_name")
    private String activityName;

    //예약날짜
    @Column(name = "activity_date")
    private LocalDate activityDate;

    //예약가능 인원
    @Column(name = "activity_stock")
    private int activityStock;

    //가격
    @Column(name = "activity_price")
    private String activityPrice;

    @Builder
    public Activity(Long activityId, VillageInfo activityVillageInfo, String activityName,
                    LocalDate activityDate, int activityStock, String activityPrice) {
        this.activityId = activityId;
        this.activityVillageInfo = activityVillageInfo;
        this.activityName = activityName;
        this.activityDate = activityDate;
        this.activityStock = activityStock;
        this.activityPrice = activityPrice;
    }
}
