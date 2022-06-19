package com.chonnolja.opendataservice.reservation.model;

import com.chonnolja.opendataservice.activity.model.Activity;
import com.chonnolja.opendataservice.user.model.UserInfo;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Getter
@DynamicUpdate
public class Reservation {

    //예약 아이디
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="reservation_id")
    private Long reservationId;

    //예약한 사용자 정보
    @ManyToOne
    @JoinColumn(name="reservation_userInfo")
    private UserInfo userInfo;

    //예약한 상품 정보
    @ManyToOne
    @JoinColumn(name="reservation_activity")
    private Activity activity;
    
    //예약 상태
    @Column(name="reservation_status")
    private String reservationStatus;
    
    //예약 날짜
    @Column(name="reservation_date")
    private LocalDateTime reservationDate;
    
    //예약 금액
    @Column(name = "reservation_price")
    private int reservationPrice;

    //예약 인원
    @Column(name="reservation_quantity")
    private int reservationQuantity;

    @Builder
    public Reservation(Long reservationId, UserInfo userInfo, Activity activity, String reservationStatus,
                       LocalDateTime reservationDate,  int reservationPrice, int reservationQuantity) {
        this.reservationId = reservationId;
        this.userInfo = userInfo;
        this.activity = activity;
        this.reservationStatus = reservationStatus;
        this.reservationDate = reservationDate;;
        this.reservationPrice = reservationPrice;
        this.reservationQuantity = reservationQuantity;
    }
}




