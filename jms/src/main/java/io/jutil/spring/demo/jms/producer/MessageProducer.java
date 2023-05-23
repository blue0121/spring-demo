package io.jutil.spring.demo.jms.producer;

import io.jutil.spring.demo.jms.util.WaitUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Jin Zheng
 * @since 2023-05-23
 */
@Slf4j
@Component
public class MessageProducer implements InitializingBean, Runnable, DisposableBean {
	@Autowired
	JmsProducer producer;

	private volatile boolean closed;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.closed = false;
		var thread = new Thread(this);
		thread.start();
		log.info(">>>> MessageProducer thread start");
	}

	@Override
	public void run() {
		while (!closed) {
			try {
				producer.sendTopic("command/vehicle", "vehicle message");
				producer.sendTopic("command/device", "device message");
			} catch (Exception e) {
				log.error("Error, ", e);
			}
			WaitUtil.sleep(10_000);
		}
	}

	@Override
	public void destroy() throws Exception {
		this.closed = true;
	}
}
