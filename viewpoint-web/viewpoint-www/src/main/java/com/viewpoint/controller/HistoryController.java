package com.viewpoint.controller;

import com.viewpoint.dataobject.HistoryFootprints;
import com.viewpoint.service.HistoryFootprintsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 历史足迹Controller
 */
@Controller
@RequestMapping("/history")
public class HistoryController {

    @Autowired
    private HistoryFootprintsService historyFootprintsService;

    @RequestMapping("/foot")
    public String index(Model model){
        List<HistoryFootprints> historyFootprintsList = historyFootprintsService.orderBySort();
        model.addAttribute("historyFootprintsList",historyFootprintsList);
        return "view/footprint";
    }

}
