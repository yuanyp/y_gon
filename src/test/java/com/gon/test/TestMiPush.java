package com.gon.test;

import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestMiPush {

    public static void main(String[] args) {
        try {
            TestMiPush.sendMessage1();
            new URL("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String APP_SECRET_KEY = "guiFPesMi0vgbC3ASNuvKg==";
    public static String MY_PACKAGE_NAME = "com.manyit.mportal";
    public static String regId = "";

    private static void sendMessage() throws Exception {
        Constants.useOfficial();
        Sender sender = new Sender(APP_SECRET_KEY);
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        Message message = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(MY_PACKAGE_NAME)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        Result result = sender.send(message, regId, 3);
        System.out.println("Server response: " + "MessageId: " + result.getMessageId()
                + " ErrorCode: " + result.getErrorCode().getName() +  " ErrorCode: " + result.getErrorCode().getValue()
                + " Reason: " + result.getReason());
    }

    private static void sendMessage1() throws Exception {
        Constants.useSandbox();
        Sender sender = new Sender(APP_SECRET_KEY);
        String messagePayload = "This is a message";
        String title = "notification title";
        String description = "notification description";
        List<String> aliasList = new ArrayList<String>();
        aliasList.add("MANYITMobilePortal-test_admin");  //alias非空白，不能包含逗号, 长度小于128
//        aliasList.add("testAlias2");  //alias非空白，不能包含逗号, 长度小于128
//        aliasList.add("testAlias3");  //alias非空白，不能包含逗号, 长度小于128
        Message message = new Message.IOSBuilder()
                .description(description)
                .soundURL("default")    // 消息铃声
                .badge(1)               // 数字角标
                .category("action")     // 快速回复类别
                .extra("key", "value")  // 自定义键值对
                .extra("flow_control", "4000")   // 设置平滑推送, 推送速度4000每秒(qps=4000)
                .build();
        Message message1 = new Message.Builder()
                .title(title)
                .description(description).payload(messagePayload)
                .restrictedPackageName(MY_PACKAGE_NAME)
                .notifyType(1)     // 使用默认提示音提示
                .build();
        Result result = sender.sendToAlias(message, aliasList, 3); //根据aliasList, 发送消息到指定设备上
        System.out.println("Server response: " + "MessageId: " + result.getMessageId()
                + " ErrorCode: " + result.getErrorCode().getName() +  " ErrorCode: " + result.getErrorCode().getValue()
                + " Reason: " + result.getReason());
    }
}
