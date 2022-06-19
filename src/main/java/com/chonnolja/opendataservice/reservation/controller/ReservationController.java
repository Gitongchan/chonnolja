package com.chonnolja.opendataservice.reservation.controller;

import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.reservation.dto.request.ReservationDto;
import com.chonnolja.opendataservice.reservation.service.ReservationService;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ReservationController {
    private final ReservationService reservationService;
    //주문하기
    @PostMapping("/api/user/reservation/{activityId}")
    public ResResultDto ordersSave(@LoginUser UserInfo userInfo, @PathVariable("activityId") Long activityId
            , @RequestBody ReservationDto reservationDto){
        Long result = reservationService.reservationSave(userInfo,activityId,reservationDto);
        return result == -1L ?
                new ResResultDto(result,"예약 가능인원이 초과했습니다.") : new ResResultDto(result,"성공적으로 예약되었습니다.");
    }
}
