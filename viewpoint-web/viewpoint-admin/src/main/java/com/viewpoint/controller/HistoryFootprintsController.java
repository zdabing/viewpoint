package com.viewpoint.controller;

import com.viewpoint.dataobject.HistoryFootprints;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.service.HistoryFootprintsService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/historyFootprints")
public class HistoryFootprintsController {

    @Autowired
    private HistoryFootprintsService historyFootprintsService;


    /**
     * 历史足迹时间线页面
     * @param model
     * @return
     */
    @GetMapping("/index")
    public String list(Model model,@RequestParam(required = false,value = "orderBy") Integer orderBy){
        if(!StringUtils.isEmpty(orderBy)){
            List<HistoryFootprints> historyFootprintsList = historyFootprintsService.orderBySort();
            model.addAttribute("historyFootprintsList",historyFootprintsList);
        }else{
            List<HistoryFootprints> historyFootprintsList = historyFootprintsService.orderByStartTime();
            model.addAttribute("historyFootprintsList",historyFootprintsList);
        }
        return "historyFootprints/list";
    }

    /**
     * 增加或更改页面
     * @param model
     * @param historyId
     * @return
     */
    @RequestMapping("/add")
    public String add(Model model,@RequestParam(required = false,value = "historyId") Integer historyId) {
        if(historyId != null){
            HistoryFootprints historyFootprints = historyFootprintsService.findById(historyId);
            model.addAttribute("historyFootprints",historyFootprints);
        }
        return "historyFootprints/newsAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultVO save(HistoryFootprints historyFootprints,BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        if(StringUtils.isEmpty(historyFootprints.getHistoryId())){
            //说明是新增
            historyFootprintsService.save(historyFootprints);
            return ResultVOUtil.success();
        }else{
            //说明是修改
            HistoryFootprints historyOld = historyFootprintsService.findById(historyFootprints.getHistoryId());
            historyFootprints.setCreateTime(historyOld.getCreateTime());
            historyFootprints.setUpdateTime(LocalDateTime.now());
            historyFootprintsService.save(historyFootprints);
            return ResultVOUtil.success();
        }
    }

    @GetMapping("/delete")
    @ResponseBody
    public ResultVO delete(@RequestParam(value = "historyId")Integer historyId){
        historyFootprintsService.delete(historyId);
        return ResultVOUtil.success();
    }

    @RequestMapping("/orderByStartTime")
    @ResponseBody
    public ResultVO orderByStartTime(Model model){
        List<HistoryFootprints> historyFootprintsList = historyFootprintsService.orderByStartTime();
        model.addAttribute("historyFootprintsList",historyFootprintsList);
        return ResultVOUtil.success();
    }

}
