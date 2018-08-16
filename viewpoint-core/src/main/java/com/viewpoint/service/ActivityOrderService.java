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
     * 微信ID查找活动订单
     * @param buyerOpenid
     * @return
     */
    List<ActivityOrder> findByBuyerOpenid(String buyerOpenid);

    /**
     * 活动ID查找参加人数
     * @param activityId
     * @return
     */
    long countByActivityId(String activityId);

    /**
     * 是否已申请
     * @param buyerOpenid
     * @param activityId
     * @return false : 未申请
     *         true : 已申请
     */
    boolean isRepeated(String buyerOpenid, String activityId);
}
