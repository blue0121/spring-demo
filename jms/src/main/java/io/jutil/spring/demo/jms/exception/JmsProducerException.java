package io.jutil.spring.demo.jms.exception;

import io.jutil.spring.demo.jms.client.JmsTopic;
import lombok.Getter;

/**
 * @author Jin Zheng
 * @since 2023-02-21
 */
@Getter
public class JmsProducerException extends JmsException {
    private static final long serialVersionUID = 1L;

    public JmsProducerException(JmsTopic topic, Throwable cause) {
        super(topic, cause);
    }

}
