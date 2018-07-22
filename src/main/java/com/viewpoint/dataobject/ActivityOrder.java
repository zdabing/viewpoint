package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class ActivityOrder {

    @Id
    private String activityOrderId;

    private String activityId;

    private String buyerName;

    private String buyerPhone;

    /** 微信openID  */
    private String buyerOpenid;

    /** 活动订单状态(默认已报名) */
    private String orderStatus;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
