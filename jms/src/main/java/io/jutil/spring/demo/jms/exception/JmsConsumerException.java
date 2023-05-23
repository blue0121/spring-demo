package io.jutil.spring.demo.jms.exception;

import io.jutil.spring.demo.jms.client.JmsTopic;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2023-02-21
 */
@Getter
public class JmsConsumerException extends JmsException {
    private static final long serialVersionUID = 1L;

    public JmsConsumerException(JmsTopic topic, Throwable cause) {
        super(topic, cause);
    }

}
