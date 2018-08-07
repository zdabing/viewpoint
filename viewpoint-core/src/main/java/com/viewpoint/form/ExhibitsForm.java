package com.viewpoint.form;

import lombok.Data;

@Data
public class ExhibitsForm {

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

    /** 父节点iD */
    private String parentId;

    /** 景点iD */
    private String areasId;
}
