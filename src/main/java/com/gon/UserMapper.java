package com.gon;

import org.activiti.engine.identity.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {
    @Select("SELECT * FROM T_USER WHERE userid = #{userId}")
    User getUser(@Param("userId") String userId);
}
