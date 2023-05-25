package io.jutil.spring.demo.jms.client;

import io.jutil.spring.demo.jms.config.JmsProperties;
import io.jutil.spring.demo.jms.consumer.JmsConsumer;
import io.jutil.spring.demo.jms.consumer.JmsMessageListener;
import io.jutil.spring.demo.jms.util.WaitUtil;
import jakarta.jms.Connection;
import jakarta.jms.ExceptionListener;
import jakarta.jms.JMSException;
import lombok.extern.slf4j.Slf4j;
import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.text.MessageFormat;

/**
 * @author Jin Zheng
 * @since 2023-03-01
 */
@Slf4j
public class JmsConnection implements ExceptionListener, InitializingBean,
		DisposableBean, BeanFactoryAware {
	private static final String TPL_URI = "{0}{1}";

	private final JmsProperties prop;
	private JmsConnectionFactory connectionFactory;
	private volatile Connection connection;

	private JmsConsumer jmsConsumer;

	private BeanFactory beanFactory;

	private volatile boolean closed = false;

	public JmsConnection(JmsProperties prop) {
		this.prop = prop;
	}

	private void init() {
		var connProp = prop.getConnection();
		var uri = MessageFormat.format(TPL_URI, connProp.getServer(), connProp.getVhost());
		log.info("JMS uri: {}", uri);

		this.connectionFactory = new JmsConnectionFactory(uri);
		this.jmsConsumer = new JmsConsumer(this);
		this.connect();
	}

	private void connect() {
		var interval = prop.getConnection().getReconnectInterval();
		while (true) {
			try {
				if (this.connection != null) {
					this.connection.close();
				}
				this.connection = connectionFactory.createConnection();
				this.connection.setExceptionListener(this);
				connection.start();
				log.info("JMS reconnected successfully");
				jmsConsumer.subscribe();
				break;
			} catch (JMSException ex) {
				log.error("JMS reconnect failed,", ex);
				WaitUtil.sleep(interval);
			}
		}
	}

	@Override
	public void onException(JMSException e) {
		if (closed) {
			return;
		}

		log.error("JMS connection error, ", e);
		jmsConsumer.unsubscribe();
		this.connect();
	}

	@Override
	public void destroy() throws Exception {
		closed = true;
		jmsConsumer.unsubscribe();
		try {
			if (this.connection != null) {
				this.connection.close();
			}
			log.info("JMS connection destroy successful.");
		} catch (Exception e) {
			log.error("JMS connection destroy error.", e);
		}
	}

	public Connection getConnection() {
		return connection;
	}

	public JmsProperties getAmqpProperties() {
		return this.prop;
	}

	public JmsMessageListener getBean(String name) {
		return beanFactory.getBean(name, JmsMessageListener.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

	@Override
	public void afterPropertiesSet() {
		this.init();
	}
}
