package com.viewpoint.controller;

import com.viewpoint.dataobject.Article;
import com.viewpoint.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/detail/{nodeId}")
    public String detail(@PathVariable(value = "nodeId") Integer nodeId, Model model) {
        List<Article> articleList = articleService.findByNodeId(nodeId);
        model.addAttribute("articleList",articleList);
        return "";
    }
}
