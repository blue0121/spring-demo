package io.jutil.spring.demo.jms.util;

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
