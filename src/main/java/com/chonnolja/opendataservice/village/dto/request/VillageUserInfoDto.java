package com.chonnolja.opendataservice.village.dto.request;


import com.chonnolja.opendataservice.user.model.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VillageUserInfoDto {
    //일반사용자 Dto
    private Long id;

    @NotBlank(message = "아이디는 필수 입력 값입니다.")
    private String userid;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{6,12}",
            message = "비밀번호는 영문자와 숫자, 특수기호가 적어도 1개 이상 포함된 6자~12자의 비밀번호여야 합니다.")
    private String password;

    @NotBlank(message = "사용자명은 필수 입력 값입니다.")
    @Size(max = 20, message = "이름의 최대 길이는 20자 입니다.")
    private String name;

    @NotBlank(message = "핸드폰번호는 필수 입력 값입니다.")
    @Pattern(regexp = "^[0-9]+$", message = "핸드폰번호는 숫자만 허용합니다.")
    private String phone;

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Email(message = "이메일 형식에 맞지 않습니다")
    private String email;

    //권한 ( user,manager,admin)
    private String role;

    //회원 우편번호
    private String userAdrNum;

    //회원 지번 주소
    private String userLotAdr;

    //회원 도로명 주소
    private String userStreetAdr;

    //회원 상세 주소
    private String userDetailAdr;

    //회원 탈퇴 여부
    private boolean userEnabled;

    //회원 탈퇴 날짜
    private LocalDateTime userDeletedDate;

    /*                              여기부터 마을정보                           */

    //userinfo
    private UserInfo userInfo;

    //사업자등록번호
    private Long villageId;

    //마을명
    private String villageName;

    //대표자 이름 (사업자가입시 체크할 때 사용됨)
    private String villageRepName;

    //대표 연락처
    private String villageNum;

    //시도명
    private String villageAdrMain;

    //시군구명
    private String villageAdrSub;

    //마을 도로명 주소
    private String villageStreetAdr;

    //위도
    private String villageLatitude;

    //경도
    private String villageLongitude;

    //공식홈페이지주소
    private String villageUrl;

    //제공 기관 코드
    private String villageProviderCode;

    //제공기관명
    private String villageProviderName;

    //마을 계좌
    private String villageBanknum;

    //마을 상태
    private VillageStatus villageStatus;

    //마을 사업자 탈퇴 날짜
    private LocalDateTime villageDeletedDate;

    //체험 활동 목록
    private String villageActivity;

    //체험마을 대표 사진
    private String villagePhoto;

    //체험마을소개
    private String villageDescription;

    //체험마을안내사항
    private String villageNotify;

}
