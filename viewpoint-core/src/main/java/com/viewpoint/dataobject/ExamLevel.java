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

/**
 * 科举等级
 */

@Entity
@Data
@DynamicUpdate
@DynamicInsert
public class ExamLevel implements Serializable {

    private static final long serialVersionUID = -77218928824527733L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer levelId;

    private String levelName;

    private String levelIcon;

    private Integer sort;

    private Integer enabled;

    //名次的描述
    private String levelDesc;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

}
