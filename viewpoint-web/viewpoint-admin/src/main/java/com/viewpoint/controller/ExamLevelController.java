package com.viewpoint.controller;


import com.viewpoint.dataobject.ExamLevel;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.ExamLevelService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Collections;
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
        List<ExamLevel> examLevelList = examLevelService.findAllBySort();
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
            examLevel1.setLevelIcon(examLevel.getLevelIcon());
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

    /**
     * 判断sort是否有重复
     * @param sort
     * @return
     */
    @RequestMapping("/sortRepeat")
    @ResponseBody
    public ResultVO sortRepeat(@RequestParam(value = "sort")Integer sort,@RequestParam(value = "levelId",required = false) Integer levelId){
        List<Integer> sortList = this.getSortList();
        if(!StringUtils.isEmpty(levelId)){
            ExamLevel examLevel = examLevelService.findById(levelId);
            for(int i = 0;i < sortList.size();i++){
                if(sortList.get(i) == examLevel.getSort()){
                    sortList.remove(i);
                }
            }
        }

        //查看有没有重复的序号sort
        if(sortList.contains(sort)){
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"序号重复，请输入新的序号");
        }
        return ResultVOUtil.success();
    }

    @RequestMapping("/onDown")
    @ResponseBody
    public ResultVO onDown(@RequestParam(value = "levelId")Integer levelId){
        //取sort的集合
        List<Integer> sortList = this.getSortList();
        //对sort进行排序
        Collections.sort(sortList);
        //取出这个足迹对象
        ExamLevel examLevel = examLevelService.findById(levelId);
        int currentSort = examLevel.getSort();
        if(currentSort == sortList.get(sortList.size()-1)){
            //说明是最大一个序号
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"已经到底了！");
        }
        //当前对象sort的下标
        int currentIndex = sortList.indexOf(examLevel.getSort());
        //下一个对象的sort
        int nextSort = sortList.get(currentIndex+1);
        //通过序号获得他下一个对象
        ExamLevel examLevelNext = examLevelService.findBySort(nextSort);
        //互相交换两个对象的sort
        examLevel.setSort(nextSort);
        //由于sort值不能重复所以先设置一个不可能出现的sort，先插入进去
        examLevelNext.setSort(985465852);
        examLevelService.save(examLevelNext);
        //插入到数据库
        examLevelService.save(examLevel);
        //把下一个对象设置成当前的序号并插入
        examLevelNext.setSort(currentSort);
        examLevelService.save(examLevelNext);
        return ResultVOUtil.success();
    }

    @RequestMapping("/onUp")
    @ResponseBody
    public ResultVO onUp(@RequestParam(value = "levelId")Integer levelId){
        //取sort的集合
        List<Integer> sortList = this.getSortList();
        //对sort进行排序
        Collections.sort(sortList);

        //取出这个足迹对象
        ExamLevel examLevel = examLevelService.findById(levelId);
        int currentSort = examLevel.getSort();

        if(currentSort == sortList.get(0)){
            //说明是最小一个序号
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"已经到顶了！");
        }
        //当前对象sort的下标
        int currentIndex = sortList.indexOf(examLevel.getSort());

        //上一个对象的sort
        int nextSort = sortList.get(currentIndex-1);

        //通过序号获得他上一个对象
        ExamLevel  examLevelNext = examLevelService.findBySort(nextSort);
        //互相交换两个对象的sort
        examLevel.setSort(nextSort);
        //由于sort值不能重复所以先设置一个不可能出现的sort，先插入进去
        examLevelNext.setSort(985465852);
        examLevelService.save(examLevelNext);
        //插入到数据库
        examLevelService.save(examLevel);
        //把下一个对象设置成当前的序号并插入
        examLevelNext.setSort(currentSort);
        examLevelService.save(examLevelNext);
        return ResultVOUtil.success();
    }

    //获得所有的sort
    private List<Integer> getSortList(){
        //拿出所有的对象并取出sort封装为一个集合
        List<ExamLevel> examLevelList = examLevelService.findAll();
        List<Integer> sortList = new ArrayList<>();
        //遍历出所有的sort
        for(ExamLevel examLevel : examLevelList){
            sortList.add(examLevel.getSort());
        }
        return  sortList;
    }
}
