package io.jutil.spring.demo.jpa.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-01-13
 */
public class StringUtil {
	public static final String STR_SPLIT = "[,;]";

	private StringUtil() {
	}

	public static String getString(String path) {
		AssertUtil.notEmpty(path, "Path");

		if (FileUtil.isClassPath(path)) {
			var classpath = FileUtil.extractClassPath(path);
			try (var is = StringUtil.class.getResourceAsStream(classpath)) {
				return new String(is.readAllBytes(), StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new UncheckedIOException(e);
			}
		}

		try (var is = new FileInputStream(path)) {
			return new String(is.readAllBytes(), StandardCharsets.UTF_8);
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	public static String repeat(String item, int times, String separator) {
		AssertUtil.notEmpty(item, "Item");
		AssertUtil.positive(times, "Times");

		String sep = separator == null ? "" : separator;
		StringBuilder concat = new StringBuilder();
		for (int i = 0; i < times; i++) {
			concat.append(item).append(sep);
		}
		if (concat.length() > sep.length()) {
			concat.delete(concat.length() - sep.length(), concat.length());
		}
		return concat.toString();
	}

	public static List<String> split(String str) {
		List<String> list = new ArrayList<>();
		if (str == null || str.isEmpty()) {
			return list;
		}

		for (String s : str.split(STR_SPLIT)) {
			s = s.trim();
			if (s.isEmpty()) {
				continue;
			}

			list.add(s);
		}
		return list;
	}

}
