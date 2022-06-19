package com.chonnolja.opendataservice.reservation.repository;

import com.chonnolja.opendataservice.reservation.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation,Integer> {

}
