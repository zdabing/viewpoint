package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class HistoryLog {

    @Id
    private Integer logId;

    private String openid;

    private String masterId;

    private LocalDateTime createTime;
}
