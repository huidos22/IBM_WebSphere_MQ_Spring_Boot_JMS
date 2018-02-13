package com.ibm.sova.jms.actuator.mq.metric;

import static org.junit.Assert.*;

import java.util.Iterator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ibm.mq.jms.MQQueueConnectionFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MQueueMetricsTest {
	private static final Logger log = LoggerFactory.getLogger(MQueueMetricsTest.class);
	@Autowired
    private MQueueMetrics mQueueMetrics;
	
	@Test
	public void contextLoads() {
	}

	@Test
	public void testMetrics() {
		for (Iterator<Metric<?>> iterator = mQueueMetrics.metrics().iterator(); iterator.hasNext();) {
			Metric<Number> type = (Metric<Number>) iterator.next();
			log.info(type.getName());
			
		}
		
	}

}
