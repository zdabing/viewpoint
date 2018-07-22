package com.viewpoint.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
@Data
public class Article {

    @Id
    private String articleId;

    /** 文章标题 */
    private String title;

    /** 文章logo */
    private String articleLogo;

    private String content;

    private Integer enabled;

    private Integer nodeId;

    private Integer sort;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
