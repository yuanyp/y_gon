package com.gon.controller;

import com.gon.entity.User;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BaseController {

    public static final String _USERINFO = "_userinfo";
    public static final String _CONFIG_NAME = "config_name";
    public static final String _CONFIG_PASS = "config_pass";
    public static final String _PRODUCES = "application/json";

    @ModelAttribute
    public void initPath(HttpServletRequest request, HttpServletResponse response, ModelMap model) {
        String path = request.getContextPath();
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
        model.addAttribute("path", path);
        model.addAttribute("basePath", basePath);
    }

    public User getSuperUser(String loginName) {
        User user = new User();
        user.setLoginName(loginName);
        user.setExpiresDate("2099-01-01 00:00:00");
        user.setCardInfo("");
        return user;
    }

    public Result getResult() {
        Result ret = new Result();
        ret.setCode(-1);
        return ret;
    }
}
