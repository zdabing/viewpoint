package com.viewpoint.service.impl;

import com.viewpoint.converter.ArticleNode2NodeMaster;
import com.viewpoint.dataobject.ArticleNodes;
import com.viewpoint.dto.NodeMaster;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.repository.ArticleNodesRepository;
import com.viewpoint.service.ArticleNodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArticleNodeServiceImpl implements ArticleNodesService {

    @Autowired
    private ArticleNodesRepository articleNodesRepository;

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
