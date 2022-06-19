package com.chonnolja.opendataservice.reservation.service;

import com.chonnolja.opendataservice.reservation.dto.request.ReservationDto;
import com.chonnolja.opendataservice.user.model.UserInfo;

public interface ReservationService {

    //주문하기
    Long reservationSave(UserInfo userInfo, Long villageId, ReservationDto reservationDto);

}
