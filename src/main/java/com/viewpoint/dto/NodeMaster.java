package com.viewpoint.dto;

import com.viewpoint.dataobject.ArticleNodes;
import lombok.Data;

import java.util.List;

@Data
public class NodeMaster {

    private Integer nodeId;

    /** 节点名称  */
    private String nodeName;

    List<ArticleNodes> articleNodesList;
}
