package com.viewpoint.controller;

import com.viewpoint.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private ArticleService articleService;

    @GetMapping("/zfgh")
    public String zfgh(Model model) {
        return "view/zfgh";
    }

    @GetMapping("/nffz")
    public String nffz(Model model) {
        return "view/nffz";
    }

    @GetMapping("/wwjs")
    public String wwjs(Model model) {
        return "view/wwjs";
    }

    @GetMapping("/wwjs/detail/{}")
    public String wwjsDetail(Model model) {
        return "view/wwjs-detail";
    }

    @GetMapping("/wmjs")
    public String wmjs(Model model) {
        return "view/wmjs";
    }
}
