package com.viewpoint.dto;

import com.viewpoint.dataobject.ExhibitsInfo;
import lombok.Data;

import java.util.List;

@Data
public class ExhibitsDTO{

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

    /** 景点iD */
    private String areasId;

    /** 景点名称 */
    private String areasName;

    List<ExhibitsInfo> exhibitsItemList;

}
