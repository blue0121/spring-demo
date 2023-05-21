package io.jutil.spring.demo.jpa.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 1.0 2021-12-30
 */
public class StringUtilTest {
    public StringUtilTest() {
    }

    @Test
    public void testRepeat() {
        String repeat1 = StringUtil.repeat("?", 4, ",");
        Assertions.assertEquals("?,?,?,?", repeat1);

        String repeat2 = StringUtil.repeat("?", 4, null);
        Assertions.assertEquals("????", repeat2);

        String repeat3 = StringUtil.repeat("?", 1, null);
        Assertions.assertEquals("?", repeat3);
    }

    @Test
    public void testSplit() {
        var s1 = StringUtil.split("1,2");
        Assertions.assertEquals(List.of("1", "2"), s1);

        var s2 = StringUtil.split("1;2");
        Assertions.assertEquals(List.of("1", "2"), s2);

        var s3 = StringUtil.split("1,2;3");
        Assertions.assertEquals(List.of("1", "2", "3"), s3);

    }

}
