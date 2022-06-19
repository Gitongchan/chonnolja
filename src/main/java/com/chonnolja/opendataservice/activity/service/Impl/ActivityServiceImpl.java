package com.chonnolja.opendataservice.activity.service.Impl;

import com.chonnolja.opendataservice.activity.dto.request.ActivityDto;
import com.chonnolja.opendataservice.activity.dto.response.ResActivityDto;
import com.chonnolja.opendataservice.activity.model.Activity;
import com.chonnolja.opendataservice.activity.repository.ActivityRepository;
import com.chonnolja.opendataservice.activity.service.ActivityService;
import com.chonnolja.opendataservice.exception.CustomException;
import com.chonnolja.opendataservice.user.model.UserInfo;
import com.chonnolja.opendataservice.user.repository.UserRepository;
import com.chonnolja.opendataservice.village.model.VillageInfo;
import com.chonnolja.opendataservice.village.repository.VillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ActivityServiceImpl implements ActivityService {
    private final UserRepository userRepository;
    private final VillageRepository villageRepository;
    private final ActivityRepository activityRepository;

    //활동등록
    @Override
    public Long activitySave(UserInfo userInfo, ActivityDto activityDto) {
        UserInfo saveUserInfo = userRepository.findById(userInfo.getId()).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("사용자 정보를 찾을 수 없습니다.")
        );

        VillageInfo villageInfo = villageRepository.findByUserInfo(saveUserInfo).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("마을 정보를 찾을 수 없습니다.")
        );

        return activityRepository.save(
                Activity.builder()
                        .activityVillageInfo(villageInfo)
                        .activityName(activityDto.getActivityName())
                        .activityDate(activityDto.getActivityDate())
                        .activityPrice(activityDto.getActivityPrice())
                        .activityStock(activityDto.getActivityStock())
                        .build()
        ).getActivityId();
    }

    //마을별 활동 조회
    @Override
    public List<ResActivityDto> activityList(Long villageId) {

        VillageInfo villageInfoCheck = villageRepository.findByVillageId(villageId).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("마을 정보를 찾을 수 없습니다.")
        );

        List<Activity> activityList = activityRepository.findByActivityVillageInfo(villageInfoCheck);
        List<ResActivityDto> resActivityDto = new ArrayList<>();
        activityList.forEach(entity->{
            ResActivityDto listDto = new ResActivityDto();
            listDto.setActivityId(entity.getActivityId());
            listDto.setVillageId(entity.getActivityVillageInfo().getVillageId());
            listDto.setActivityDate(entity.getActivityDate());
            listDto.setActivityName(entity.getActivityName());
            listDto.setActivityPrice(entity.getActivityPrice());
            listDto.setActivityStock(entity.getActivityStock());
            resActivityDto.add(listDto);
        });

        return resActivityDto;
    }

    //활동 정보
    @Override
    public ResActivityDto activityInfo(Long activityId) {

        Activity activity = activityRepository.findByActivityId(activityId).orElseThrow(
                () -> new CustomException.ResourceNotFoundException("활동 정보를 찾을 수 없습니다")
        );
        return new ResActivityDto(activity);
    }

}
