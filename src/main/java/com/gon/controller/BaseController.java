package com.gon.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseController {
    @ModelAttribute  
    public void initPath(HttpServletRequest request,HttpServletResponse response,ModelMap model){  
        String path = request.getContextPath();
        String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
        model.addAttribute("path", path);
        model.addAttribute("basePath", basePath);
    } 
}
