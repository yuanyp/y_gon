
package com.gon.controller.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

public class DesUtil {
    public static final Log log = LogFactory.getLog(DesUtil.class);
    public static final String EncryptionKey = "NCBCXKEY";
    public static final String EncryptCommonKey = "MANYITKEY";

    public static String encryption(String plainData, String secretKey) throws Exception {
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(1, a(secretKey));
        } catch (NoSuchAlgorithmException var6) {
            log.error("", var6);
        } catch (NoSuchPaddingException var7) {
            log.error("", var7);
        } catch (InvalidKeyException var8) {
            log.error("", var8);
        }
        try {
            byte[] buf = cipher.doFinal(plainData.getBytes());
            return StringUtil.BASE64Encoder(buf);
        } catch (IllegalBlockSizeException var4) {
            log.error("", var4);
            throw new Exception("IllegalBlockSizeException", var4);
        } catch (BadPaddingException var5) {
            log.error("", var5);
            throw new Exception("BadPaddingException", var5);
        }
    }

    public static String decryption(String secretData, String secretKey) throws Exception {
        Cipher cipher = null;

        try {
            cipher = Cipher.getInstance("DES");
            cipher.init(2, a(secretKey));
        } catch (NoSuchAlgorithmException var6) {
            log.error("", var6);
            throw new Exception("NoSuchAlgorithmException", var6);
        } catch (NoSuchPaddingException var7) {
            log.error("", var7);
            throw new Exception("NoSuchPaddingException", var7);
        } catch (InvalidKeyException var8) {
            log.error("", var8);
            throw new Exception("InvalidKeyException", var8);
        }

        try {
            byte[] buf = cipher.doFinal(StringUtil.BASE64Decoder(secretData));
            return new String(buf, "UTF-8");
        } catch (IllegalBlockSizeException var4) {
            log.error("", var4);
            throw new Exception("IllegalBlockSizeException", var4);
        } catch (BadPaddingException var5) {
            log.error("", var5);
            throw new Exception("BadPaddingException", var5);
        }
    }

    private static SecretKey a(String secretKey) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        DESKeySpec keySpec = new DESKeySpec(secretKey.getBytes());
        keyFactory.generateSecret(keySpec);
        return keyFactory.generateSecret(keySpec);
    }

    public static void main(String[] args) {
        String b = null;

        try {
            String a = StringUtil.getRandomString(8);
            System.out.println(a);
            b = encryption("yuan19920803pu", a);
            Map<String, Object> map = new HashMap();
            map.remove("aa");
        } catch (Exception var4) {
            var4.printStackTrace();
        }

        System.out.println(b);
    }
}
