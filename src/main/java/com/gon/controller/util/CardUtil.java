package com.gon.controller.util;

/**
 * 卡密生成工具
 */
public class CardUtil {

    /**
     * 根据用户信息生成卡密
     * 登录名+转账记录ID
     *
     * @return
     */
    public static String generateCard(String loginName, String payUUID) throws Exception {
        return DesUtil.encryption(loginName + "_" + payUUID, DesUtil.EncryptionKey);
    }
}
