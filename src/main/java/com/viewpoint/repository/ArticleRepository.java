package com.viewpoint.repository;

import com.viewpoint.dataobject.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleReponsitory extends JpaRepository<Article,String> {


}
