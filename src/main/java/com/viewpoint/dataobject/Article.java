package com.viewpoint.dataobject;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Data
public class Article implements Serializable {

    private static final long serialVersionUID = -77218928824527733L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private String articleId;

    /** 文章标题 */
    private String title;

    /** 文章logo */
    private String articleLogo;

    private String content;

    private Integer enabled;

    private Integer nodeId;

    private Integer sort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
