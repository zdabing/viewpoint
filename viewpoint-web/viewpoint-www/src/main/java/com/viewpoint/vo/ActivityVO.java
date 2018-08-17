package com.viewpoint.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class ActivityVO implements Serializable {

    private static final long serialVersionUID = 1453472870734225103L;

    private String activityId;

    /** 活动名称 */
    private String activityName;

    /** 活动标签*/
    private String activityTag;

    /** 活动默认logo */
    private String activityLogo;

    /** 活动描述 */
    private String activityDesc;

    /** 活动内容 */
    private String activityContent;

    /** 发布时间 */
    private LocalDateTime releaseTime;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /** 活动限制人数 */
    private Integer buyLimit = 1000;

    /** 热度 */
    private Integer hot = 0;

    /** 已参加人数 */
    private long orderCount;

    /** 活动状态 */
    private String activityStatus;

    /** 订单状态 */
    private String orderStatus;
}
