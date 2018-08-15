package com.viewpoint.controller;

import com.viewpoint.dataobject.ExamLevel;
import com.viewpoint.dataobject.HistoryPerson;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.ExamLevelService;
import com.viewpoint.service.HistoryPersonService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/historyPerson")
public class HistoryPersonController {

    @Autowired
    private HistoryPersonService historyPersonService;

    @Autowired
    private ExamLevelService examLevelService;

    @RequestMapping("/index")
    public String index(){
        return "historyPerson/list";
    }

    @RequestMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                         @RequestParam(value = "limit", defaultValue = "10") Integer size){

        Page<HistoryPerson> historyPersonPage =  historyPersonService.findAll(PageRequest.of(page - 1 ,size));
        List<HistoryPerson> historyPersonList = historyPersonPage.getContent();
        historyPersonList.stream().forEach(e ->
            e.setLevelId(examLevelService.findById(Integer.valueOf(e.getLevelId())).getLevelName())
        );
        return ResultVOUtil.success(historyPersonList,historyPersonPage.getTotalElements());
    }

    @RequestMapping("/add")
    public String add(@RequestParam(value = "personId",required = false) Integer  personId, Model model){

        List<ExamLevel> examLevelList = examLevelService.findAll();
        model.addAttribute("examLevelList",examLevelList);

        if(!StringUtils.isEmpty(personId)){
            //说明是修改的
            HistoryPerson historyPerson = historyPersonService.findById(personId);
            model.addAttribute("historyPerson",historyPerson);
        }
        return "historyPerson/newsAdd";
    }

    @RequestMapping("/save")
    @ResponseBody
    public ResultVO save(HistoryPerson historyPerson){
        if(StringUtils.isEmpty(historyPerson.getPersonId())){
            //说明是新增的
            HistoryPerson historyPerson1 = historyPersonService.save(historyPerson);
            if(historyPerson1 == null){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"参数错误，请重新新增");
            }
        }else{
            HistoryPerson historyPerson1 = historyPersonService.findById(historyPerson.getPersonId());
            historyPerson1.setLevelId(historyPerson.getLevelId());
            historyPerson1.setPersonDesc(historyPerson.getPersonDesc());
            historyPerson1.setPersonIcon(historyPerson.getPersonIcon());
            historyPerson1.setPersonName(historyPerson.getPersonName());
            HistoryPerson historyPerson2 = historyPersonService.save(historyPerson1);
            if(historyPerson2 == null){
                return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"参数错误，请重新新增");
            }
        }
        return ResultVOUtil.success();

    }

    @RequestMapping("/del")
    @ResponseBody
    public ResultVO delete(Integer personId){
        if(StringUtils.isEmpty(personId)){
            return ResultVOUtil.error(0,"参数错误");
        }
        historyPersonService.deleteById(personId);
        return ResultVOUtil.success();
    }

    @PostMapping("/updateSale")
    @ResponseBody
    public ResultVO updateSale(Integer personId,Integer enabled){
        try {
            historyPersonService.updateSale(personId,enabled);
        } catch (ViewpointException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }
}
