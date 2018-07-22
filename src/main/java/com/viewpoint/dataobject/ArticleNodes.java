package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 *
 */
@Data
@Entity
public class ArticleNodes {

    /** 节点ID */
    @Id
    private Integer nodeId;

    /** 父节点ID */
    private Integer parentId;

    /** 节点名称  */
    private String nodeName;

    /** 是否有子节点 */
    private Integer hasChildren;

    /** 发布 */
    private Integer enabled;

    /** 排序 */
    private Integer orderSort;

    /** 修改时间 */
    private LocalDateTime updateTime;
}
