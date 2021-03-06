package com.dock.core.utils;

import com.alibaba.fastjson.JSON;

public class JsonUtils {
    public JsonUtils() {
    }

    public static String toJsonString(Object object) {
        return JSON.toJSONString(object);
    }

    public static <T> T parseJavaObject(String json, Class<T> cls) {
        return JSON.parseObject(json, cls);
    }
}