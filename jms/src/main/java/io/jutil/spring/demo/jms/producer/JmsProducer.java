package io.jutil.spring.demo.jms.producer;

import io.jutil.spring.demo.jms.client.JmsConnection;
import io.jutil.spring.demo.jms.client.JmsTopic;
import io.jutil.spring.demo.jms.client.JmsType;
import io.jutil.spring.demo.jms.exception.JmsProducerException;
import io.jutil.spring.demo.jms.util.AssertUtil;
import io.jutil.spring.demo.jms.util.JmsUtil;
import jakarta.jms.DeliveryMode;
import jakarta.jms.JMSException;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Jin Zheng
 * @since 2023-03-01
 */
@Slf4j
@Component
public class JmsProducer {
	@Autowired(required = false)
	JmsConnection connection;

	public void sendQueue(String topic, String message) {
		this.send(new JmsTopic(JmsType.QUEUE, topic), List.of(message));
	}

	public void sendTopic(String topic, String message) {
		this.send(new JmsTopic(JmsType.TOPIC, topic), List.of(message));
	}

	public void send(JmsTopic topic, List<String> messageList) {
		if (connection == null) {
			log.warn("AMQP is disabled");
			return;
		}

		AssertUtil.notNull(topic, "Topic");
		AssertUtil.notEmpty(messageList, "Message list");
		Session session = null;
		MessageProducer producer = null;
		try {
			session = connection.getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
			var destination = JmsUtil.createDestination(session, topic);
			producer = session.createProducer(destination);
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			for (var object : messageList) {
				var message = session.createTextMessage(object);
				producer.send(message);
				if (log.isDebugEnabled()) {
					log.debug("AMQP produce, topic: {}, message: {}", topic, object);
				} else {
					log.info("AMQP produce, topic: {}", topic);
				}
			}
		}
		catch (JMSException e) {
			throw new JmsProducerException(topic, e);
		} finally {
			JmsUtil.close(producer);
			JmsUtil.close(session);
		}
	}

}
