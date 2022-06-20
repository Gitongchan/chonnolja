package com.chonnolja.opendataservice.activity.controller;

import com.chonnolja.opendataservice.activity.dto.response.ResActivityDto;
import com.chonnolja.opendataservice.activity.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/activity")
public class ActivityPermitAllController {

    private final ActivityService activityService;

    @GetMapping("/list/{villageId}")
    public List<ResActivityDto> activityList(@PathVariable("villageId") Long villageId){
    return activityService.activityList(villageId);
    }

    //체험 활동 정보
    @GetMapping("/{activityId}")
    public ResActivityDto activityInfo(@PathVariable("activityId") Long activityId){
        return activityService.activityInfo(activityId);
    }
}
