package io.jutil.spring.demo.jms.util;

import io.jutil.spring.demo.jms.client.JmsTopic;
import io.jutil.spring.demo.jms.client.JmsType;
import jakarta.jms.Destination;
import jakarta.jms.JMSException;
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Jin Zheng
 * @since 2023-04-30
 */
@Slf4j
public class JmsUtil {

	public static void close(AutoCloseable closeable) {
		if (closeable != null) {
			try {
				closeable.close();
			}
			catch (Exception e) {
				log.error("JMS close error, ", e);
			}
		}
	}

	public static Destination createDestination(Session session, JmsTopic topic) throws JMSException {
		if (topic.getType() == null || topic.getType() == JmsType.QUEUE) {
			return session.createQueue(topic.getTopic());
		}

		return session.createTopic(topic.getTopic());
	}

}
