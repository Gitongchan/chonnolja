package com.chonnolja.opendataservice.village.controller;

import com.chonnolja.opendataservice.user.dto.response.ResDupliCheckDto;
import com.chonnolja.opendataservice.user.service.UserService;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import com.chonnolja.opendataservice.village.dto.request.CheckVillageDto;
import com.chonnolja.opendataservice.village.dto.request.VillageUserInfoDto;
import com.chonnolja.opendataservice.village.dto.response.ResVillageInfoDto;
import com.chonnolja.opendataservice.village.dto.response.ResVillageInfoListDto;
import com.chonnolja.opendataservice.village.service.VillageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/village")
public class VillagePermitAllController {
    @Autowired
    private final VillageService villageService;
    @Autowired
    private final UserService userService;

    //마을 사업자 회원가입전 체험마을 확인
    @GetMapping("/check")
    public List<ResVillageInfoDto> villageRegisterCheck(@RequestParam String villageRepName, @RequestParam String villageNum,
                                                        @RequestParam String villageStreetAdr) {

        CheckVillageDto checkVillageDto = new CheckVillageDto(villageRepName, villageNum, villageStreetAdr);

        if (villageService.villageRegisterCheck(checkVillageDto) == null) {
            return null;
        }
        return villageService.villageRegisterCheck(checkVillageDto);
    }

    //마을명 중복 체크
    @GetMapping("/name_check")
    public ResDupliCheckDto villageNameCheck(@RequestParam("villageName") String villageName) {
        return new ResDupliCheckDto(villageService.villageNameCheck(villageName));
    }

    //마을 회원가입
    @PostMapping("/signup/{villageId}")
    public ResResultDto villageRegister(@PathVariable("villageId") Long villageId,
                                        @RequestBody VillageUserInfoDto villageUserInfoDto) {

        Integer idCheckResult = userService.userIdCheck(villageUserInfoDto.getUserid());
        Integer emailCheckResult = userService.emailCheck(villageUserInfoDto.getEmail());

        //중복 id,email 검증
        if (idCheckResult.equals(-1) || emailCheckResult.equals(-1)) {
            return new ResResultDto(-1L, "회원가입 실패, 아이디,이메일을 다시 확인하세요.");
        } else {
            Long result = villageService.villageRegister(villageId, villageUserInfoDto);
            return result > 0 ?
                    new ResResultDto(result, "사업자 등록 성공.") : new ResResultDto(result, "사업자 등록 실패.");
        }
    }

    //체험마을 리스트 (페이징)
    @GetMapping("/list/{sort}/{size}")
    public List<ResVillageInfoListDto> villageList(Pageable pageable,
                                                   int page,
                                                   @PathVariable("sort") String sort,@PathVariable("size") int size,
                                                   @RequestParam String villageActivity ,@RequestParam String villageName,
                                                   @RequestParam String address
                                                   ){
        return villageService.villageList(pageable, page, villageActivity, villageName, address, sort, size);
    }
}
