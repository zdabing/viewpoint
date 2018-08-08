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
@DynamicInsert
@DynamicUpdate
public class ActivityOrder implements Serializable {

    private static final long serialVersionUID = 2307429457205668009L;

    @Id
    private String activityOrderId;

    private String activityId;

    private String buyerName;

    private String buyerPhone;

    /** 微信openID  */
    private String buyerOpenid;

    /** 活动订单状态(默认已报名) */
    private Integer orderStatus = 0;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
