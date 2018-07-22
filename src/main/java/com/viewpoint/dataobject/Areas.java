package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 展区
 */
@Entity
@Data
public class Areas {

    @Id
    private String areasId;

    private String areasDes;

    private String areasContent;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
