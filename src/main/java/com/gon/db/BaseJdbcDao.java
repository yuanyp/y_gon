package com.gon.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class BaseJdbcDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SuppressWarnings("unchecked")
    public <T> List<T> queryForList(String sql, Class<T> tClass, Object... args) {
        RowMapper<T> rowMapper = null;
        if (Map.class.isAssignableFrom(tClass)) {
            rowMapper = (RowMapper<T>) new ColumnMapRowMapper();
        } else if (String.class.equals(tClass) || Integer.class.equals(tClass) || Long.class.equals(tClass)) {
            rowMapper = new SingleColumnRowMapper<T>(tClass);
        } else {
            rowMapper = new BeanPropertyRowMapper<T>(tClass);
        }
        List<T> list = jdbcTemplate.query(sql, rowMapper, args);
        return list;
    }

    public <T> T queryForObject(String sql, Class<T> tClass, Object... args) {
        List<T> list = queryForList(sql, tClass, args);
        return list == null || list.isEmpty() ? null : list.get(0);
    }
}