package com.gon.db.util;

public class DBUtil {

    public static int calculatePageSelectRows(int pageIndex,int pageSize){
        return pageIndex * pageSize;
    }
    
    public static int calcuatePageLimit(int pageIndex,int pageSize){
        return (pageIndex * pageSize) - pageSize;
    }
    
    public static int getTotalPages(int records,int pageSize){
        if(records % pageSize == 0){
            return records / pageSize;
        }else{
            return records / pageSize + 1;
        }
    }
}
