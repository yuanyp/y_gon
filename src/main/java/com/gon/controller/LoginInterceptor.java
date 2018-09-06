package com.gon.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;  
  
public class LoginInterceptor implements HandlerInterceptor {
	
	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		if(isLogin(request)){
			return true;
		}else {
			logger.warn("未登录");
			return false;
		}
	}
	
    public boolean isLogin(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	if(null != session && null !=session.getAttribute("_userinfo")) {
    		return true;
    	}
    	return false;
    }
}