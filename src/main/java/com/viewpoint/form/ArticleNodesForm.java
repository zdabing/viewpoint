package com.viewpoint.form;

import lombok.Data;

@Data
public class ArticleNodesForm {

    private String nodeName;

    private Integer nodeId;

    private Integer parentId;

    /** 是否有子节点 */
    private Integer hasChildren;

    private Integer sort;


}
