package com.chonnolja.opendataservice.village.service.Impl;


import com.chonnolja.opendataservice.exception.CustomException;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.repository.UserRepository;
import com.chonnolja.opendataservice.village.dto.request.CheckVillageDto;
import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.village.dto.request.VillageStatus;
import com.chonnolja.opendataservice.village.dto.request.VillageUserInfoDto;
import com.chonnolja.opendataservice.village.dto.response.ResVillageInfoDto;
import com.chonnolja.opendataservice.village.dto.response.ResVillageInfoListDto;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import com.chonnolja.opendataservice.village.repository.VillageRepository;
import com.chonnolja.opendataservice.village.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class VillageServiceImpl implements VillageService {
     private final VillageRepository villageRepository;
     private final UserRepository userRepository;


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

        VillageInfo registerVillInfo = villageRepository.findByVillageId(villageRegisterId).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("마을 정보를 찾을 수 없습니다.")
        );

        //저장 로직
            UserInfo singUpUserInfo =
                    userRepository.save(
                            UserInfo.builder()
                                    .userid(villageUserInfoDto.getUserid())
                                    .password(villageUserInfoDto.getPassword())
                                    .name(villageUserInfoDto.getName())
                                    .phone(villageUserInfoDto.getPhone())
                                    .email(villageUserInfoDto.getEmail())
                                    .role("ROLE_MANAGER")//가입시 자동 설정
                                    .userAdrNum(villageUserInfoDto.getUserAdrNum())
                                    .userStreetAdr(villageUserInfoDto.getUserStreetAdr())
                                    .userLotAdr(villageUserInfoDto.getUserLotAdr())
                                    .userDetailAdr(villageUserInfoDto.getUserDetailAdr())
                                    .userEnabled(true)//가입시 자동 설정
                                    .build()
                    );
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
                                    .villageBankNum(villageUserInfoDto.getVillageBanknum())
                                    .villageStatus(VillageStatus.USE)//가입시 자동 설정
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
            // 그외의 정보들은 문의를 통해서만 변경 가능하다
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(updateVillageInfo.getVillageId())
                            .userInfo(updateVillageInfo.getUserInfo())
                            .villageName(updateVillageInfo.getVillageName())
                            .villageRepName(updateVillageInfo.getVillageRepName())
                            .villageNum(updateVillageInfo.getVillageNum())
                            .villageAdrMain(updateVillageInfo.getVillageAdrMain())
                            .villageAdrSub(updateVillageInfo.getVillageAdrSub())
                            .villageStreetAdr(updateVillageInfo.getVillageStreetAdr())
                            .villageLatitude(updateVillageInfo.getVillageLatitude())
                            .villageLongitude(updateVillageInfo.getVillageLongitude())
                            .villageActivity(villageInfoDto.getVillageActivity())
                            .villageProviderCode(updateVillageInfo.getVillageProviderCode())
                            .villageProviderName(updateVillageInfo.getVillageProviderName())
                            .villageUrl(villageInfoDto.getVillageUrl())
                            .villageBankNum(villageInfoDto.getVillageBanknum())
                            .villageBankName(villageInfoDto.getVillageName())
                            .villageStatus(updateVillageInfo.getVillageStatus())
                            .villagePhoto(villageInfoDto.getVillagePhoto())
                            .villageDescription(villageInfoDto.getVillageDescription())
                            .villageNotify(villageInfoDto.getVillageNotify())
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
    public Long villageDeleted(UserInfo userInfo) {

        UserInfo villUserinfo = userRepository.findByIdAndRole(userInfo.getId(),"ROLE_MANAGER").orElseThrow(
                ()->new CustomException.ResourceNotFoundException("유저 정보를 찾을 수 없습니다.")
        );

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
                            .role("ROLE_USER")  //권한 변경
                            .build()
            );

        VillageInfo deleteVillageInfo = villageRepository.findByUserInfo(userInfo).orElseGet(
                    ()-> VillageInfo.builder().build()
        );
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(deleteVillageInfo.getVillageId())
                            .userInfo(deleteVillageInfo.getUserInfo())
                            .villageName(deleteVillageInfo.getVillageName())
                            .villageRepName(deleteVillageInfo.getVillageRepName())
                            .villageNum(deleteVillageInfo.getVillageNum())
                            .villageAdrMain(deleteVillageInfo.getVillageAdrMain())
                            .villageAdrSub(deleteVillageInfo.getVillageAdrSub())
                            .villageStreetAdr(deleteVillageInfo.getVillageStreetAdr())
                            .villageLatitude(deleteVillageInfo.getVillageLatitude())
                            .villageLongitude(deleteVillageInfo.getVillageLongitude())
                            .villageActivity(deleteVillageInfo.getVillageActivity())
                            .villageProviderCode(deleteVillageInfo.getVillageProviderCode())
                            .villageProviderName(deleteVillageInfo.getVillageProviderName())
                            .villageUrl(deleteVillageInfo.getVillageUrl())
                            .villageBankNum(null)
                            .villageBankName(null)
                            .villageStatus(VillageStatus.DELETE)
                            .villagePhoto(null)
                            .villageDescription(null)
                            .villageNotify(null)
                            .villageDeletedDate(LocalDateTime.now())  //탈퇴한 시간
                            .build()
            );
            return userInfo.getId();

    }
    
    //체험마을 리스트 (페이징)
    @Override
    public List<ResVillageInfoListDto> villageList(Pageable pageable, int page,String villageActivity ,String villageName, String address, String sort, int size) {
        pageable = PageRequest.of(page, size, Sort.by(sort).descending());

        Page<VillageInfo> villageInfoPage = villageRepository.findByVillageList(pageable,villageActivity,villageName,address);

        List<VillageInfo> villageInfoList = villageInfoPage.getContent();
        List<ResVillageInfoListDto> villageInfoDto = new ArrayList<>();

      villageInfoList.forEach(entity->{
          ResVillageInfoListDto listDto = new ResVillageInfoListDto();
          listDto.setVillageId(entity.getVillageId());
          listDto.setVillageName(entity.getVillageName());
          listDto.setVillageAdrMain(entity.getVillageAdrMain());
          listDto.setVillageAdrSub(entity.getVillageAdrSub());
          listDto.setVillageStreetAdr(entity.getVillageStreetAdr());
          listDto.setVillageLatitude(entity.getVillageLatitude());
          listDto.setVillageLongitude(entity.getVillageLongitude());
          listDto.setVillageActivity(entity.getVillageActivity());
          listDto.setVillagePhoto(entity.getVillagePhoto());
          listDto.setVillageViewCnt(entity.getVillageViewCnt());
          listDto.setTotalPage(villageInfoPage.getTotalPages());
          listDto.setTotalElement(villageInfoPage.getTotalElements());
          villageInfoDto.add(listDto);
      });
      return villageInfoDto;
    }

}
