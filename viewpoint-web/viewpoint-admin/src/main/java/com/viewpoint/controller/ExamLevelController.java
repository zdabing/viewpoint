package com.viewpoint.controller;


import com.viewpoint.dataobject.Activity;
import com.viewpoint.dataobject.ExamLevel;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.ExamLevelService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("examLevel")
public class ExamLevelController {

    @Autowired
    private ExamLevelService examLevelService;

    @RequestMapping("/index")
    public String index(){
        return "examLevel/list";
    }

    @ResponseBody
    @RequestMapping("/list")
    public ResultVO list( Model model){
        List<ExamLevel> examLevelList = examLevelService.findAll();
        return ResultVOUtil.success(examLevelList);
    }

    @RequestMapping("/add")
    public String add(@RequestParam(value = "levelId",required = false) Integer levelId ,Model model){
        if (levelId != null){
            //如果不为空就把数据显现给前端
            ExamLevel examLevel = examLevelService.findById(levelId);
            model.addAttribute("examLevel",examLevel);

        }
        return "examLevel/newsAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultVO save(@Valid ExamLevel examLevel, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return ResultVOUtil.error(1,bindingResult.getFieldError().getDefaultMessage());
        }
        if(StringUtils.isEmpty(examLevel.getLevelId())){
            //说明是新增的
            ExamLevel examLevel1 = examLevelService.save(examLevel);
            if (examLevel == null){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"新增错误，请重新填写");
            }
        }else {
            //说明是修改的
            ExamLevel examLevel1 = examLevelService.findById(examLevel.getLevelId());
            examLevel1.setLevelName(examLevel.getLevelName());
            examLevel1.setSort(examLevel.getSort());
            examLevel1.setLevelDesc(examLevel.getLevelDesc());
            ExamLevel examLevel2 = examLevelService.save(examLevel1);
            if (examLevel2 == null){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"修改错误，请重新填写");
            }
        }
        return ResultVOUtil.success();
    }

    //上下架操作
    @PostMapping("/updateSale")
    @ResponseBody
    public ResultVO updateSale(Integer levelId,Integer enabled){
        try {
            examLevelService.updateSale(levelId,enabled);
        } catch (ViewpointException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }

    //删除操作
    @PostMapping("/delete")
    @ResponseBody
    public ResultVO delete(Integer levelId){
        if(StringUtils.isEmpty(levelId)){
            //如果levelId为空的
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"参数错误，请重试");
        }
        examLevelService.delete(levelId);
        return ResultVOUtil.success();
    }
}
