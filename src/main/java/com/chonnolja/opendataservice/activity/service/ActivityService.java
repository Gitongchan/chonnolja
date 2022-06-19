package com.chonnolja.opendataservice.activity.service;

import com.chonnolja.opendataservice.activity.dto.request.ActivityDto;
import com.chonnolja.opendataservice.activity.dto.response.ResActivityDto;
import com.chonnolja.opendataservice.user.model.UserInfo;

import java.util.List;

public interface ActivityService {
    
    //활동 등록
    Long activitySave(UserInfo userInfo, ActivityDto activityDto);
    //활동 삭제
    
    //활동 조회
    List<ResActivityDto> activityList(Long villageId);

}
