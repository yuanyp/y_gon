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

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gon.controller.util.AppContextServlet;
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
    @RequestMapping(value="login")
    public void login(HttpServletRequest request,HttpServletResponse response){
    	String name = request.getParameter("name");
    	String pass = request.getParameter("pass");
        Map<String,Object> ret = new HashMap<>();
        Gson gson = new Gson();
        PrintWriter writer = null;
        try {
        	String config_name = AppContextServlet.getProperty("config_name");
        	String config_pass = AppContextServlet.getProperty("config_pass");
        	if(name.equals(config_name) && pass.equals(config_pass)) {
        		ret.put("code", 0);
        		request.getSession().setAttribute("_userinfo", config_name);
        	}else {
        		ret.put("error_msg", "账号密码错误");
        	}
            writer = response.getWriter();
        } catch (Exception e) {
            logger.error("", e);
            ret.put("code", -1);
            ret.put("error_msg", e.getMessage());
        }
        writer.print(gson.toJson(ret));
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

    @RequestMapping(value="mouse_opt")
    public void mouse_opt(HttpServletRequest request,HttpServletResponse response){
    	Map<String,Object> ret = new HashMap<>();
    	Gson gson = new Gson();
    	PrintWriter writer = null;
        try {
        	String x_y = request.getParameter("x_y");
        	String l = request.getParameter("l");
        	if(StringUtils.isNotBlank(x_y)){
        		String[] xy = x_y.split(",");
        		if(xy.length == 2){
        			int x = Integer.parseInt(xy[0]);
        			int y = Integer.parseInt(xy[1]);
        			Base.robot.delay(200);
        			logger.info("x_y:" + x_y +"," + Boolean.valueOf(l));
            		Base.mouse.mouseClick(x, y, Boolean.valueOf(l));
            		Base.robot.delay(200);
        		}
        	}
        	writer = response.getWriter();
        	ret.put("code", 0);
        } catch (Exception e) {
        	logger.error("", e);
        	ret.put("code", -1);
            ret.put("error_msg", e.getMessage());
        }
        writer.print(gson.toJson(ret));
    }
    
    @RequestMapping(value="key_opt")
    public void key_opt(HttpServletRequest request,HttpServletResponse response){
    	Map<String,Object> ret = new HashMap<>();
    	Gson gson = new Gson();
    	PrintWriter writer = null;
        try {
        	int k = Integer.parseInt(request.getParameter("key"));
        	Base.press.keyPress(k);
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
