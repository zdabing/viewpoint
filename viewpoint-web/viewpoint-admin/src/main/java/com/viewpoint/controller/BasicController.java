package com.viewpoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class BasicController {

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping(value = {"/","index"})
    public String index(){
        return "index";
    }
}
