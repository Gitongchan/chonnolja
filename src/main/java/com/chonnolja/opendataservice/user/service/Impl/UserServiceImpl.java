package com.chonnolja.opendataservice.user.service.Impl;

import com.chonnolja.opendataservice.village.dto.request.VillageInfoDto;
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

        //임시로 권한 USER로 지정
        userInfoDto.setRole("ROLE_USER");
        userInfoDto.setUserEnabled(true);

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
                        .role(userInfoDto.getRole())
                        .userAdrNum(userInfoDto.getUserAdrNum())
                        .userLotAdr(userInfoDto.getUserLotAdr())
                        .userStreetAdr(userInfoDto.getUserStreetAdr())
                        .userDetailAdr(userInfoDto.getUserDetailAdr())
                        .userEnabled(userInfoDto.isUserEnabled())
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

        userInfoDto.setUserEnabled(false);//사용자 이용 중지

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
                            .userEnabled(userInfoDto.isUserEnabled())
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
    public Long villageRegister(UserInfo userInfo, UserInfoDto userInfoDto, VillageInfoDto villageInfoDto) {

        if(villageRepository.findByUserInfo(userInfo).isPresent()){
            return -1L;//가입된 유저 정보 있으면 -1L
        }
        if(villageRepository.findByUserInfo(userInfo).isPresent()){
            return -2L; //이미 등록된 사업자 일 때
        }

        if(userRepository.findById(userInfo.getId()).isPresent()){

            UserInfo registerUserInfo = userRepository.findById(userInfo.getId()).get();

            userInfoDto.setRole("ROLE_MANAGER"); // 권한 MANAGER로 변경
            villageInfoDto.setVillageStatus("사용중"); // 사업자 서비스 이용 가능

            userRepository.save(
                    UserInfo.builder()
                            .id(registerUserInfo.getId())
                            .userid(registerUserInfo.getUserid())
                            .password(registerUserInfo.getPassword())
                            .name(registerUserInfo.getName())
                            .phone(registerUserInfo.getPhone())
                            .email(registerUserInfo.getEmail())
                            .role(userInfoDto.getRole())
                            .userAdrNum(registerUserInfo.getUserAdrNum())
                            .userStreetAdr(registerUserInfo.getUserStreetAdr())
                            .userLotAdr(registerUserInfo.getUserLotAdr())
                            .userDetailAdr(registerUserInfo.getUserDetailAdr())
                            .userEnabled(registerUserInfo.isUserEnabled())
                            .build()
            );
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(villageInfoDto.getVillageId())
                            .userInfo(registerUserInfo)
                            .villageName(villageInfoDto.getVillageName())
                            .villageNum(villageInfoDto.getVillageNum())
                            .villageBanknum(villageInfoDto.getVillageBanknum())
                            .villageStatus(villageInfoDto.getVillageStatus())
                            .build()
            );            return registerUserInfo.getId();

        }
        return -1L;
    }

    //탈퇴한 회원 복구
    @Override
    public Long villageRestore(UserInfo userInfo, UserInfoDto userInfoDto, VillageInfoDto villageInfoDto) {


        if (userRepository.findById(userInfo.getId()).isEmpty()) {
            return -1L; //사용자 정보 없음
        }

        UserInfo restoreUserInfo = userRepository.findById(userInfo.getId()).get();

        if (villageRepository.findByUserInfo(restoreUserInfo).isEmpty()) {
            return -1L; //로그인된 사용자가 사업자 등록이 안되어있음
        }

        VillageInfo restorevillUserinfo = villageRepository.findByUserInfo(restoreUserInfo).get();


        if (restorevillUserinfo.getVillageStatus().equals("탈퇴")) {
            userInfoDto.setRole("ROLE_MANAGER");
            villageInfoDto.setVillageStatus("사용중");
            villageInfoDto.setVillageDeletedDate(null);

            userRepository.save(
                    UserInfo.builder()
                            .id(restoreUserInfo.getId())
                            .userid(restoreUserInfo.getUserid())
                            .password(restoreUserInfo.getPassword())
                            .name(restoreUserInfo.getName())
                            .phone(restoreUserInfo.getPhone())
                            .email(restoreUserInfo.getEmail())
                            .role(userInfoDto.getRole())
                            .userAdrNum(restoreUserInfo.getUserAdrNum())
                            .userStreetAdr(restoreUserInfo.getUserStreetAdr())
                            .userLotAdr(restoreUserInfo.getUserLotAdr())
                            .userDetailAdr(restoreUserInfo.getUserDetailAdr())
                            .userEnabled(restoreUserInfo.isUserEnabled())
                            .build()
            );
            villageRepository.save(
                    VillageInfo.builder()
                            .villageId(restorevillUserinfo.getVillageId())
                            .userInfo(restoreUserInfo)
                            .villageName(restorevillUserinfo.getVillageName())
                            .villageNum(restorevillUserinfo.getVillageNum())
                            .villageBanknum(restorevillUserinfo.getVillageBanknum())
                            .villageStatus(villageInfoDto.getVillageStatus())
                            .villageDeletedDate(villageInfoDto.getVillageDeletedDate())
                            .build()
            );
            return restoreUserInfo.getId();

        }
        return -1L; //이미 사업자 이용중임
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
