package com.viewpoint.controller;

import com.viewpoint.dataobject.ActivityOrder;
import com.viewpoint.dataobject.ExhibitsInfo;
import com.viewpoint.dataobject.HistoryLog;
import com.viewpoint.dataobject.User;
import com.viewpoint.service.ActivityOrderService;
import com.viewpoint.service.ExhibitsService;
import com.viewpoint.service.HistoryLogService;
import com.viewpoint.service.UserService;
import com.viewpoint.vo.HistoryVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

    @GetMapping("/{openid}")
    public String index(@PathVariable(value = "openid") String openid, Model model) {
        User user = save(openid);
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

    @GetMapping("/activity/{openid}")
    public String activity(@PathVariable(value = "openid") String openid , Model model){
        save(openid);
        List<ActivityOrder> activityOrderList = activityOrderService.findByBuyerOpenid(openid);
        model.addAttribute("activityOrderList",activityOrderList);
        return "member/activity";
    }

    /**
     * 生成会员记录
     * @param openid
     */
    private User save(String openid){

        User user = userService.findByOpenid(openid);
        if (user == null) {
            User user1 = new User();
            user1.setOpenid(openid);
            user1.setIcon("http://192.168.1.145:7777/2018/08/02/202cb962ac59075b964b07152d234b70.jpg");
            user1.setAlias("我真不是大饼");
            user1.setName("我真不是大饼");
            user = userService.save(user1);
        }
        return user;
    }
}
