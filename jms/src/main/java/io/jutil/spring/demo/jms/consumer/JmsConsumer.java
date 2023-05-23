package io.jutil.spring.demo.jms.consumer;

import io.jutil.spring.demo.jms.client.JmsConnection;
import io.jutil.spring.demo.jms.client.JmsTopic;
import io.jutil.spring.demo.jms.exception.JmsConsumerException;
import io.jutil.spring.demo.jms.util.StringUtil;
import io.jutil.spring.demo.jms.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Jin Zheng
 * @since 2023-03-01
 */
@Slf4j
public class JmsConsumer {
	private final Map<JmsTopic, JmsConsumerThread> consumerMap = new ConcurrentHashMap<>();
	private final JmsConnection connection;
	private ExecutorService executorService;

	public JmsConsumer(JmsConnection connection) {
		this.connection = connection;
		this.init();
	}

	private void init() {
		var configList = connection.getAmqpProperties().getListeners();
		if (configList == null || configList.isEmpty()) {
			log.info("JMS listener list is empty");
			return;
		}

		for (var config : configList) {
			var listener = connection.getBean(config.getBeanName());
			var topicList = StringUtil.split(config.getTopic());
			for (var topic : topicList) {
				var amqpTopic = new JmsTopic(config.getJmsType(), topic);
				var consumer = new JmsConsumerThread(connection, listener, amqpTopic);
				consumerMap.put(amqpTopic, consumer);
			}
		}

		this.executorService = Executors.newFixedThreadPool(consumerMap.size());
	}

	public void subscribe() {
		var reconnectInterval = connection.getAmqpProperties().getConnection().getReconnectInterval();
		for (var entry : consumerMap.entrySet()) {
			var consumer = entry.getValue();
			executorService.submit(() -> {
				try {
					consumer.run();
				} catch (JmsConsumerException e) {
					if (!consumer.isClosed()) {
						WaitUtil.sleep(reconnectInterval);
						consumer.run();
					}
				} catch (Exception e) {
					log.error("Error, ", e);
				}
			});
		}
	}

	public void unsubscribe() {
		for (var entry : consumerMap.entrySet()) {
			try {
				entry.getValue().close();
				log.info("JMS unsubscribe successful: {}", entry.getKey());
			} catch (Exception e) {
				log.error("JMS consumer close error, ", e);
			}
		}
	}

}
