package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 展品详情
 */
@Entity
@Data
public class ExhibitsInfo {

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
    private Integer exhibitsStatus;

    /** 父节点iD */
    private String parentId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
