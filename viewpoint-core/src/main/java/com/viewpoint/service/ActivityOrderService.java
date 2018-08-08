package com.viewpoint.service;

import com.viewpoint.dataobject.ActivityOrder;

import java.util.List;

public interface ActivityOrderService {
    /**
     * 生成订单
     * @param activityOrder
     */
    void save(ActivityOrder activityOrder);

    /**
     * 我的活动订单
     * @param buyerOpenid
     * @return
     */
    List<ActivityOrder> findByBuyerOpenid(String buyerOpenid);
}
