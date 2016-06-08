package com.gon.db;

public interface DBDialect {

    public String getPageSql(String sql,int pageIndex,int pageSize);
    
    public String getCountSql(String sql);
}
