package com.viewpoint.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.viewpoint.dataobject.ExhibitsInfo;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class HistoryVO implements Serializable {

    private static final long serialVersionUID = -1063065296915296744L;

    private Integer logId;

    private String openid;

    private ExhibitsInfo exhibit;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
