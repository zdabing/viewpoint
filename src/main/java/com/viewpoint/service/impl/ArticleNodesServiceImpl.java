package com.viewpoint.service.impl;

import com.viewpoint.converter.ArticleNode2NodeMaster;
import com.viewpoint.dataobject.ArticleNodes;
import com.viewpoint.dto.NodeMaster;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.form.ArticleNodesForm;
import com.viewpoint.repository.ArticleNodesRepository;
import com.viewpoint.service.ArticleNodesService;
import com.viewpoint.service.ArticleService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArticleNodesServiceImpl implements ArticleNodesService {

    @Autowired
    private ArticleNodesRepository articleNodesRepository;

    /** 增加文章节点 */
    @Override
    @Transactional
    public ArticleNodes save(ArticleNodes articleNodes) {
        if(articleNodes.getHasChildren() == 0){
            articleNodes.setParentId(0);
            return articleNodesRepository.save(articleNodes);
        }
        articleNodes.setHasChildren(0);
        return articleNodesRepository.save(articleNodes);
    }

//    /** 查询节点下的子节点，若没有子节点返回article*/
//    @Override
//    public ResultVO findNode(Integer nodeId) {
//        //获取节点的内容
//        ResultVO resultVO = this.findByNodeId(nodeId);
//        Optional<ArticleNodes> articleNodes = (Optional<ArticleNodes>)resultVO.getData();
//
//        //没有子节点了
//        if(articleNodes.get().getHasChildren() == 0){
//            ResultVO resultVo1 = articleService.findArticleById(nodeId.toString());
//            return resultVo1;
//        }else {
//            //还有子节点
//            List<ArticleNodes> articleNodesList = articleNodesRepository.findByParentId(articleNodes.get().getNodeId());
//            return ResultVOUtil.success(articleNodesList);
//        }
//    }

    @Override
    public List<ArticleNodes> findChildrenId(Integer parentId){
        List<ArticleNodes> articleNodesList = articleNodesRepository.findByParentId(parentId);
        return articleNodesList;
    }


    /**
     * 查询所有的父节点
     * @return
     */
    @Override
    public List<ArticleNodes> findAllByFatherId() {

        return articleNodesRepository.findByParentId(0);
    }

    @Override
    public List<ArticleNodes> findListArticleNodes() {
        List<ArticleNodes> nodes = new ArrayList<>();
        List<ArticleNodes> articleNodesList = this.findAllByFatherId();
        for (ArticleNodes articleNodes : articleNodesList) {
            if(articleNodes.getHasChildren() == 0){
                nodes.add(articleNodes);
            }else{
                nodes.add(articleNodes);
                List<ArticleNodes> articleChildrenNodesList = this.findChildrenId(articleNodes.getNodeId());
                for(ArticleNodes articleChildrenNodes : articleChildrenNodesList){
                    articleChildrenNodes.setNodeName( "&nbsp;&nbsp;&nbsp;&nbsp;|__"+articleChildrenNodes.getNodeName());
                    nodes.add(articleChildrenNodes);
                }
            }
        }
        return nodes;
    }

    /**
     * 节点是否发布
     * @param nodeId
     * @param enabled
     * @return
     */
    @Override
    @Transactional
    public ArticleNodes updateSale(Integer nodeId, Integer enabled) {
        Optional<ArticleNodes> articleNodesOptional = articleNodesRepository.findById(nodeId);
        ArticleNodes articleNodes = articleNodesOptional.orElse(null);
        if(articleNodes.getHasChildren() == 1 && enabled == 0){
            Integer parentId = articleNodes.getNodeId();
            List<ArticleNodes> articleNodesList = articleNodesRepository.findByParentId(parentId);
            for (ArticleNodes articleNodes1 : articleNodesList){
                articleNodes1.setEnabled(enabled);
                articleNodesRepository.save(articleNodes1);
            }
        }
        articleNodes.setEnabled(enabled);
        return articleNodesRepository.save(articleNodes);
    }

    /**
     * 删除操作
     * @param nodeId
     */
    @Override
    public void delete(Integer nodeId) {
        articleNodesRepository.deleteById(nodeId);
    }

    @Override
    public ArticleNodes findByNodeId(Integer nodeId) {
        Optional<ArticleNodes> articleNodesOptional = articleNodesRepository.findById(nodeId);
        return articleNodesOptional.orElse(null);
    }

    @Override
    public List<NodeMaster> findUpNodeMaster() {
        List<ArticleNodes> articleNodesList = articleNodesRepository.findByEnabled(StatusEnum.UP.getCode());
        List<NodeMaster> nodeMastersList = articleNodesList.stream().filter(e -> e.getParentId() == 0).map(e -> ArticleNode2NodeMaster.convert(e)).collect(Collectors.toList());
        /*for (NodeMaster nodeMaster : nodeMastersList){
            List<ArticleNodes> articleNodesList1 = new ArrayList<>();
            for (ArticleNodes articleNodes : articleNodesList) {
                if (articleNodes.getParentId() == nodeMaster.getNodeId()){
                    articleNodesList1.add(articleNodes);
                }
            }
            nodeMaster.setArticleNodesList(articleNodesList1);
        }*/
        nodeMastersList.forEach(e -> {
            e.setArticleNodesList(articleNodesList.stream().filter(a -> a.getParentId() == e.getNodeId()).collect(Collectors.toList()));
        });
        return nodeMastersList;
    }


}
