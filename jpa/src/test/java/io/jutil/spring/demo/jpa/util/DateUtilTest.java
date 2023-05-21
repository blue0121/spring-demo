package io.jutil.spring.demo.jpa.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.temporal.ChronoUnit;

/**
 * @author Jin Zheng
 * @since 2023-03-22
 */
public class DateUtilTest {

    @Test
    public void testNow() {
        var now = DateUtil.now(ChronoUnit.SECONDS);
        System.out.println(now);

        var regex = "^\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}Z$";
        Assertions.assertTrue(now.toString().matches(regex));
    }

}
