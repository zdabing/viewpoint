package com.viewpoint.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 展区
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class Areas implements Serializable {

    private static final long serialVersionUID = 6232534241897000757L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer areasId;

  //  private String areasDes;

    private String areasContent;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    private String areasLeft;

    private String areasTop;

  //  private String areasLogo;

    //音频
    private String areasAudio;

    //视频
    private String areasVideo;

    private String areasName;

}
