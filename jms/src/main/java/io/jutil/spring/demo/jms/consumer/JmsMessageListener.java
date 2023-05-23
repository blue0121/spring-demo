package io.jutil.spring.demo.jms.consumer;

import io.jutil.spring.demo.jms.client.JmsTopic;

/**
 * @author Jin Zheng
 * @since 2023-03-02
 */
public interface JmsMessageListener {

    void onMessage(JmsTopic topic, String message);

}
