package com.viewpoint.service.impl;

import com.viewpoint.dto.NodeMaster;
import com.viewpoint.service.ArticleNodesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ArticleNodeServiceImplTest {

    @Autowired
    private ArticleNodesService articleNodesService;

    @Test
    public void findUpNodeMaster() {
        List<NodeMaster> nodeMasterList = articleNodesService.findUpNodeMaster();
        System.out.println(nodeMasterList);
    }
}