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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
    public String list(Model model){

        List<HistoryFootprints> historyFootprintsList = historyFootprintsService.orderBySort();
        model.addAttribute("historyFootprintsList",historyFootprintsList);
        List<Integer> sortList = this.getSortList();

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

    @RequestMapping("/delete")
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

    /**
     * 判断sort是否有重复
     * @param sort
     * @return
     */
    @RequestMapping("/sortRepeat")
    @ResponseBody
    public ResultVO sortRepeat(@RequestParam(value = "sort")Integer sort,@RequestParam(value = "historyId",required = false) Integer historyId){
        //取sort的集合
        List<Integer> sortList = this.getSortList();
        if(!StringUtils.isEmpty(historyId)){
            HistoryFootprints examLevel = historyFootprintsService.findById(historyId);
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
    public ResultVO onDown(@RequestParam(value = "historyId")Integer historyId){
        //取sort的集合
        List<Integer> sortList = this.getSortList();
        //对sort进行排序
        Collections.sort(sortList);
        for(int i : sortList){
            System.out.println(i);
        }

        //取出这个足迹对象
        HistoryFootprints historyFootprints = historyFootprintsService.findById(historyId);
        int currentSort = historyFootprints.getSort();
        System.out.println("当前对象sort的值"+currentSort);
        if(currentSort == sortList.get(sortList.size()-1)){
            //说明是最大一个序号
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"已经到底了！");
        }
        //当前对象sort的下标
        int currentIndex = sortList.indexOf(historyFootprints.getSort());
        System.out.println("当前对象sort的下标"+currentIndex);
        //下一个对象的sort
        int nextSort = sortList.get(currentIndex+1);
        System.out.println("下一个对象的sort"+nextSort);
        //通过序号获得他下一个对象
        HistoryFootprints historyFootprintsNext = historyFootprintsService.findBySort(nextSort);
        //互相交换两个对象的sort
        historyFootprints.setSort(nextSort);
        //由于sort值不能重复所以先设置一个不可能出现的sort，先插入进去
        historyFootprintsNext.setSort(985465852);
        historyFootprintsService.save(historyFootprintsNext);
        //插入到数据库
        historyFootprintsService.save(historyFootprints);
        //把下一个对象设置成当前的序号并插入
        historyFootprintsNext.setSort(currentSort);
        historyFootprintsService.save(historyFootprintsNext);
        return ResultVOUtil.success();
    }

    @RequestMapping("/onUp")
    @ResponseBody
    public ResultVO onUp(@RequestParam(value = "historyId")Integer historyId){
        //取sort的集合
        List<Integer> sortList = this.getSortList();
        //对sort进行排序
        Collections.sort(sortList);

        //取出这个足迹对象
        HistoryFootprints historyFootprints = historyFootprintsService.findById(historyId);
        int currentSort = historyFootprints.getSort();

        if(currentSort == sortList.get(0)){
            //说明是最小一个序号
            return ResultVOUtil.error(ResultEnum.ERROR.getCode(),"已经到顶了！");
        }
        //当前对象sort的下标
        int currentIndex = sortList.indexOf(historyFootprints.getSort());

        //上一个对象的sort
        int nextSort = sortList.get(currentIndex-1);

        //通过序号获得他上一个对象
        HistoryFootprints historyFootprintsNext = historyFootprintsService.findBySort(nextSort);
        //互相交换两个对象的sort
        historyFootprints.setSort(nextSort);
        //由于sort值不能重复所以先设置一个不可能出现的sort，先插入进去
        historyFootprintsNext.setSort(985465852);
        historyFootprintsService.save(historyFootprintsNext);
        //插入到数据库
        historyFootprintsService.save(historyFootprints);
        //把下一个对象设置成当前的序号并插入
        historyFootprintsNext.setSort(currentSort);
        historyFootprintsService.save(historyFootprintsNext);
        return ResultVOUtil.success();
    }



    //获得所有的sort
    private List<Integer> getSortList(){
        //拿出所有的对象并取出sort封装为一个集合
        List<HistoryFootprints> historyFootprintsList = historyFootprintsService.findAll();
        List<Integer> sortList = new ArrayList<>();
        //遍历出所有的sort
        for(HistoryFootprints historyFootprints : historyFootprintsList){
            sortList.add(historyFootprints.getSort());
        }
        return  sortList;
    }

}
