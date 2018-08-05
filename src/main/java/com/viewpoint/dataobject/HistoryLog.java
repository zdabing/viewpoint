package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class HistoryLog implements Serializable {

    private static final long serialVersionUID = 5051396015712138480L;
    @Id
    private Integer logId;

    private String openid;

    private String masterId;

    private LocalDateTime createTime;
}
