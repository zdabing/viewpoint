package com.viewpoint.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class HistoryPerson implements Serializable {

    private static final long serialVersionUID = 374456721275499152L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer personId;

    private String personName;

    //人物的头像链接
    private String personIcon;

    private String personDesc;

    //关联ExamLevel的levelId
    private String levelId;

    //有无上下架
    private Integer enabled;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
