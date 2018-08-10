package com.viewpoint.controller;

import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.ExhibitsService;
import com.viewpoint.service.HistoryLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/exhibit")
@Slf4j
public class ExhibitController {

    @Autowired
    private ExhibitsService exhibitsService;

    @Autowired
    private HistoryLogService historyLogService;

    @GetMapping("/detail/{exhibitsId}")
    public String detail(@PathVariable(value = "exhibitsId") String exhibitsId,
                         Model model){
        ExhibitsInfo exhibitsMain = exhibitsService.findOne(exhibitsId);
        if (exhibitsMain == null || exhibitsMain.getExhibitsStatus() != StatusEnum.UP.getCode()) {
            throw new ViewpointException(ResultEnum.EXHIBITS_NOT_EXIST);
        }
        try {
            historyLogService.save(exhibitsId,"123123");
        }catch (ViewpointException e){
            log.error("历史记录新增失败");
        }
        model.addAttribute("exhibitsMain",exhibitsMain);
        if(StringUtils.isEmpty(exhibitsMain.getParentId())){
            List<ExhibitsInfo> exhibitsInfoListOld= exhibitsService.findByParentId(exhibitsMain.getExhibitsId());
            List<ExhibitsInfo> exhibitsInfoList = exhibitsInfoListOld.stream().filter(e -> e.getExhibitsStatus() == StatusEnum.UP.getCode()).collect(Collectors.toList());
            model.addAttribute("exhibitsInfoList",exhibitsInfoList);
        }
        return "exhibit/detail";
    }
}
