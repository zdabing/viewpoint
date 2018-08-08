package com.viewpoint.service;

import com.viewpoint.dataobject.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {

    /**增加文章*/
    Article addArticle(Article article);
    /**删除文章*/
    void deleteArticle(String articleId);
    /**通过ID查看文章*/
    Article findArticleById(String articleId);
    /**查询所有文章*/
    Page findAllArticle(Pageable pageable);

    /**
     * 文章上下架
     * @param articleId
     * @param enabled
     * @return
     */
    Article updateSale(Integer articleId, Integer enabled);

    List<Article> findByNodeId(Integer nodeId);
}
