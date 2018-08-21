package com.gon.controller;


import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Controller
public class IndexController extends BaseController{

    private Logger logger = Logger.getLogger(this.getClass());

    @RequestMapping(value="index")
    public ModelAndView main(HttpServletRequest request,HttpServletResponse response){
        BufferedImage image = Base.robot.screenCapture();
        String path = request.getSession().getServletContext().getRealPath("/") + "resource/images/desktop.png";
        try {
            FileOutputStream out = new FileOutputStream(new File(path));
            ImageIO.write(image,"png",out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        ModelAndView mv = new ModelAndView("/index/main.jsp","command","LOGIN SUCCESS");
        return mv;
    }
}