package com.viewpoint.service.impl;

import com.viewpoint.dataobject.ActivityOrder;
import com.viewpoint.repository.ActivityOrderRepository;
import com.viewpoint.service.ActivityOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityOrderServiceImpl implements ActivityOrderService {

    @Autowired
    private ActivityOrderRepository activityOrderRepository;

    @Override
    @Transactional
    public void save(ActivityOrder activityOrder) {
        activityOrderRepository.save(activityOrder);
    }
}
