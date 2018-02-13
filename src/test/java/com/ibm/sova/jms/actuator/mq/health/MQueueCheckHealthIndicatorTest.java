package com.ibm.sova.jms.actuator.mq.health;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibm.sova.jms.actuator.mq.health.MQueueCheckHealthIndicator;
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MQueueCheckHealthIndicatorTest {
	private static final Logger log = LoggerFactory.getLogger(MQueueCheckHealthIndicatorTest.class);
	@Autowired
    private MQueueCheckHealthIndicator mQueueCheckHealthIndicator;
	 @Test
	    public void contextLoads() {
	    }
	@Test
	public void testDoHealthCheckBuilder() {
		log.info(mQueueCheckHealthIndicator.health().getStatus().getDescription());
		log.info(mQueueCheckHealthIndicator.health().toString());
		log.info(mQueueCheckHealthIndicator.health().getStatus().getCode());
	}

}
