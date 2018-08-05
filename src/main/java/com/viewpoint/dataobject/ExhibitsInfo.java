package com.viewpoint.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 展品详情
 */
@Entity
@Data
@DynamicInsert
@DynamicUpdate
public class ExhibitsInfo implements Serializable {

    private static final long serialVersionUID = 3696306577135245449L;

    @Id
    private String exhibitsId;

    /** 展品名称 */
    private String exhibitsName;

    /** 展品简介 */
    private String exhibitsDescription;

    /** 展品详情 */
    private String exhibitsContent;

    /** 展品图片 */
    private String exhibitsIcon;

    /** 展品语音介绍 */
    private String exhibitsMp3;

    /** 展品视频介绍 */
    private String exhibitsMp4;

    /** 展品二维码链接 */
    private String exhibitsLink;

    /** 展品状态 0下架 1上架 */
    private Integer exhibitsStatus = 0;

    /** 父节点iD */
    private String parentId;

    /** 景点ID */
    private String areasId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
