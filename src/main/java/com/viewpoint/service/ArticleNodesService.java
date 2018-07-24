package com.viewpoint.service;

import com.viewpoint.dataobject.Article;
import com.viewpoint.dataobject.ArticleNodes;
import com.viewpoint.dto.NodeMaster;
import com.viewpoint.form.ArticleNodesForm;
import com.viewpoint.vo.ResultVO;

import java.util.List;

public interface ArticleNodesService {

    /**
     * 增加文章节点
     */
    ArticleNodes save(ArticleNodes articleNodes);

    /** 通过nodeId查询当前节点 */
    ArticleNodes findByNodeId(Integer nodeId);


    List<ArticleNodes> findChildrenId(Integer nodeId);

    List<NodeMaster> findUpNodeMaster();

    List<ArticleNodes> findAllByFatherId();

    /**
     * 查询展示在列表中的数据并展示
     * @param
     * @return
     */
    List<ArticleNodes> findListArticleNodes();

    /**
     * 产品上下架
     * @param nodeId
     * @param enabled
     * @return
     */
    ArticleNodes updateSale(Integer nodeId, Integer enabled);

    /**
     * 删除产品
     * @param nodeId
     */
    void delete(Integer nodeId);
}