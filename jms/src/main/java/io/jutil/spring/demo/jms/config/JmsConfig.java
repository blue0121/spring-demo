package io.jutil.spring.demo.jms.config;

import io.jutil.spring.demo.jms.client.JmsConnection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Jin Zheng
 * @since 2023-05-23
 */
@Configuration
@EnableConfigurationProperties(JmsProperties.class)
@ConditionalOnProperty(prefix = "jms", name = "enabled", havingValue = "true")
public class JmsConfig {

	@Bean
	public JmsConnection jmsConnection(JmsProperties prop) {
		prop.check();
		return new JmsConnection(prop);
	}
}
