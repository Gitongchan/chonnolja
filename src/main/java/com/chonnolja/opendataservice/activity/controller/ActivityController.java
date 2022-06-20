package com.chonnolja.opendataservice.activity.controller;

import com.chonnolja.opendataservice.activity.dto.request.ActivityDto;
import com.chonnolja.opendataservice.activity.service.ActivityService;
import com.chonnolja.opendataservice.annotation.LoginUser;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.util.responseDto.ResResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/manager/activity")
public class ActivityController {
    private final ActivityService activityService;

    //활동 등록
    @PostMapping("/save")
    public ResResultDto saveActivity(@LoginUser UserInfo userInfo,@RequestBody ActivityDto activityDto){
        Long result = activityService.activitySave(userInfo,activityDto);
        return new ResResultDto(result,"활동 등록을 완료했습니다.");
    }
    
    //활동 삭제



}
