package com.viewpoint.service.impl;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.dataobject.ActivityOrder;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.repository.ActivityOrderRepository;
import com.viewpoint.repository.ActivityRepository;
import com.viewpoint.service.ActivityOrderService;
import com.viewpoint.service.ActivityService;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
        // 活动是不是过期
        Activity activity = activityService.findOne(activityOrder.getActivityId());
        if (LocalDateTime.now().isAfter(activity.getEndTime())){
            throw new ViewpointException(ResultEnum.TIME_IS_AFTEr);
        }
        // 活动是不是未开始
        if (LocalDateTime.now().isBefore(activity.getStartTime())) {
            throw new ViewpointException(ResultEnum.TIME_IS_BEFORE);
        }
        // 已申请
        ActivityOrder order = activityOrderRepository.findByBuyerOpenidAndActivityId(activityOrder.getBuyerOpenid(),
                activityOrder.getActivityId());
        if (StringUtils.isEmpty(order)) {
            throw new ViewpointException(ResultEnum.ACTIVITY_ORDER_IS_EXIST);
        }
        activityOrderRepository.save(activityOrder);
    }

    @Override
    public List<ActivityOrder> findByBuyerOpenid(String buyerOpenid) {
        List<ActivityOrder> activityOrderList = activityOrderRepository.findByBuyerOpenid(buyerOpenid);
        return activityOrderList;
    }
}
