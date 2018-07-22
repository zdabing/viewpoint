package com.viewpoint.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ActivityForm {

    private String activityId;

    /** 活动名称 */
    @NotEmpty
    private String activityName;

    /** 活动标签*/
    private String activityTag;

    /** 活动默认logo */
    private String activityLogo;

    /** 活动描述 */
    private String activityDesc;

    /** 活动内容 */
    @NotEmpty
    private String activityContent;

    /** 开始时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "开始时间不能为空")
    private LocalDateTime startTime;

    /** 结束时间 */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "结束时间不能为空")
    private LocalDateTime endTime;

    /** 活动限制人数 */
    private Integer buyLimit = 1000;
}
