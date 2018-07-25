package com.viewpoint.controller;

import com.viewpoint.dataobject.User;
import com.viewpoint.exception.ViewpointException;
import com.viewpoint.service.UserService;
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

/**
 * 用户controller
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/admin/index")
    public String adminIndex(){
        return "intra/admin/list";
    }

    @GetMapping("/registered/index")
    public String registeredIndex(){
        return "intra/registered/list";
    }

    @ResponseBody
    @RequestMapping("/list/{role}")
    public ResultVO adminList(@RequestParam(value = "page", defaultValue = "1") Integer page,
                              @RequestParam(value = "limit", defaultValue = "10") Integer size,@PathVariable Integer role){
        Page<User> userList = userService.findByRole(role,PageRequest.of(page - 1 ,size));
        return ResultVOUtil.success(userList.getContent(),userList.getTotalElements());
    }

    @RequestMapping("/add")
    public String add(@RequestParam(value = "id",required = false) Integer id , Model model){
        if (!StringUtils.isEmpty(id)) {
            User user = userService.findById(id);
            model.addAttribute("user",user);
        }
        return "intra/admin/adminAdd";
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
