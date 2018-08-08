package com.viewpoint.repository;

import com.viewpoint.dataobject.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article,String> {
    List<Article> findByNodeIdAndEnabled(Integer nodeId, Integer enabled);
}
