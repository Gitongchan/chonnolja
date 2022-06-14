package com.chonnolja.opendataservice.user.repository;

import com.chonnolja.opendataservice.user.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserInfo,Integer> {
    Optional<UserInfo> findByUserid(String userid);

    Optional<UserInfo> findByEmail(String email);

    Optional<UserInfo> findById(Long id);

    Optional<UserInfo> findByIdAndRole(Long id,String role);

    Optional<UserInfo> findByEmailAndPhone(String email, String phone);

}
