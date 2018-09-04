package com.viewpoint.controller;

import com.viewpoint.constant.CookieConstant;
import com.viewpoint.dataobject.*;
import com.viewpoint.service.*;
import com.viewpoint.util.CookieUtil;
import com.viewpoint.vo.ActivityOrderVO;
import com.viewpoint.vo.HistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/member")
@Controller
public class MemberController {

    @Autowired
    private ActivityOrderService activityOrderService;

    @Autowired
    private ExhibitsService exhibitsService;

    @Autowired
    private HistoryLogService historyLogService;

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;

    @GetMapping("/center")
    public String index(HttpServletRequest request, Model model) {
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        String openid = cookie.getValue();
        User user = userService.findByOpenid(openid);
        model.addAttribute("member",user);
        // 历史记录
        List<HistoryLog> historyLogList = historyLogService.findByOpenid(openid);
        List<HistoryVO> historyVOList = new ArrayList<>();
        for (HistoryLog historyLog : historyLogList) {
            HistoryVO historyVO = new HistoryVO();
            BeanUtils.copyProperties(historyLog,historyVO);
            ExhibitsInfo exhibitsInfo = exhibitsService.findOne(historyLog.getMasterId());
            historyVO.setExhibit(exhibitsInfo);
            historyVOList.add(historyVO);
        }
        model.addAttribute("historyList",historyVOList);
        return "member/my";
    }

    @GetMapping("/activity")
    public String activity(HttpServletRequest request, Model model){
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        String openid = cookie.getValue();
        //User user = userService.findByOpenid(openid);
        List<ActivityOrder> activityOrderList = activityOrderService.findByBuyerOpenid(openid);
        List<ActivityOrderVO> activityOrderVOList = activityOrderList.stream().map(e -> convert(e,activityService.findOne(e.getActivityId()))).collect(Collectors.toList());
        model.addAttribute("activityOrderVOList",activityOrderVOList);
        return "member/activity";
    }

    public ActivityOrderVO convert(ActivityOrder activityOrder, Activity activity){
        ActivityOrderVO activityOrderVO = new ActivityOrderVO();
        activityOrderVO.setActivityCode(activity.getActivityId());
        activityOrderVO.setActivityName(activity.getActivityName());
        activityOrderVO.setOrderCode(activityOrder.getActivityOrderId());
        activityOrderVO.setOrderStatus(activityOrder.getOrderStatus() == 0 ? "报名中" : "已结束");
        return activityOrderVO;
    }
}
