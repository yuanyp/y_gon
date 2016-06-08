package com.gon.db;

import com.gon.db.util.DBUtil;

public class MySQLDBDialect implements DBDialect {

	/**
	 * sqlString->
	 * select count(1) from (
	 * sqlString
	 * )
	 */
    @Override
	public String getCountSql(String sql) {
		int sqlLength = sql.length();
		StringBuffer sb_sql = new StringBuffer(sqlLength + 30);
		sb_sql.append("select count(1) from (");
		sb_sql.append(sql.endsWith(";")?sql.substring(0,sqlLength-1):sql);
		sb_sql.append(") gj_temp_table");
		return sb_sql.toString();
	}

	private String getLimitString(String sql, int offset, int limit) {
		int sqlLength = sql.length();
		StringBuffer sb_sql = new StringBuffer(sqlLength + 30);
		sb_sql.append(sql.endsWith(";")?sql.substring(0,sqlLength-1):sql);
		sb_sql.append(" LIMIT ");
		sb_sql.append(offset);
		sb_sql.append(" ,");
		sb_sql.append(limit - offset);
		return sb_sql.toString();
	}

    @Override
    public String getPageSql(String sql, int pageIndex, int pageSize) {
        int i = DBUtil.calcuatePageLimit(pageIndex, pageSize);
        int j = DBUtil.calculatePageSelectRows(pageIndex, pageSize);
        return getLimitString(sql, i, j);
    }

}
