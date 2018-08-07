package com.viewpoint.converter;

import com.viewpoint.dataobject.ArticleNodes;
import com.viewpoint.dto.NodeMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;


public class ArticleNode2NodeMaster {

    public static NodeMaster convert(ArticleNodes articleNodes){
        NodeMaster nodeMaster = new NodeMaster();
        BeanUtils.copyProperties(articleNodes,nodeMaster);
        return nodeMaster;
    }

    public static List<NodeMaster> convert(List<ArticleNodes> articleNodesList) {
        return articleNodesList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
