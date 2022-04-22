package com.chonnolja.opendataservice.company.controller;

import com.chonnolja.opendataservice.company.dto.request.CompanyUserInfoDto;
import com.chonnolja.opendataservice.company.service.CompanyService;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.user.model.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/manager")
public class CompanyController {
    @Autowired
    private final CompanyService companyService;

    //사업자 탈퇴
    @PutMapping("/company_deleted")
    public ResResultDto companyDeleted(@LoginUser UserInfo userInfo, @RequestBody CompanyUserInfoDto companyUserInfoDto){

        System.out.println("사업자 탈퇴 컨트롤러 실행됨");
        Long result = companyService.companyDeleted(userInfo,companyUserInfoDto);
        return result == -1L ?
                new ResResultDto(result,"사업자 탈퇴 실패.") : new ResResultDto(result,"사업자 탈퇴 성공.");
    }

    
}
