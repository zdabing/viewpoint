package com.viewpoint.controller;

import com.viewpoint.dataobject.User;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.UserService;
import com.viewpoint.utils.ResultVOUtil;
import com.viewpoint.vo.ResultVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/index")
    public String index(){
        return "intra/user/list";
    }

    @ResponseBody
    @RequestMapping("/admin/list")
    public ResultVO admin(){
        List<User> userList = userService.findAdmin();
        return ResultVOUtil.success(userList,Long.valueOf(userList.size()));
    }

    @ResponseBody
    @RequestMapping("/registered/list")
    public ResultVO registered(){
        List<User> userList = userService.findAdmin();
        return ResultVOUtil.success(userList,Long.valueOf(userList.size()));
    }

    @RequestMapping("/add")
    public String add(@RequestParam(value = "id",required = false) Integer id , Model model){
        if (!StringUtils.isEmpty(id)) {
            User user = userService.findById(id);
            model.addAttribute("user",user);
        }
        return "intra/user/adminAdd";
    }

    @PostMapping("/save")
    @ResponseBody
    public ResultVO save(User user){
        User userInfo = new User();
        try {
            //如果productId为空, 说明是新增
            if(!StringUtils.isEmpty(user.getId())){
                userInfo = userService.findById(user.getId());
            }
            BeanUtils.copyProperties(user, userInfo);
            userInfo.setRole(2);
            userService.save(userInfo);
        }  catch (ViewpointException e) {
            return ResultVOUtil.error(1,e.getMessage());
        }
        return ResultVOUtil.success();
    }
}
