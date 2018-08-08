package com.viewpoint.service;

import com.viewpoint.dataobject.ActivityOrder;

public interface ActivityOrderService {
    /**
     * 生成订单
     * @param activityOrder
     */
    void save(ActivityOrder activityOrder);
}
