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
import com.chonnolja.opendataservice.village.service.VillageFileService;
import com.chonnolja.opendataservice.village.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
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
     private final VillageFileService villageFileService;


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
    public Long villageRegister(Long villageRegisterId, VillageUserInfoDto villageUserInfoDto, MultipartFile thumb) {

        //비밀번호 암호화
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String rawPassword = villageUserInfoDto.getPassword();
        villageUserInfoDto.setPassword(bCryptPasswordEncoder.encode(rawPassword));

        VillageInfo registerVillInfo = villageRepository.findByVillageId(villageRegisterId).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("마을 정보를 찾을 수 없습니다.")
        );

        String thumbFilePath = null;
        //썸네일 저장
        if(!thumb.isEmpty()){
            thumbFilePath = villageFileService.thumbFile(thumb);
        }

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
                                    .villageBankName(villageUserInfoDto.getVillageBankName())
                                    .villageBankNum(villageUserInfoDto.getVillageBankNum())
                                    .villageStatus(VillageStatus.USE)//가입시 자동 설정
                                    .villagePhoto(thumbFilePath)
                                    .villageDescription(villageUserInfoDto.getVillageDescription())
                                    .villageNotify(villageUserInfoDto.getVillageNotify())
                                    .build()
                    );

            return singUpUserInfo.getId();

    }

    //사업자 정보 수정
    @Override
    public Long villageUpdate(UserInfo userInfo,Long villageId,VillageInfoDto villageInfoDto,String deletedThumb,MultipartFile thumb) {
            UserInfo updateUserInfo = userRepository.findById(userInfo.getId()).orElseThrow(
                    () -> new CustomException.ResourceNotFoundException("회원 정보를 찾을 수 없습니다.")
            );

            VillageInfo updateVillageInfo = villageRepository.findByUserInfo(updateUserInfo).orElseThrow(
                    ()-> new CustomException.ResourceNotFoundException("마을 정보를 찾을 수 없습니다.")
            );

            //썸네일이 삭제되었을때
            if(deletedThumb != null && thumb != null){
                File deletedThumbFile = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\static\\village_thumbNail\\" + deletedThumb);
                if(deletedThumbFile.delete()){
                    String updateThumb = villageFileService.thumbFile(thumb);
                    villageRepository.updateVillagePhoto(updateThumb,villageId);
                }
            }
             String thumbFilePath = null;

            //썸네일 저장
            if(!thumb.isEmpty()){
                thumbFilePath = villageFileService.thumbFile(thumb);
            }

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
                            .villageBankNum(villageInfoDto.getVillageBankNum())
                            .villageBankName(villageInfoDto.getVillageBankName())
                            .villageStatus(updateVillageInfo.getVillageStatus())
                            .villagePhoto(thumbFilePath)
                            .villageDescription(villageInfoDto.getVillageDescription())
                            .villageNotify(villageInfoDto.getVillageNotify())
                            .build()
            );
            return updateVillageInfo.getVillageId();
        }


    //사업자 회사 정보 조회
    @Override
    public ResVillageInfoDto villageInfoCheck(UserInfo userInfo) {
        UserInfo userInfoCheck = userRepository.findById(userInfo.getId()).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("회원 정보를 찾을 수 없습니다.")
        );
         VillageInfo villageInfo = villageRepository.findByUserInfo(userInfoCheck).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("마을 정보를 찾을 수 없습니다.")
        );

         return new ResVillageInfoDto(villageInfo);
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

    //개별 마을 조회
    @Override
    public ResVillageInfoDto villageInfo(Long villageId, HttpServletRequest request, HttpServletResponse response) {
        
        VillageInfo villageInfo = villageRepository.findByVillageId(villageId).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("마을 정보를 찾을 수 없습니다")
        );

        //조회수 증가, 쿠키로 중복 증가 방지
        //쿠키가 있으면 그 쿠키가 해당 게시글 쿠키인지 확인하고 아니라면 조회수 올리고 setvalue로 해당 게시글의 쿠키 값도 넣어줘야함
        Cookie oldCookie = null;
        Cookie[] cookies = request.getCookies();

        //기존에 쿠키를 가지고 있다면 해당 쿠키를 oldCookie에 담아줌
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("villageView")) {
                    oldCookie = cookie;
                }
            }
        }

        //oldCookie가 쿠키를 가지고 있으면 oldCookie의 value값에 현재 게시글의 id가 없다면 조회수 증가
        //그리고 현제 게시글 id를 쿠키에 다시 담아서 보냄
        //쿠키가 없다면 새로 생성 후 조회 수 증가
        if(oldCookie != null) {
            if(!oldCookie.getValue().contains("[" + villageId.toString() + "]")) {
                villageRepository.updateVillageView(villageId);
                oldCookie.setValue(oldCookie.getValue() + "[" + villageId + "]");
                oldCookie.setMaxAge(60 * 60);
                response.addCookie(oldCookie);
            }
        } else {
            Cookie postCookie = new Cookie("villageView", "[" + villageId + "]");
            villageRepository.updateVillageView(villageId);
            //쿠키 사용시간 1시간 설정
            postCookie.setMaxAge(60 * 60);
            System.out.println("쿠키 이름 : " + postCookie.getValue());
            response.addCookie(postCookie);
        }
        
        return new ResVillageInfoDto(villageInfo);
    }


}
