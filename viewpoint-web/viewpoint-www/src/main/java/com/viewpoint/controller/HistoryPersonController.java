package com.viewpoint.controller;

import com.viewpoint.dataobject.ExamLevel;
import com.viewpoint.dataobject.HistoryPerson;
import com.viewpoint.service.ExamLevelService;
import com.viewpoint.service.HistoryPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/view")
public class HistoryPersonController {

    @Autowired
    private HistoryPersonService historyPersonService;

    @Autowired
    private ExamLevelService examLevelService;

    @GetMapping("/kjwh")
    public String kjwh(Model model){

        return "view/kjwh";
    }

    @GetMapping("/kjwh/detail/{id}")
    public String kjwhDetail(Model model, @PathVariable(value = "levelId") String levelId){
        ExamLevel examLevel = examLevelService.findById(Integer.valueOf(levelId));
        model.addAttribute("examLevel",examLevel);
        List<HistoryPerson> historyPersonList = historyPersonService.findUpHistoryPersonList(levelId);
        model.addAttribute("historyPersonList",historyPersonList);
        return "view/kjwh-detail";
    }
}
