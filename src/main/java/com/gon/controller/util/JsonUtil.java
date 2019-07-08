package com.gon.controller.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public abstract class JsonUtil {
    public JsonUtil() {
    }

    public static <T> T jsonStr2Bean(String jsonString, Class<T> classOfT) {
        return JSONObject.parseObject(jsonString, classOfT);
    }

    public static <T> T jsonStr2Bean(String jsonString, Type t) {
        return JSONObject.parseObject(jsonString, t, new Feature[0]);
    }

    public static String bean2JosnStr(Object javaObj) {
        return JSONObject.toJSONString(javaObj, new SerializerFeature[]{SerializerFeature.WriteMapNullValue, SerializerFeature.WriteNullListAsEmpty, SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.DisableCircularReferenceDetect});
    }

    public static List json2List(Object javaObj) {
        return javaObj instanceof List ? (List) javaObj : (List) jsonStr2Bean(StringUtil.convertToString(javaObj), List.class);
    }

    public static Map json2Map(Object javaObj) {
        return javaObj instanceof Map ? (Map) javaObj : (Map) jsonStr2Bean(StringUtil.convertToString(javaObj), Map.class);
    }

    public static String map2jsonStr(Map map) {
        return bean2JosnStr(map);
    }

    public static void main(String[] args) {
        Map<String, Object> a1 = new HashMap();
        a1.put("a", 1);
        a1.put("b", (Object) null);
        String astr = bean2JosnStr(a1);
        System.out.println(astr);
        Map b = json2Map(astr);
        System.out.println(b);
        String str = "['a','b']";
        String[] arr = (String[]) jsonStr2Bean(str, String[].class);
        String[] var6 = arr;
        int i = arr.length;

        for (i = 0; i < i; ++i) {
            String s = var6[i];
            System.out.println("arr item " + s);
        }

        str = "[{'a':1},{'b':'aaa'}]";
        List<Map<String, Object>> list = json2List(str);
        Iterator var12 = list.iterator();

        while (var12.hasNext()) {
            Map<String, Object> map = (Map) var12.next();
            System.out.println("map " + map);
        }

        for (i = 0; i < list.size(); ++i) {
            String a = StringUtil.convertToString(list.get(i));
            Map m = json2Map(a);
            System.out.println(m);
        }

        str = "[[{'a':1},{'b':'aaa'}],[{'a':1},{'b':'aaa'}]]";
        List list1 = json2List(str);
        str = bean2JosnStr(list1);
        System.out.println(str);

        for (i = 0; i < list1.size(); ++i) {
            List list2 = (List) list1.get(i);
            Map map = (Map) list2.get(0);
            System.out.println(map);
        }

        System.out.println(list);
    }
}
