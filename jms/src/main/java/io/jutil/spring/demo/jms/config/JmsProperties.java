package io.jutil.spring.demo.jms.config;

import io.jutil.spring.demo.jms.client.JmsType;
import io.jutil.spring.demo.jms.util.AssertUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-05-23
 */
@Getter
@Setter
@NoArgsConstructor
@ConfigurationProperties("jms")
public class JmsProperties {
    private Connection connection;
    private List<Listener> listeners;

    public void check() {
        AssertUtil.notNull(connection, "JMS connection");
        AssertUtil.notEmpty(connection.getServer(), "JMS connection server");

        if (listeners != null && !listeners.isEmpty()) {
            for (var listener : listeners) {
                listener.jmsType = JmsType.getType(listener.type);
                AssertUtil.notNull(listener.jmsType, "JMS Type");
                AssertUtil.notEmpty(listener.topic, "Topic");
                AssertUtil.notEmpty(listener.beanName, "Bean Name");
            }
        }
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Connection {
        private String server;
        private String vhost;
        private int reconnectInterval;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Listener {
        private String id;
        private String type;
        private String topic;
        private String beanName;

        private JmsType jmsType;
    }
}
