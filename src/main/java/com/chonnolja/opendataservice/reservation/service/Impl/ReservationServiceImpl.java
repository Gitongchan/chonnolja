package com.chonnolja.opendataservice.reservation.service.Impl;

import com.chonnolja.opendataservice.activity.model.Activity;
import com.chonnolja.opendataservice.activity.repository.ActivityRepository;
import com.chonnolja.opendataservice.exception.CustomException;
import com.chonnolja.opendataservice.reservation.dto.request.ReservationDto;
import com.chonnolja.opendataservice.reservation.model.Reservation;
import com.chonnolja.opendataservice.reservation.model.ReservationStatus;
import com.chonnolja.opendataservice.reservation.repository.ReservationRepository;
import com.chonnolja.opendataservice.reservation.service.ReservationService;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.repository.UserRepository;
import com.chonnolja.opendataservice.village.repository.VillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    private final UserRepository userRepository;
    private final VillageRepository villageRepository;
    private final ReservationRepository reservationRepository;
    private final ActivityRepository activityRepository;

    //예약하기
    @Override
    public Long reservationSave(UserInfo userInfo, Long activityId, ReservationDto reservationDto) {
        UserInfo reservationUserInfo = userRepository.findById(userInfo.getId()).orElseThrow(
                ()->new CustomException.ResourceNotFoundException("로그인 사용자를 찾을 수 없습니다")
        );
        Activity reservationActivity = activityRepository.findByActivityId(activityId).orElseThrow(
                ()->new CustomException.ResourceNotFoundException("해당 활동을 찾을 수 없습니다")
        );

        Reservation reservation = reservationRepository.save(
                Reservation.builder()
                        .userInfo(reservationUserInfo)
                        .activity(reservationActivity)
                        .reservationStatus(ReservationStatus.READY.getStatus())
                        .reservationDate(LocalDateTime.now())
                        .reservationPrice(reservationDto.getReservationPrice())
                        .reservationQuantity(reservationDto.getReservationQuantity())
                        .build()
        );

        //제품 수량 변경
        if(reservationActivity.getActivityStock() < reservationDto.getReservationQuantity()){
            return -1L; // 재고 부족
        }else{
            activityRepository.updateProductStock(reservationDto.getReservationQuantity(),reservationActivity.getActivityId());
        }

        return reservation.getReservationId();
    }
}
