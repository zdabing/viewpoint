package com.viewpoint.service.impl;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.dataobject.ActivityOrder;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.repository.ActivityOrderRepository;
import com.viewpoint.service.ActivityOrderService;
import com.viewpoint.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityOrderServiceImpl implements ActivityOrderService {

    @Autowired
    private ActivityOrderRepository activityOrderRepository;

    @Autowired
    private ActivityService activityService;

    @Override
    @Transactional
    public void save(ActivityOrder activityOrder) {
        Activity activity = activityService.findOne(activityOrder.getActivityId());
        // 活动是否存在
        if (activity == null || activity.getEnabled() == StatusEnum.DOWN.getCode()) {
            throw new ViewpointException(ResultEnum.ACTIVITY_NOT_EXIST);
        }
        // 活动是否开始
        if (LocalDateTime.now().isBefore(activity.getStartTime())) {
            throw new ViewpointException(ResultEnum.ACTIVITY_NOT_STAR);
        }
        // 活动是否结束
        if (LocalDateTime.now().isAfter(activity.getEndTime())) {
            throw new ViewpointException(ResultEnum.ACTIVITY_IS_END);
        }
        activityOrderRepository.save(activityOrder);
    }

    @Override
    public List<ActivityOrder> findByBuyerOpenid(String buyerOpenid) {
        List<ActivityOrder> activityOrderList = activityOrderRepository.findByBuyerOpenid(buyerOpenid);
        return activityOrderList;
    }

    @Override
    public long countByActivityId(String activityId) {

        return activityOrderRepository.countByActivityId(activityId);
    }
}
