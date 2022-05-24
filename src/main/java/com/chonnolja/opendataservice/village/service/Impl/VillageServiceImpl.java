package com.chonnolja.opendataservice.village.service.Impl;


import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.repository.UserRepository;
import com.chonnolja.opendataservice.user.service.UserService;
import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.village.dto.request.VillageUserInfoDto;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import com.chonnolja.opendataservice.village.repository.VillageRepository;
import com.chonnolja.opendataservice.village.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class VillageServiceImpl implements VillageService {
    @Autowired private final VillageRepository villageRepository;
    @Autowired private final UserRepository userRepository;
    @Autowired private final UserService userService;

    //업체명 중복 체크
    @Override
    public Integer villageNameCheck(String villageName) {
        if (villageRepository.findByvillageName(villageName).isPresent()) {
            return -1; //같은 이메일 존재할 때
        }
        return 1; // 같은 이메일 없을 때
    }
    
    //체험마을 사업자 등록 전 확인


    //사업자 회원 등록
    @Override
    public Long villageRegister(VillageUserInfoDto villageUserInfoDto) {

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = villageUserInfoDto.getPassword();
        villageUserInfoDto.setPassword(bCryptPasswordEncoder.encode(rawPassword));

        //가입시 자동 설정
        villageUserInfoDto.setRole("ROLE_MANAGER");
        villageUserInfoDto.setVillageEnabled(true);
        villageUserInfoDto.setUserEnabled(true);

            System.out.println("실행됨");
            UserInfo singUpUserInfo =
                    userRepository.save(
                            UserInfo.builder()
                                    .userid(villageUserInfoDto.getUserid())
                                    .password(villageUserInfoDto.getPassword())
                                    .name(villageUserInfoDto.getName())
                                    .phone(villageUserInfoDto.getPhone())
                                    .email(villageUserInfoDto.getEmail())
                                    .role(villageUserInfoDto.getRole())
                                    .userAdrNum(villageUserInfoDto.getUserAdrNum())
                                    .userStreetAdr(villageUserInfoDto.getUserStreetAdr())
                                    .userLotAdr(villageUserInfoDto.getUserLotAdr())
                                    .userDetailAdr(villageUserInfoDto.getUserDetailAdr())
                                    .userEnabled(villageUserInfoDto.isUserEnabled())
                                    .build()
                    );

            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(villageUserInfoDto.getVillageId())
                            .userInfo(singUpUserInfo)
                            .villageName(villageUserInfoDto.getVillageName())
                            .villageNum(villageUserInfoDto.getVillageNum())
                            .villageAdrNum(villageUserInfoDto.getVillageAdrNum())
                            .villageLotAdr(villageUserInfoDto.getVillageLotAdr())
                            .villageStreetAdr(villageUserInfoDto.getVillageStreetAdr())
                            .villageDetailAdr(villageUserInfoDto.getVillageDetailAdr())
                            .villageBanknum(villageUserInfoDto.getVillageBanknum())
                            .villageEnabled(villageUserInfoDto.isVillageEnabled())
                            .build()
            );
            return singUpUserInfo.getId();

    }

    //사업자 정보 수정
    @Override
    public Long villageUpdate(UserInfo userInfo, VillageInfoDto villageInfoDto) {
        if(villageRepository.findByUserInfo(userInfo).isPresent()){

            VillageInfo updateVillageInfo = villageRepository.findByUserInfo(userInfo).orElseGet(
                    ()-> VillageInfo.builder().build()
            );
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(updateVillageInfo.getVillageId())
                            .userInfo(updateVillageInfo.getUserInfo())
                            .villageName(villageInfoDto.getVillageName())
                            .villageNum(villageInfoDto.getVillageNum())
                            .villageAdrNum(villageInfoDto.getVillageAdrNum())
                            .villageLotAdr(villageInfoDto.getVillageLotAdr())
                            .villageStreetAdr(villageInfoDto.getVillageStreetAdr())
                            .villageDetailAdr(villageInfoDto.getVillageDetailAdr())
                            .villageBanknum(villageInfoDto.getVillageBanknum())
                            .villageEnabled(updateVillageInfo.isVillageEnabled())
                            .build()
            );
            return updateVillageInfo.getVillageId();
        }
        else return -1L;
    }

    //사업자 회사 정보 조회
    @Override
    public VillageInfo villageInfoCheck(UserInfo userInfo) {
        if(villageRepository.findByUserInfo(userInfo).isPresent()){
            return villageRepository.findByUserInfo(userInfo).get();
        }
        return null;
    }

    //사업자탈퇴
    @Override
    public Long villageDeleted(UserInfo userInfo, VillageUserInfoDto villageUserInfoDto) {

        if(userRepository.findById(userInfo.getId()).isPresent() && userInfo.getRole().equals("ROLE_MANAGER")) {

            UserInfo comUserInfo = userRepository.findById(userInfo.getId()).get();

            villageUserInfoDto.setVillageEnabled(false);
            villageUserInfoDto.setRole("ROLE_USER");

            userRepository.save(
                    UserInfo.builder()
                            .id(comUserInfo.getId()) //로그인 유저 키값을 받아옴
                            //유저의 정보는 그대로 유지
                            .userid(comUserInfo.getUserid())
                            .password(comUserInfo.getPassword())
                            .name(comUserInfo.getName())
                            .phone(comUserInfo.getPhone())
                            .email(comUserInfo.getEmail())
                            .userAdrNum(comUserInfo.getUserAdrNum())
                            .userLotAdr(comUserInfo.getUserLotAdr())
                            .userStreetAdr(comUserInfo.getUserStreetAdr())
                            .userDetailAdr(comUserInfo.getUserDetailAdr())
                            .userEnabled(comUserInfo.isUserEnabled())
                            .role(villageUserInfoDto.getRole())
                            .build()
            );

            VillageInfo deleteVillageInfo = villageRepository.findByUserInfo(userInfo).orElseGet(
                    ()-> VillageInfo.builder().build()
            );
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(deleteVillageInfo.getVillageId())
                            .userInfo(userInfo)
                            .villageName(deleteVillageInfo.getVillageName())
                            .villageNum(deleteVillageInfo.getVillageNum())
                            .villageAdrNum(deleteVillageInfo.getVillageAdrNum())
                            .villageLotAdr(deleteVillageInfo.getVillageLotAdr())
                            .villageStreetAdr(deleteVillageInfo.getVillageStreetAdr())
                            .villageDetailAdr(deleteVillageInfo.getVillageDetailAdr())
                            .villageBanknum(deleteVillageInfo.getVillageBanknum())
                            .villageEnabled(villageUserInfoDto.isVillageEnabled())
                            .villageDeletedDate(LocalDateTime.now())
                            .build()

            );
            return userInfo.getId();
        }
        return -1L;
    }
}
