package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class ExhibitsInfo {

    @Id
    private String exhibitsId;

    private String exhibitsName;

    private String exhibitsDescription;

    private String exhibitsContent;

    private String exhibitsIcon;

    private Integer exhibitsStatus;

    private String masterId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
