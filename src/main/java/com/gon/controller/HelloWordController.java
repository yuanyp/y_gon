package com.gon.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HelloWordController {
    @RequestMapping(value="main")
    public ModelAndView main(HttpServletRequest request,HttpServletResponse response){
        
        ModelAndView mv = new ModelAndView("/index/main.jsp","command","LOGIN SUCCESS");
        return mv;
    }
    
    @RequestMapping(value="hello")
    public ModelAndView hello(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        mv.addObject("name", "helloWord!!!");
        mv.setViewName("/helloWord.ftl");
        return mv;
    }
}