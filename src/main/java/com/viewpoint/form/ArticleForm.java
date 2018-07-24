package com.viewpoint.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ArticleForm {
    /**
     * 文章Id
     */

    private Integer articleId;

    @NotEmpty
    private String title;

    private String  articleLogo;

    private Integer nodeId;

    @NotEmpty
    private String content;

    private Integer sort = 0;
}
