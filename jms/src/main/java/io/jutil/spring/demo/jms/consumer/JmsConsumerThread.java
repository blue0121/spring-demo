package io.jutil.spring.demo.jms.consumer;

import io.jutil.spring.demo.jms.client.JmsConnection;
import io.jutil.spring.demo.jms.client.JmsTopic;
import io.jutil.spring.demo.jms.exception.JmsConsumerException;
import io.jutil.spring.demo.jms.util.JmsUtil;
import io.jutil.spring.demo.jms.util.WaitUtil;
import jakarta.jms.JMSException;
import jakarta.jms.MessageConsumer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;

/**
 * @author Jin Zheng
 * @since 2023-04-30
 */
@Slf4j
public class JmsConsumerThread implements Runnable, Closeable {
	private static final int TIMEOUT = 5000;

	private final JmsConnection connection;
	private final JmsMessageListener listener;
	private final JmsTopic topic;

	private volatile boolean closed = false;
	private Session session;
	private MessageConsumer consumer;

	public JmsConsumerThread(JmsConnection connection, JmsMessageListener listener,
							 JmsTopic topic) {
		this.connection = connection;
		this.listener = listener;
		this.topic = topic;
	}

	@Override
	public void run() {
		try {
			this.closed = false;
			session = connection.getConnection().createSession(false, Session.CLIENT_ACKNOWLEDGE);
			var destination = JmsUtil.createDestination(session, topic);
			consumer = session.createConsumer(destination);
			log.info("JMS consumer listen, topic: {}", topic);
			this.messageConsume(consumer);
		} catch (JMSException e) {
			throw new JmsConsumerException(topic, e);
		}
	}

	private void messageConsume(MessageConsumer consumer) throws JMSException {
		while (!closed) {
			var message = consumer.receive(TIMEOUT);
			if (message == null) {
				continue;
			}
			log.info("JMS consume, topic: {}, message type: {}", topic,
					message.getClass().getSimpleName());
			if (!(message instanceof TextMessage)) {
				log.warn("Unsupported not text message");
				message.acknowledge();
				continue;
			}
			var text = ((TextMessage) message).getText();
			log.debug("JMS consume, message: {}", text);
			try {
				listener.onMessage(topic, text);
				message.acknowledge();
			} catch (Exception e) {
				log.error("JMS consumer listener error,", e);
				WaitUtil.sleep(TIMEOUT);
			}
		}
	}

	public boolean isClosed() {
		return this.closed;
	}

	public void close() {
		this.closed = true;
		JmsUtil.close(consumer);
		JmsUtil.close(session);
		log.info("JMS consumer wakeup, topic: {}", topic);
	}
}
