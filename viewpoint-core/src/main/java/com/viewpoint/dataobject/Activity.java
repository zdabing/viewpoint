package com.viewpoint.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class Activity implements Serializable {

    private static final long serialVersionUID = 374456721275499152L;
    @Id
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

    /** 发布 */
    private Integer enabled = 0;

    /** 活动状态 */
    private Integer activityStatus;

}
