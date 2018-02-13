package com.ibm.sova.jms.actuator.mq.health;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health.Builder;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsOperations;
import org.springframework.transaction.annotation.Transactional;


@Configuration
public class MQueueCheckHealthIndicator extends AbstractHealthIndicator {
	private static final Logger log = LoggerFactory.getLogger(MQueueCheckHealthIndicator.class);
	@Autowired
	private JmsOperations jmsOperations;

	@Transactional(value = "jmsTransactionManager")
	@Override
	protected void doHealthCheck(Builder builder) throws JMSException {
		builder.up();
		try {
			jmsOperations.convertAndSend("SOVA.INPUT", "hello world");

			jmsOperations.convertAndSend("SOVA.INPUT", "transaction test");

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			builder.down();
		}
	}

}