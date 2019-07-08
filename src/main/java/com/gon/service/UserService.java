package com.gon.service;

import com.gon.db.BaseJdbcDao;
import com.gon.entity.User;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BaseJdbcDao baseJdbcDao;

    public User findUser(String loginName, String password) {
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(password)) {
            return null;
        }
        String sql = "select * from yd_user where login_name= ? and pass_word=?";
        User user = baseJdbcDao.queryForObject(sql, User.class, loginName, password);
        return user;
    }

    public User findUserByCard(String loginName, String cardInfo) {
        if (StringUtils.isBlank(loginName) || StringUtils.isBlank(cardInfo)) {
            return null;
        }
        String sql = "select * from yd_user where login_name= ? and card_info=?";
        User user = baseJdbcDao.queryForObject(sql, User.class, loginName, cardInfo);
        return user;
    }

    public User findUser(String loginName) {
        if (StringUtils.isBlank(loginName)) {
            return null;
        }
        String sql = "select * from yd_user where login_name= ?";
        User user = baseJdbcDao.queryForObject(sql, User.class, loginName);
        return user;
    }
}
