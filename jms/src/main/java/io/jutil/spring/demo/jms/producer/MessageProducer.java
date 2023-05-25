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
	}

	@Override
	public void run() {
		log.info(">>>> MessageProducer thread start");
		int count = 0;
		while (!closed && count < 1) {
			try {
				producer.sendQueue("command/queue", "queue message " + count);
				producer.sendTopic("command/topic", "topic message " + count);
			} catch (Exception e) {
				log.error("Error, ", e);
			}
			WaitUtil.sleep(1_000);
			count++;
		}
		log.info(">>>> MessageProducer thread end");
	}

	@Override
	public void destroy() throws Exception {
		this.closed = true;
	}
}
