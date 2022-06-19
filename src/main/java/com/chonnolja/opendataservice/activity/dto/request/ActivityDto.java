package com.chonnolja.opendataservice.activity.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityDto {
    //공통부분
    private Long activityId;

    //활동명
    private String activityName;

    //예약날짜
    private LocalDate activityDate;

    //예약가능 인원
    private String activityStock;

    //가격
    private String activityPrice;

}
