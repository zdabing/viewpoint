package com.viewpoint.controller;

import com.viewpoint.dto.NodeMaster;
import com.viewpoint.service.ArticleNodesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
public class BaseController {

    @Autowired
    private ArticleNodesService articleNodesService;

    @ModelAttribute
    public void addAttributes(Model model) {
        List<NodeMaster> nodeList = articleNodesService.findUpNodeMaster();
        model.addAttribute("nodeList",nodeList);
    }
}
