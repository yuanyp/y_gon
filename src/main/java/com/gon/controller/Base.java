package com.gon.controller;

import com.xnx3.microsoft.*;
import com.xnx3.robot.Robot;

/**
 * Created by yuanyp on 2018/8/21.
 */
public class Base {

    public static Com com;
    public static Window window;
    public static Mouse mouse;
    public static Press press;
    public static Color color;
    public static Robot robot;
    public static FindPic findPic;
    public static FindStr findStr;
    public static com.xnx3.microsoft.File file;

    public static String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();

    static{
        com = new Com();
        window = new Window(com.getActiveXComponent());    //窗口操作类
        mouse = new Mouse(com.getActiveXComponent());   //鼠标模拟操作类
        press = new Press(com.getActiveXComponent());   //键盘模拟操作类
        color = new Color(com.getActiveXComponent());   //颜色相关的取色、判断类
        findPic = new FindPic(com.getActiveXComponent());
        findStr = new FindStr(com.getActiveXComponent());
        file = new com.xnx3.microsoft.File(com.getActiveXComponent());
        robot = new Robot();
    }

}
