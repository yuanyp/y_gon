package com.gon.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 内容静页面态化 TODO <功能简述> <br/>
 * TODO <功能详细描述>
 * 
 * @author yuany
 */
//@Component
public class Task {

    //@Scheduled(cron = "0/5 * *  * * ? ")
    // 每5秒执行一次
    public void createContentHtml() {
        System.out.println("生成开始");
    }

    /*
     * @Scheduled(cron="0/5 * *  * * ? ") //每5秒执行一次 public void myTest1(){ System.out.println("进入测试1"); }
     */
}