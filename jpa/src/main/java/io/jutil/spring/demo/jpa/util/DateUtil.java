package io.jutil.spring.demo.jpa.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * @author Jin Zheng
 * @since 2023-01-26
 */
public class DateUtil {
	private DateUtil() {
	}

	/**
	 * truncate to second
	 *
	 * @return
	 */
	public static Instant now() {
		return now(ChronoUnit.SECONDS);
	}

	public static Instant now(TemporalUnit unit) {
		var now = Instant.now();
		return now.truncatedTo(unit);
	}
}
