package com.viewpoint.service.impl;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.repository.ActivityRepository;
import com.viewpoint.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ActivityServiceImpl implements ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    @Override
    public List<Activity> findUpAll() {
        List<Activity> activityList = activityRepository.findByEnabled(StatusEnum.UP.getCode());
        return activityList;
    }

    @Override
    public Page<Activity> findAll(Pageable pageable) {
        Page<Activity> activityPage = activityRepository.findAll(pageable);
        return activityPage;
    }

    @Override
    public Activity findOne(String activityId) {
        Optional<Activity> activity = activityRepository.findById(activityId);
        return activity.orElse(null);
    }

    @Override
    @Transactional
    public Activity save(Activity activity) {
        return activityRepository.save(activity);
    }

    @Override
    public Activity onSale(String activityId) {
        return null;
    }

    @Override
    @Transactional
    public Activity offSale(String activityId) {
        Activity activity = activityRepository.findById(activityId).orElse(null);
        if (activity == null){
            throw new ViewpointException(ResultEnum.ACTIVITY_NOT_EXIST);
        }
        activity.setEnabled(StatusEnum.UP.getCode());
        return activityRepository.save(activity);
    }

    @Override
    public void delete(String activityId) {
        activityRepository.deleteById(activityId);
    }
}
