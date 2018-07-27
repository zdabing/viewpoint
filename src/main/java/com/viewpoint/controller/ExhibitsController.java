package com.viewpoint.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 展品管理
 */
@Controller
@RequestMapping("/exhibit")
public class ExhibitsController {

    @GetMapping("/index")
    public String index(){
        return "intra/exhibit/list";
    }
}
