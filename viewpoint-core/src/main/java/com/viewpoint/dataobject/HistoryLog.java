package com.viewpoint.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class HistoryLog implements Serializable {

    private static final long serialVersionUID = 5051396015712138480L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer logId;

    private String openid;

    private String masterId;

    private LocalDateTime createTime;
}
