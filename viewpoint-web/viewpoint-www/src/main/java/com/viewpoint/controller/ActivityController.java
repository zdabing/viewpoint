package com.viewpoint.controller;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/activity")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/item")
    public String item(Model model){
        List<Activity> activityList = activityService.findUpAll();
        model.addAttribute("activityList",activityList);
        return "activity/item";
    }
}
