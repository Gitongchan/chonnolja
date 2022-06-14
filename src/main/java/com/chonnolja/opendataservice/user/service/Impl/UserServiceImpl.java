package com.chonnolja.opendataservice.user.service.Impl;

import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
import com.chonnolja.opendataservice.village.dto.request.VillageStatus;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import com.chonnolja.opendataservice.village.repository.VillageRepository;
import com.chonnolja.opendataservice.user.dto.request.UserAdapter;
import com.chonnolja.opendataservice.user.dto.request.UserInfoDto;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.repository.UserRepository;
import com.chonnolja.opendataservice.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    @Autowired private final UserRepository userRepository;
    @Autowired private final VillageRepository villageRepository;


    @Override
    public UserDetails loadUserByUsername(String userid) throws UsernameNotFoundException {
        UserInfo userInfo = userRepository.findByUserid(userid)
                .orElseThrow(() -> new UsernameNotFoundException((userid)));

        return new UserAdapter(userInfo);
    }

    //회원가입
    @Override
    public Long join(UserInfoDto userInfoDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = userInfoDto.getPassword();
        userInfoDto.setPassword(bCryptPasswordEncoder.encode(rawPassword));

        //중복 id,email 검증
        Integer idCheckResult = userIdCheck(userInfoDto.getUserid());
        Integer emailCheckResult = emailCheck(userInfoDto.getEmail());
        if(idCheckResult.equals(-1)||emailCheckResult.equals(-1)) {
            return -1L;
        }
        return userRepository.save(
                UserInfo.builder()
                        .userid(userInfoDto.getUserid())
                        .password(userInfoDto.getPassword())
                        .name(userInfoDto.getName())
                        .phone(userInfoDto.getPhone())
                        .email(userInfoDto.getEmail())
                        .role("ROLE_USER")
                        .userAdrNum(userInfoDto.getUserAdrNum())
                        .userLotAdr(userInfoDto.getUserLotAdr())
                        .userStreetAdr(userInfoDto.getUserStreetAdr())
                        .userDetailAdr(userInfoDto.getUserDetailAdr())
                        .userEnabled(true)
                        .build()
        ).getId();
    }

    //유저 아이디 중복 체크
    @Override
    public Integer userIdCheck(String userid) {
        //.isPresent , Optional객체가 있으면 true null이면 false 반환
        if (userRepository.findByUserid(userid).isPresent()) {
            return -1; //같은 userid있으면 -1반환
        }
        return 1;
    }

    //이메일 중복 체크
    @Override
    public Integer emailCheck(String email) {
        if (userRepository.findByEmail(email).isPresent()) {
            return -1; //같은 이메일 존재할 때
        }
        return 1; // 같은 이메일 없을 때
    }

    //회원 정보 수정
    @Transactional
    @Override
    public Long update(UserInfo userInfo, UserInfoDto userInfoDto) {
        //로그인 사용자 검증 이후 동작함
        if (userRepository.findById(userInfo.getId()).isPresent()) {

            UserInfo modifyUser = userRepository.findById(userInfo.getId()).get();

            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            String rawPassword = userInfoDto.getPassword();
            userInfoDto.setPassword(bCryptPasswordEncoder.encode(rawPassword));

            userRepository.save(
                    UserInfo.builder()
                            .id(modifyUser.getId()) //로그인 유저 키값을 받아옴
                            .userid(modifyUser.getUserid()) //그대로 유지
                            .password(userInfoDto.getPassword())
                            .name(userInfoDto.getName())
                            .phone(userInfoDto.getPhone())
                            .email(userInfoDto.getEmail())
                            .role(modifyUser.getRole())
                            .userAdrNum(userInfoDto.getUserAdrNum())
                            .userLotAdr(userInfoDto.getUserLotAdr())
                            .userStreetAdr(userInfoDto.getUserStreetAdr())
                            .userDetailAdr(userInfoDto.getUserDetailAdr())
                            .userEnabled(modifyUser.isUserEnabled())
                            .build()
            );
            return userInfo.getId();
        }
        return -1L;
    }

    

    //회원 탈퇴
    @Override
    @Transactional
    public Long userDeleted(UserInfo userInfo, UserInfoDto userInfoDto) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(!userInfo.getRole().equals("ROLE_USER")) return -2L;

        if (userRepository.findById(userInfo.getId()).isPresent()
                && bCryptPasswordEncoder.matches(userInfoDto.getPassword(),userInfo.getPassword())) {

            UserInfo DeleteUser = userRepository.findById(userInfo.getId()).get();

            userRepository.save(
                    UserInfo.builder()
                            .id(DeleteUser.getId()) //로그인 유저 키값을 받아옴
                            .userid(DeleteUser.getUserid()) //그대로 유지
                            .password(DeleteUser.getPassword())
                            .name(DeleteUser.getName())
                            .phone(DeleteUser.getPhone())
                            .email(DeleteUser.getEmail())
                            .role(DeleteUser.getRole())
                            .userAdrNum(DeleteUser.getUserAdrNum())
                            .userStreetAdr(DeleteUser.getUserStreetAdr())
                            .userLotAdr(DeleteUser.getUserLotAdr())
                            .userDetailAdr(DeleteUser.getUserDetailAdr())
                            .userDeletedDate(LocalDateTime.now()) //현재시간
                            .userEnabled(false)//사용자 이용 중지
                            .build()
            );
            return userInfo.getId();
        }
        return -1L;
    }

    //회원 정보 조회
    @Override
    public UserInfo userInfoCheck(UserInfo userInfo) {

        if(userRepository.findById(userInfo.getId()).isPresent()) {
            return  userRepository.findById(userInfo.getId()).get();
        }
        return null;
    }

    //사업자 등록
    //일반회원으로 등록된 사용자의 사업자 등록
    @Override
    public Long villageRegister(UserInfo userInfo,Long villageId,VillageInfoDto villageInfoDto) {

        if(userRepository.findById(userInfo.getId()).isEmpty()){
           return -1L;//로그인 정보 없을때
        }

        UserInfo registerUserInfo = userRepository.findById(userInfo.getId()).get();

        if(villageRepository.findByUserInfo(registerUserInfo).isPresent()){
            return -1L;//가입된 유저 정보 있으면 -1L
        }
        if(villageRepository.findByUserInfo(registerUserInfo).isPresent()){
            return -2L; //이미 등록된 사업자 일 때
        }
        //검색된 여러가지 정보중 선택된 한가지 마을정보의 아이값을 가져와서 사용한다
        if(villageRepository.findByVillageId(villageId).isEmpty()){
            return -1L;
        }
        VillageInfo registerVillInfo = villageRepository.findByVillageId(villageId).get();

        userRepository.save(
                    UserInfo.builder()
                            .id(registerUserInfo.getId())
                            .userid(registerUserInfo.getUserid())
                            .password(registerUserInfo.getPassword())
                            .name(registerUserInfo.getName())
                            .phone(registerUserInfo.getPhone())
                            .email(registerUserInfo.getEmail())
                            .role("ROLE_MANAGER")
                            .userAdrNum(registerUserInfo.getUserAdrNum())
                            .userStreetAdr(registerUserInfo.getUserStreetAdr())
                            .userLotAdr(registerUserInfo.getUserLotAdr())
                            .userDetailAdr(registerUserInfo.getUserDetailAdr())
                            .userEnabled(registerUserInfo.isUserEnabled())
                            .build()
            );
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(registerVillInfo.getVillageId())
                            .userInfo(registerUserInfo)
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
                            .villageBanknum(villageInfoDto.getVillageBanknum())
                            .villageStatus(VillageStatus.USE)//가입시 자동 설정
                            .villagePhoto(villageInfoDto.getVillagePhoto())
                            .villageDescription(villageInfoDto.getVillageDescription())
                            .villageNotify(villageInfoDto.getVillageNotify())
                            .build()
            );            return registerUserInfo.getId();
    }

    //탈퇴한 회원 복구
    @Override
    public Long villageRestore(UserInfo userInfo, UserInfoDto userInfoDto) {


        if (userRepository.findById(userInfo.getId()).isEmpty()) {
            return -1L; //사용자 정보 없음
        }

        UserInfo restoreUserInfo = userRepository.findById(userInfo.getId()).get();

        if (villageRepository.findByUserInfoAndVillageStatus(restoreUserInfo,VillageStatus.DELETE).isEmpty()) {
            return -1L; //로그인된 사용자가 사업자 등록이 안되어있거나 , delete 상태가 아님
        }

        VillageInfo restoreVillUserinfo = villageRepository
                .findByUserInfoAndVillageStatus(restoreUserInfo,VillageStatus.DELETE).get();

            userRepository.save(
                    UserInfo.builder()
                            .id(restoreUserInfo.getId())
                            .userid(restoreUserInfo.getUserid())
                            .password(restoreUserInfo.getPassword())
                            .name(restoreUserInfo.getName())
                            .phone(restoreUserInfo.getPhone())
                            .email(restoreUserInfo.getEmail())
                            .role("ROLE_MANAGER")
                            .userAdrNum(restoreUserInfo.getUserAdrNum())
                            .userStreetAdr(restoreUserInfo.getUserStreetAdr())
                            .userLotAdr(restoreUserInfo.getUserLotAdr())
                            .userDetailAdr(restoreUserInfo.getUserDetailAdr())
                            .userEnabled(restoreUserInfo.isUserEnabled())
                            .build()
            );
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(restoreVillUserinfo.getVillageId())
                            .userInfo(restoreVillUserinfo.getUserInfo())
                            .villageName(restoreVillUserinfo.getVillageName())
                            .villageRepName(restoreVillUserinfo.getVillageRepName())
                            .villageNum(restoreVillUserinfo.getVillageNum())
                            .villageAdrMain(restoreVillUserinfo.getVillageAdrMain())
                            .villageAdrSub(restoreVillUserinfo.getVillageAdrSub())
                            .villageStreetAdr(restoreVillUserinfo.getVillageStreetAdr())
                            .villageLatitude(restoreVillUserinfo.getVillageLatitude())
                            .villageLongitude(restoreVillUserinfo.getVillageLongitude())
                            .villageActivity(restoreVillUserinfo.getVillageActivity())
                            .villageProviderCode(restoreVillUserinfo.getVillageProviderCode())
                            .villageProviderName(restoreVillUserinfo.getVillageProviderName())
                            .villageUrl(restoreVillUserinfo.getVillageUrl())
                            .villageStatus(VillageStatus.USE)
                            .villageDeletedDate(null)
                            .build()
            );
            return restoreUserInfo.getId();
            
    }

    @Override
    public String useridFind(UserInfoDto userInfoDto) {
        if(userRepository.findByEmailAndPhone(userInfoDto.getEmail(),userInfoDto.getPhone()).isPresent()){
            UserInfo findUserInfo = userRepository.findByEmailAndPhone(userInfoDto.getEmail(),userInfoDto.getPhone()).get();
            return findUserInfo.getUserid();
        }
        return null;
    }
}
