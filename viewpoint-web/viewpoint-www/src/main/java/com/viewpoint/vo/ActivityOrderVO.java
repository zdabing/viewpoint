package com.viewpoint.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 〈一句话功能简述〉<br>
 *
 * @author Surging
 * @create 2018/9/4
 * @since 1.0.0
 */
@Data
public class ActivityOrderVO implements Serializable {

    private static final long serialVersionUID = 2309854481153490553L;

    private String orderCode;

    private String activityCode;

    private String activityName;

    private String orderStatus;

}