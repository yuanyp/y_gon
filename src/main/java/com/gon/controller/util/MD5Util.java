package com.gon.controller.util;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    private static Logger a = Logger.getLogger(MD5Util.class);
    protected static char[] hexDigits = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    public MD5Util() {
    }

    private static MessageDigest a() {
        try {
            MessageDigest messagedigest = MessageDigest.getInstance("MD5");
            return messagedigest;
        } catch (NoSuchAlgorithmException var1) {
            a.error("", var1);
            return null;
        }
    }

    public static String getFileMD5String(File file) throws IOException {
        MessageDigest messagedigest = a();
        FileInputStream in = (FileInputStream) new FileInputStream(file);
        byte[] buffer = new byte[10485760];
        boolean var4 = false;

        int len;
        while ((len = in.read(buffer)) > 0) {
            messagedigest.update(buffer, 0, len);
        }

        in.close();
        return a(messagedigest.digest());
    }

    public static String getMD5String(String s) throws UnsupportedEncodingException {
        return getMD5String(s.getBytes("utf-8"));
    }

    public static String getMD5String(byte[] bytes) {
        MessageDigest messagedigest = a();
        messagedigest.update(bytes);
        return a(messagedigest.digest());
    }

    private static String a(byte[] bytes) {
        return a(bytes, 0, bytes.length);
    }

    private static String a(byte[] bytes, int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;

        for (int l = m; l < k; ++l) {
            a(bytes[l], stringbuffer);
        }

        return stringbuffer.toString();
    }

    private static void a(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 240) >> 4];
        char c1 = hexDigits[bt & 15];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    public static boolean checkPassword(String password, String md5PwdStr) throws UnsupportedEncodingException {
        String s = getMD5String(password);
        return s.equals(md5PwdStr);
    }
}
