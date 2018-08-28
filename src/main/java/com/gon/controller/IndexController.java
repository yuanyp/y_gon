package com.gon.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;


@Controller
public class IndexController extends BaseController{

    private Logger logger = Logger.getLogger(this.getClass());

    private void refreshDesk(String realPath){
        BufferedImage image = Base.robot.screenCapture();
        String path = realPath + "resource/images/desktop.png";
        try {
            FileOutputStream out = new FileOutputStream(new File(path));
            ImageIO.write(image,"png",out);
        } catch (Exception e) {
            logger.error("", e);
        }
    }
    @RequestMapping(value="refresh")
    public void refresh(HttpServletRequest request,HttpServletResponse response){
        String realPath = request.getSession().getServletContext().getRealPath("/");
        refreshDesk(realPath);
        Map<String,Object> ret = new HashMap<>();
        Gson gson = new Gson();
        PrintWriter writer = null;
        try {
            writer = response.getWriter();
            ret.put("code", 0);
        } catch (Exception e) {
            logger.error("", e);
            ret.put("code", -1);
            ret.put("error_msg", e.getMessage());
        }
        writer.print(gson.toJson(ret));
    }
    @RequestMapping(value="index")
    public ModelAndView main(HttpServletRequest request,HttpServletResponse response){
        String realPath = request.getSession().getServletContext().getRealPath("/");
        refreshDesk(realPath);
        ModelAndView mv = new ModelAndView("/index/main.jsp","command","LOGIN SUCCESS");
        return mv;
    }
    @RequestMapping(value="key_opt")
    public void key_opt(HttpServletRequest request,HttpServletResponse response){
    	Map<String,Object> ret = new HashMap<>();
    	Gson gson = new Gson();
    	PrintWriter writer = null;
        try {
        	Base.press.keyPress(Base.press.I);
        	writer = response.getWriter();
        	ret.put("code", 0);
        } catch (Exception e) {
        	logger.error("", e);
        	ret.put("code", -1);
            ret.put("error_msg", e.getMessage());
        }
        writer.print(gson.toJson(ret));
    }
}