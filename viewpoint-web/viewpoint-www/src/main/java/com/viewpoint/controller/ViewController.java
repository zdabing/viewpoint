package com.viewpoint.controller;

import com.viewpoint.dataobject.ExamLevel;
import com.viewpoint.dataobject.GoodsCategory;
import com.viewpoint.dataobject.HistoryPerson;
import com.viewpoint.dataobject.View;
import com.viewpoint.service.ExamLevelService;
import com.viewpoint.service.GoodsCategoryService;
import com.viewpoint.service.HistoryPersonService;
import com.viewpoint.service.WenMiaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/view")
public class ViewController {

    @Autowired
    private WenMiaoService wenMiaoService;

    @Autowired
    private GoodsCategoryService goodsCategoryService;

    @Autowired
    private HistoryPersonService historyPersonService;

    @Autowired
    private ExamLevelService examLevelService;

    @GetMapping("/zfgh")
    public String zfgh(Model model) {
        View view = wenMiaoService.getById("zfgh".toUpperCase());
        model.addAttribute("view",view);
        return "view/zfgh";
    }

    @GetMapping("/nffz")
    public String nffz(Model model) {
        View view = wenMiaoService.getById("nffz".toUpperCase());
        model.addAttribute("view",view);
        return "view/nffz";
    }

    @GetMapping("/wwjs")
    public String wwjs(Model model) {
        View view = wenMiaoService.getById("wwjs".toUpperCase());
        model.addAttribute("view",view);
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
        View view = wenMiaoService.getById("wmjs".toUpperCase());
        model.addAttribute("view",view);
        return "view/wmjs";
    }

    @GetMapping("/kjwh")
    public String kjwh(Model model){
        View view = wenMiaoService.getById("kjwh".toUpperCase());
        model.addAttribute("view",view);
        List<ExamLevel> examLevelList = examLevelService.findUpExamleveList();
        List<ExamLevel> upList = examLevelList.stream().limit(4).collect(Collectors.toList());
        model.addAttribute("upList",upList);
        List<ExamLevel> downList = examLevelList.stream().skip(4).collect(Collectors.toList());
        model.addAttribute("downList",downList);
        return "view/kjwh";
    }

    @GetMapping("/kjwh/detail/{levelId}")
    public String kjwhDetail(Model model, @PathVariable(value = "levelId") String levelId){
        ExamLevel examLevel = examLevelService.findById(Integer.valueOf(levelId));
        model.addAttribute("examLevel",examLevel);
        List<HistoryPerson> historyPersonList = historyPersonService.findUpHistoryPersonList(levelId);
        model.addAttribute("historyPersonList",historyPersonList);
        return "view/kjwh-detail";
    }
}
