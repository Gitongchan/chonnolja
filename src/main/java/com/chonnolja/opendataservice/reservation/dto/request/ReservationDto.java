package com.chonnolja.opendataservice.reservation.dto.request;

import com.chonnolja.opendataservice.activity.model.Activity;
import com.chonnolja.opendataservice.user.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservationDto {

    private Long reservationId;


    private UserInfo userInfo;


    private Activity activity;


    private String reservationStatus;


    private LocalDateTime reservationDate;


    private int reservationPrice;


    private int reservationQuantity;
}
