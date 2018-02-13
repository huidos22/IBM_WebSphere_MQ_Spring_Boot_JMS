package com.ibm.sova.jms.actuator.mq.metric;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.PublicMetrics;
import org.springframework.boot.actuate.metrics.Metric;
import org.springframework.context.annotation.Configuration;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.sova.jms.config.JmsConfig;
@Configuration
public class MQueueMetrics implements PublicMetrics {
	private static final Logger log = LoggerFactory.getLogger(MQueueMetrics.class);
	@Autowired
	private JmsConfig jmsConfig;
	@Autowired
	private MQQueueConnectionFactory mQQueueConnectionFactory;
	
	public MQueueMetrics() {
	}

	@Override
	public Collection<Metric<?>> metrics() {
		List<Metric<?>> metrics = new ArrayList<>();
		
		 try
         {
			 metrics.add(new Metric<Number>("mq.queue." + jmsConfig.getQueueInput(),1) );
			 metrics.add(new Metric<Number>("mq.queue." + jmsConfig.getQueueInputalt(),2) );
			 metrics.add(new Metric<Number>("mq.queue." + jmsConfig.getQueueOuput(),3) );
			 metrics.add(new Metric<Number>("mq.queue." + jmsConfig.getQueueOutputAlt(),4) );
			 
			// metrics.add(new Metric<Number>(mQQueueConnectionFactory.getAppName(),5) );
			 metrics.add(new Metric<Number>(mQQueueConnectionFactory.getChannel(),6) );
			 metrics.add(new Metric<Number>(mQQueueConnectionFactory.getQueueManager(),7) );
         }
         catch (Exception e)
         {
             log.error(e.getMessage(), e);
         }
		 
		 return metrics;
}

}