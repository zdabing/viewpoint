package com.viewpoint.repository;

import com.viewpoint.dataobject.ArticleNodes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleNodesRepository extends JpaRepository<ArticleNodes,Integer> {
    List<ArticleNodes> findByEnabled(Integer enabled);
}
