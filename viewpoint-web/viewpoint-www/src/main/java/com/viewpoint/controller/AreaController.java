package com.viewpoint.controller;


import com.viewpoint.dataobject.Areas;
import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.AreasService;
import com.viewpoint.service.ExhibitsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private AreasService areasService;

    @Autowired
    private ExhibitsService exhibitsService;

    @GetMapping("/guide")
    public String list(Model model){
        List<Areas> areaList = areasService.findAll();
        areaList.stream().forEach(e -> e.setAreasContent(e.getAreasContent().replaceAll("<[^>]*>","")));
        model.addAttribute("areaList",areaList);
        return "area/guide";
    }

    @GetMapping("/detail/{areasId}")
    public String detail(Model model, @PathVariable(value = "areasId") Integer areasId){
        Areas area = areasService.findByAreasId(areasId);
        if (area == null) {
            throw new ViewpointException(ResultEnum.AREA_NOT_EXIST);
        }
        model.addAttribute("area",area);
        List<ExhibitsInfo> exhibitsInfoList = exhibitsService.findByAreasId(String.valueOf(area.getAreasId()));
        model.addAttribute("exhibitsInfoList",exhibitsInfoList);
        return "area/detail";
    }

}
