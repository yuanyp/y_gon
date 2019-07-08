package com.gon.controller;


import com.gon.controller.util.AppContextServlet;
import com.gon.controller.util.DateTimeUtil;
import com.gon.entity.User;
import com.gon.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Controller
public class IndexController extends BaseController {

    private Logger logger = Logger.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @RequestMapping(value = "login", produces = _PRODUCES)
    @ResponseBody
    public Result login(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        Result ret = getResult();
        User user = userService.findUser(name, pass);
        if (null != user) {
            ret.setSuccess(true);
        } else {
            String config_name = AppContextServlet.getProperty(_CONFIG_NAME);
            String config_pass = AppContextServlet.getProperty(_CONFIG_PASS);
            if (StringUtils.equalsIgnoreCase(name, config_name) && StringUtils.equalsIgnoreCase(pass, config_pass)) {
                ret.setSuccess(true);
                user = getSuperUser(config_name);
            } else {
                ret.setError_msg("账号密码错误");
            }
        }
        if (ret.success()) {
            user.clearSensitiveInfo();
            ret.setField(_USERINFO, user);
            request.getSession().setAttribute(_USERINFO, user);
        }
        return ret;
    }

    @RequestMapping(value = "logout", produces = _PRODUCES)
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.getSession().invalidate();
        ModelAndView mv = new ModelAndView();
        mv.setViewName("redirect:/index.jsp");
        return mv;
    }

    /**
     * 校验用户卡密有效期
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "api/check_user_card", produces = _PRODUCES)
    @ResponseBody
    public Result checkUserCard(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("login_name");
        String cardInfo = request.getParameter("card_info");
        if(StringUtils.isBlank(name) || StringUtils.isBlank(cardInfo)){
            return null;
        }
        Result ret = getResult();
        User user = userService.findUserByCard(name,cardInfo);
        if (null != user) {
            String expiresDate = user.getExpiresDate();
            Date date = DateTimeUtil.parseToDate(expiresDate, DateTimeUtil.FMT_yyyyMMddHHmmss);
            int a = DateTimeUtil.compareTwoDate(date, DateTimeUtil.getSystemDate());
            if (a == -1 || a == 0) {//有效期必须大于等于当前时间
                ret.setSuccess(true);
                ret.setField("expires_date", user.getExpiresDate());
            }
        }
        return ret;
    }

    @RequestMapping(value = "index")
    public ModelAndView main(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("/index/main.jsp", "command", "LOGIN SUCCESS");
        return mv;
    }
}
