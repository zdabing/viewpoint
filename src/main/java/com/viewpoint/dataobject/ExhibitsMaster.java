package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 展品
 */
@Data
@Entity
public class ExhibitsMaster {

    @Id
    private String masterId;

    /** 二维码链接 */
    private String masterLink;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
