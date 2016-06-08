package com.gon.controller;


import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.gon.custom.directive.QueryDirective;
import com.manyit.common.util.NumberUtil;
import com.manyit.common.util.StringUtil;


@Controller
public class HelloWordController extends BaseController{
    @RequestMapping(value="main")
    public ModelAndView main(HttpServletRequest request,HttpServletResponse response){
        
        ModelAndView mv = new ModelAndView("/index/main.jsp","command","LOGIN SUCCESS");
        return mv;
    }
    
    @RequestMapping(value={"/helloWord.ftl", "/helloWord_*.ftl"})
    public ModelAndView viewCat(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        String viewName = "helloWord.ftl";
        mv.addObject("name", "helloWord!!!");
        int currentPage = getCurrentPage(request.getRequestURI());
        int pageSize = 10;
        Map<String,Object> paramMap = getParamMap(request);
        mv.addObject("query", new QueryDirective(viewName,currentPage,pageSize,paramMap));
        mv.setViewName("/" + viewName);
        return mv;
    }
    
    @RequestMapping(value={"/helloWord.html", "/helloWord_*.html"})
    public ModelAndView hello(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        String viewName = "helloWord.ftl";
        mv.addObject("name", "helloWord!!!");
        int currentPage = getCurrentPage(request.getRequestURI());
        int pageSize = 10;
        Map<String,Object> paramMap = getParamMap(request);
        mv.addObject("query", new QueryDirective(viewName,currentPage,pageSize,paramMap));
        mv.setViewName("/" + viewName);
        return mv;
    }
    
    @RequestMapping(value={"/content/*/*.html"})
    public ModelAndView viewContent(HttpServletRequest request,HttpServletResponse response){
        ModelAndView mv = new ModelAndView();
        String viewName = "content.ftl";
        Map<String,Object> paramMap = getParamMap(request);
        mv.addObject("query", new QueryDirective(paramMap));
        mv.setViewName("/" + viewName);
        return mv;
    }
    
    private Map<String,Object> getParamMap(HttpServletRequest request){
        Map<String,Object> param = new HashMap<String, Object>();
        Enumeration enu = request.getParameterNames();
        Object requestValue = null;
        String requestName = "";
        while (enu.hasMoreElements()) {
            requestName = StringUtil.convertToString(enu.nextElement());
            requestValue = request.getParameter(requestName);
            param.put(requestName, requestValue);
        }
        enu = request.getAttributeNames();
        while (enu.hasMoreElements()) {
            requestName = StringUtil.convertToString(enu.nextElement());
            requestValue = request.getAttribute(requestName);
            param.put(requestName, requestValue);
        }
        return param;
    }
    
    private int getCurrentPage(String uri){
        int ret = 0;
        if(StringUtils.isNotBlank(uri)){
            ret = NumberUtil.convertToInt(uri.substring(uri.lastIndexOf("_") + 1,uri.lastIndexOf(".")));
        }
        if(ret == 0){
            ret = 1;
        }
        return ret;
    }
}