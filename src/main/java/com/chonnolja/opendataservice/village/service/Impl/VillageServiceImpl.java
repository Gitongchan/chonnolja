package com.chonnolja.opendataservice.village.service.Impl;


import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.repository.UserRepository;
import com.chonnolja.opendataservice.user.service.UserService;
import com.chonnolja.opendataservice.village.dto.reponse.ResVillageInfoDto;
import com.chonnolja.opendataservice.village.dto.request.CheckVillageDto;
import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.village.dto.request.VillageStatus;
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
import java.util.List;
import java.util.stream.Collectors;

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
        if (villageRepository.findByVillageName(villageName).isPresent()) {
            return -1; //같은 이메일 존재할 때
        }
        return 1; // 같은 이메일 없을 때
    }
    
    //체험마을 사업자 등록 전 확인
    @Override
    public List<ResVillageInfoDto> villageRegisterCheck(CheckVillageDto checkVillageDto){
        if(villageRepository.findByVillageRepNameAndVillageNumAndVillageStreetAdrAndUserInfo(
                checkVillageDto.getVillageRepName(),checkVillageDto.getVillageNum(),checkVillageDto.getVillageStreetAdr(),null
            ).isEmpty()
        ){
            return null;
        }
        return villageRepository.findByVillageRepNameAndVillageNumAndVillageStreetAdrAndUserInfo
                        (checkVillageDto.getVillageRepName(),checkVillageDto.getVillageNum(),checkVillageDto.getVillageStreetAdr(),null)
                .stream()   //stream을 통해 List를 스트림으로
                .map(ResVillageInfoDto::new)  //map()은 원소를 가공 .map()을 이용해 원하는 객체에 다시 담아준다
                .collect(Collectors.toList()); //Collectors.toList 로 map을 list로 변환후 리스트로 리턴
    }


    //사업자 회원 등록
    @Override
    public Long villageRegister(Long villageRegisterId,VillageUserInfoDto villageUserInfoDto) {

        //비밀번호 암호화
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = villageUserInfoDto.getPassword();
        villageUserInfoDto.setPassword(bCryptPasswordEncoder.encode(rawPassword));

        //가입시 자동 설정
        villageUserInfoDto.setRole("ROLE_MANAGER");
        villageUserInfoDto.setUserEnabled(true);

        //검색된 여러가지 정보중 선택된 한가지 마을정보의 아이값을 가져와서 사용한다
        if(villageRepository.findByVillageId(villageRegisterId).isEmpty()){
           return -1L;
        }

        VillageInfo registerVillInfo = villageRepository.findByVillageId(villageRegisterId).get();

        //저장 로직
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
                        
                    villageUserInfoDto.setVillageStatus(VillageStatus.USE);
                
                    villageRepository.save(
                            VillageInfo.builder()
                                    .villageId(registerVillInfo.getVillageId())
                                    .userInfo(singUpUserInfo)
                                    .villageName(registerVillInfo.getVillageName())
                                    .villageRepName(registerVillInfo.getVillageRepName())
                                    .villageNum(registerVillInfo.getVillageNum())
                                    .villageAdrMain(registerVillInfo.getVillageAdrMain())
                                    .villageAdrSub(registerVillInfo.getVillageAdrSub())
                                    .villageStreetAdr(registerVillInfo.getVillageStreetAdr())
                                    .villageLatitude(registerVillInfo.getVillageLatitude())
                                    .villageLongitude(registerVillInfo.getVillageLongitude())
                                    .villageActivity(registerVillInfo.getVillageActivity())
                                    .villageProviderCode(registerVillInfo.getVillageProviderCode())
                                    .villageProviderName(registerVillInfo.getVillageProviderName())
                                    .villageUrl(registerVillInfo.getVillageUrl())
                                    .villageBanknum(villageUserInfoDto.getVillageBanknum())
                                    .villageStatus(villageUserInfoDto.getVillageStatus())
                                    .villagePhoto(villageUserInfoDto.getVillagePhoto())
                                    .villageDescription(villageUserInfoDto.getVillageDescription())
                                    .villageNotify(villageUserInfoDto.getVillageNotify())
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

            UserInfo villUserinfo = userRepository.findById(userInfo.getId()).get();


            villageUserInfoDto.setRole("ROLE_USER");

            userRepository.save(
                    UserInfo.builder()
                            .id(villUserinfo.getId()) //로그인 유저 키값을 받아옴
                            //유저의 정보는 그대로 유지
                            .userid(villUserinfo.getUserid())
                            .password(villUserinfo.getPassword())
                            .name(villUserinfo.getName())
                            .phone(villUserinfo.getPhone())
                            .email(villUserinfo.getEmail())
                            .userAdrNum(villUserinfo.getUserAdrNum())
                            .userLotAdr(villUserinfo.getUserLotAdr())
                            .userStreetAdr(villUserinfo.getUserStreetAdr())
                            .userDetailAdr(villUserinfo.getUserDetailAdr())
                            .userEnabled(villUserinfo.isUserEnabled())
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
                            .villageBanknum(deleteVillageInfo.getVillageBanknum())
                            .villageDeletedDate(LocalDateTime.now())
                            .build()

            );
            return userInfo.getId();
        }
        return -1L;
    }
}
