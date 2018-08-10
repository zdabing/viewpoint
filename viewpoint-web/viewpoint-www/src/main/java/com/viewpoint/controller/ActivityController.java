package com.viewpoint.controller;

import com.viewpoint.dataobject.Activity;
import com.viewpoint.dataobject.ActivityOrder;
import com.viewpoint.enums.ResultEnum;
import com.viewpoint.enums.StatusEnum;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.ActivityOrderService;
import com.viewpoint.service.ActivityService;
import com.viewpoint.util.KeyUtil;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ActivityVO;
import com.viewpoint.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/activity")
@Slf4j
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @Autowired
    private ActivityOrderService activityOrderService;

    /**
     * 活动列表页
     * @param model
     * @return
     */
    @GetMapping("/item")
    public String item(Model model){
        List<Activity> activityList = activityService.findUpAll();
        List<ActivityVO> activityVOList = new ArrayList<>();
        activityList.forEach(e ->{
            ActivityVO activityVO = new ActivityVO();
            BeanUtils.copyProperties(e,activityVO);
            activityVO.setOrderCount(activityOrderService.countByActivityId(e.getActivityId()));
            // 活动状态判断
            if (LocalDateTime.now().isBefore(activityVO.getStartTime())){
                activityVO.setActivityStatus(StatusEnum.ACTIVITY_STATUS_BEFORE.getMessage());
            }else if (LocalDateTime.now().isAfter(activityVO.getEndTime())){
                activityVO.setActivityStatus(StatusEnum.ACTIVITY_STATUS_AFTER.getMessage());
            } else {
                activityVO.setActivityStatus(StatusEnum.ACTIVITY_STATUS_NOW.getMessage());
            }
            activityVOList.add(activityVO);
        });
        model.addAttribute("activityList",activityVOList);
        return "activity/item";
    }

    @GetMapping("/detail/{activityId}")
    public String detail(@PathVariable(value = "activityId") String activityId, Model model){
        Activity activity = activityService.findOne(activityId);
        if (activity == null || activity.getEnabled() != StatusEnum.UP.getCode()){
            throw new ViewpointException(ResultEnum.ACTIVITY_NOT_EXIST);
        }
        try {
            activity.setHot(activity.getHot()+1);
            activityService.save(activity);
        } catch (ViewpointException e) {
            log.error("热度更新失败");
        }
        model.addAttribute("activity",activity);
        return "activity/detail";
    }

    @PostMapping("/order")
    @ResponseBody
    public ResultVO order(@RequestParam(value = "buyerName") String buyerName,
                          @RequestParam(value = "buyerPhone") String buyerPhone,
                          @RequestParam(value = "activityId") String activityId){
        ActivityOrder activityOrder = new ActivityOrder();
        // TODO 获取微信openId
        activityOrder.setBuyerOpenid("123456789");
        activityOrder.setBuyerName(buyerName);
        activityOrder.setBuyerPhone(buyerPhone);
        activityOrder.setActivityOrderId(KeyUtil.genUniqueKey());
        activityOrder.setActivityId(activityId);
        try {
            activityOrderService.save(activityOrder);
            return ResultVOUtil.success();
        } catch (ViewpointException e){
            return ResultVOUtil.error(1,e.getMessage());
        }
    }
}
