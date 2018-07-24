package com.viewpoint.form;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ArticleNodesForm {

    private String nodeName;

    private Integer nodeId;

    private Integer parentId;

    /** 是否有子节点 */
    private Integer hasChildren;

    private Integer sort;

}
