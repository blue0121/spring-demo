package io.jutil.spring.demo.jpa.util;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.filter.Filter;

import java.nio.charset.StandardCharsets;

/**
 * @author Jin Zheng
 * @since 2023-01-26
 */
public class JsonUtil {
	private static JSONWriter.Feature[] writer = new JSONWriter.Feature[] {
			JSONWriter.Feature.WriteClassName,
			JSONWriter.Feature.WriteEnumUsingToString
	};

	private static JSONWriter.Feature[] output = new JSONWriter.Feature[] {
			JSONWriter.Feature.WriteEnumUsingToString
	};

	public static Filter AUTO_TYPE_FILTER = JSONReader.autoTypeFilter(
			"io.jutil.spring.demo.jpa");

	private JsonUtil() {
	}

	public static byte[] toBytes(Object object) {
		if (object == null) {
			return new byte[0];
		}

		if (object instanceof byte[]) {
			return (byte[]) object;
		}

		if (object instanceof CharSequence) {
			return object.toString().getBytes(StandardCharsets.UTF_8);
		}

		return JSON.toJSONBytes(object, writer);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromBytes(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		return (T) JSON.parseObject(bytes, Object.class, AUTO_TYPE_FILTER);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromBytes(byte[] bytes, Class<T> clazz) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}

		if (clazz == byte[].class) {
			return (T) bytes;
		}

		if (clazz == String.class) {
			return (T) new String(bytes, StandardCharsets.UTF_8);
		}

		return JSON.parseObject(bytes, clazz, AUTO_TYPE_FILTER);
	}

	public static String output(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof byte[]) {
			return String.format("{%d byte array}", ((byte[]) object).length);
		}

		if (object instanceof CharSequence) {
			return object.toString();
		}

		return JSON.toJSONString(object, output);
	}

	public static String toString(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof byte[]) {
			return String.format("{%d byte array}", ((byte[]) object).length);
		}

		if (object instanceof CharSequence) {
			return object.toString();
		}

		return JSON.toJSONString(object, writer);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromString(String json) {
		if (json == null || json.isEmpty()) {
			return null;
		}

		return (T) JSON.parseObject(json, Object.class, AUTO_TYPE_FILTER);
	}

	@SuppressWarnings("unchecked")
	public static <T> T fromString(String json, Class<T> clazz) {
		if (json == null || json.isEmpty()) {
			return null;
		}

		if (clazz == String.class) {
			return (T) json;
		}

		return JSON.parseObject(json, clazz, AUTO_TYPE_FILTER);
	}
}
