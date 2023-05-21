package io.jutil.spring.demo.jpa.util;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;

/**
 * @author Jin Zheng
 * @since 2023-05-09
 */
public class JsonPathUtil {
    private JsonPathUtil() {
    }

    private static Object get(Object object, String path) {
        var jsonPath = JSONPath.of(path);

        return jsonPath.eval(object);
    }

    public static JSONObject getJsonObject(Object object, String path) {
        return (JSONObject) get(object, path);
    }

    public static JSONArray getJsonArray(Object object, String path) {
        return (JSONArray) get(object, path);
    }

    public static String getString(Object object, String path) {
        return (String) get(object, path);
    }
}
