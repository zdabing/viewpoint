package com.viewpoint.service;

import com.viewpoint.dataobject.Article;
import com.viewpoint.vo.ResultVO;
import org.springframework.data.domain.Pageable;

public interface ArticleService {

    /**增加文章*/
    Article addArticle(Article article);
    /**删除文章*/
    void deleteArticle(String articleId);
    /**修改文章*/
    ResultVO updateArticle(Article article);
    /**通过ID查看文章*/
    Article findArticleById(String articleId);
    /**查询所有文章*/
    ResultVO findAllArticle(Pageable pageable);

    /**
     * 文章上下架
     * @param articleId
     * @param enabled
     * @return
     */
    Article updateSale(Integer articleId, Integer enabled);
}
