package com.viewpoint.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class BaseController {

   /* @Value("${server.servlet.context-path}")
    private String path = "";

    @ModelAttribute
    public void addAttributes(Model model) {
        model.addAttribute("resRoot", path);
        model.addAttribute("base", path);
    }*/
}
