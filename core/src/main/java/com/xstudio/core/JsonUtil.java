package com.xstudio.core;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DateFormat;

/**
 * json工具类
 *
 * @author xiaobiao
 * @version 2020/09/14
 */
public class JsonUtil {
    private static final Gson GSON = new GsonBuilder()
            .setDateFormat(DateFormat.MEDIUM)
            .disableHtmlEscaping()
            .create();

    private JsonUtil() {
    }

    /**
     * 对象转字符串
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toJsonString(Object obj) {
        return GSON.toJson(obj);
    }


    public static <T> T toObject(String json, Class<T> valueType) {
        return GSON.fromJson(json, valueType);
    }
}
