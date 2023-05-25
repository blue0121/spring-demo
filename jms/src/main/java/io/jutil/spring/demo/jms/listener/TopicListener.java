package io.jutil.spring.demo.jms.listener;

import io.jutil.spring.demo.jms.client.JmsTopic;
import io.jutil.spring.demo.jms.consumer.JmsMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2023-05-23
 */
@Slf4j
@Component
public class TopicListener implements JmsMessageListener {

    @Override
    public void onMessage(JmsTopic topic, String message) {
        log.info("Topic: {}, message: {}", topic, message);
    }
}
