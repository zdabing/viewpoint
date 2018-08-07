package com.viewpoint.controller;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    @Autowired
    private ActivityService activityService;

    @RequestMapping("/index")
    public String index(Model model){
        List<Activity> activityList = activityService.findUpAll();
        // 截取前4个
        activityList = activityList.stream().limit(4).collect(Collectors.toList());
        model.addAttribute("activityList",activityList);
        return "index";
    }
}
