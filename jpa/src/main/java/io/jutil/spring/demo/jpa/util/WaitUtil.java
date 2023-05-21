package io.jutil.spring.demo.jpa.util;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-03-05
 */
@Slf4j
public class WaitUtil {
    private WaitUtil() {
    }

    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.error("Sleep Interrupted.", e);
        }
    }

}
