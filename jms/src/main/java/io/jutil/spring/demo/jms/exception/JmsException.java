package io.jutil.spring.demo.jms.exception;

import io.jutil.spring.demo.jms.client.JmsTopic;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2023-02-21
 */
@Getter
public class JmsException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private final JmsTopic topic;

    public JmsException(JmsTopic topic, Throwable cause) {
        super(cause);
        this.topic = topic;
    }

    public JmsTopic getTopic() {
        return topic;
    }
}
