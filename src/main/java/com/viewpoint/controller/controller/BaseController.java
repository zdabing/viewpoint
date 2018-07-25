package com.viewpoint.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

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
