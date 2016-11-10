package com.dock.core.utils;
import com.dock.core.entity.ModelMap;
public class ModelMapUtils {
    public ModelMapUtils() {
    }

    public static ModelMap toModelMap(Object object) {
        return (ModelMap)JsonUtils.parseJavaObject(JsonUtils.toJsonString(object), ModelMap.class);
    }

    public static <T> T parseJavaObject(ModelMap model, Class<T> cls) {
        return JsonUtils.parseJavaObject(JsonUtils.toJsonString(model), cls);
    }
}
