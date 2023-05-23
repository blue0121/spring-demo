package io.jutil.spring.demo.jms.client;

import lombok.Getter;

import java.util.Objects;

/**
 * @author Jin Zheng
 * @since 2023-03-02
 */
@Getter
public class JmsTopic {
	private final JmsType type;
    private final String topic;

    public JmsTopic(JmsType type, String topic) {
        this.type = type;
        this.topic = topic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        JmsTopic jmsTopic = (JmsTopic) o;
        return type == jmsTopic.type && topic.equals(jmsTopic.topic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, topic);
    }

    @Override
    public String toString() {
        return String.format("JMS{type: %s, topic: %s}", type, topic);
    }
}
