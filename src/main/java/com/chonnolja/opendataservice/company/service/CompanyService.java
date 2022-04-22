package com.chonnolja.opendataservice.company.service;

import com.chonnolja.opendataservice.company.dto.request.CompanyUserInfoDto;
import com.chonnolja.opendataservice.user.model.UserInfo;

public interface CompanyService {
    //회원탈퇴
    Long companyDeleted(UserInfo userInfo, CompanyUserInfoDto companyUserInfoDto);
}
