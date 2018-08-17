package com.viewpoint.controller;

import com.viewpoint.dataobject.GoodsCategory;
import com.viewpoint.service.ArticleService;
import com.viewpoint.service.GoodsCategoryService;
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

    @Autowired
    private GoodsCategoryService goodsCategoryService;

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
       List<GoodsCategory> goodsCategoryList = goodsCategoryService.findUpAll();
       model.addAttribute("goodsCategoryList",goodsCategoryList);
        return "view/wwjs";
    }

    @GetMapping("/wwjs/detail/{goodsId}")
    public String wwjsDetail(Model model, @PathVariable("goodsId") String goodsId) {
        GoodsCategory goodsCategory = goodsCategoryService.findById(goodsId);
        model.addAttribute("goodsCategory",goodsCategory);
        return "view/wwjs-detail";
    }

    @GetMapping("/wmjs")
    public String wmjs(Model model) {
        return "view/wmjs";
    }
}
