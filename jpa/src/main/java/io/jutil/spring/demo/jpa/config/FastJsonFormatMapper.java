package io.jutil.spring.demo.jpa.config;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import io.jutil.spring.demo.jpa.util.JsonUtil;
import io.jutil.spring.demo.jpa.util.SqlUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.type.FormatMapper;
import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.JavaType;

/**
 * @author Jin Zheng
 * @since 2023-02-10
 */
@Slf4j
public class FastJsonFormatMapper implements FormatMapper {
    @Override
    @SuppressWarnings("unchecked")
    public <T> T fromString(CharSequence charSequence, JavaType<T> javaType, WrapperOptions options) {
        var json = SqlUtil.getJsonString(charSequence.toString());
        var clazz = javaType.getJavaTypeClass();
        if (clazz == JSONObject.class) {
            return (T) JSON.parseObject(json);
        } else if (clazz == JSONArray.class) {
            return (T) JSON.parseArray(json);
        }
        return JsonUtil.fromString(json, clazz);
    }

    @Override
    public <T> String toString(T value, JavaType<T> javaType, WrapperOptions options) {
        return JsonUtil.toString(value);
    }
}
