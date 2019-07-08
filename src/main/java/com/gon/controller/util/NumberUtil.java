
package com.gon.controller.util;

public class NumberUtil {
    public NumberUtil() {
    }

    public static Integer convertToInt(Object obj) {
        try {
            Double a = convertToDouble(obj);
            return a.intValue();
        } catch (Exception var2) {
            return 0;
        }
    }

    public static long convertToLong(Object obj) {
        try {
            String str = String.valueOf(obj);
            boolean isAllZero = true;

            for (Integer iIndex = str.indexOf("."); iIndex > 0 && iIndex < str.length() - 1; iIndex = iIndex + 1) {
                String compareStr = str.substring(iIndex + 1, iIndex + 1 + 1);
                if (!"0".equals(compareStr)) {
                    isAllZero = false;
                    break;
                }
            }

            if (isAllZero && str.indexOf(".") > 0) {
                str = str.substring(0, str.indexOf("."));
            }

            return Long.parseLong(str);
        } catch (Exception var6) {
            return 0L;
        }
    }

    public static Double convertToDouble(Object obj) {
        try {
            return Double.parseDouble(obj.toString());
        } catch (Exception var2) {
            return 0.0D;
        }
    }

    public static Boolean isIntegerType(Object obj) {
        if (obj.equals((Object) null)) {
            return false;
        } else {
            try {
                Integer.parseInt("" + obj);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }

    public static Boolean isDoubleType(Object obj) {
        if (obj.equals((Object) null)) {
            return false;
        } else {
            try {
                Double.parseDouble("" + obj);
                return true;
            } catch (Exception var2) {
                return false;
            }
        }
    }
}
