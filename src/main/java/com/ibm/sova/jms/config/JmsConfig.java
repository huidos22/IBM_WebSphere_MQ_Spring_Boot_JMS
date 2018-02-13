package com.ibm.sova.jms.config;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
@EnableTransactionManagement
//@PropertySource(value={"classpath:application.yml"})
public class JmsConfig {
	private static final Logger log = LoggerFactory.getLogger(JmsConfig.class);
    @Value("${project.mq.host}")
    private String host;
    @Value("${project.mq.port}")
    private Integer port;
    @Value("${project.mq.queue-manager}")
    private String queueManager;
    @Value("${project.mq.channel}")
    private String channel;
    @Value("${project.mq.queue.input}")
    private String queueInput;
    @Value("${project.mq.queue.input.alt}")
    private String queueInputalt;
    @Value("${project.mq.queue.output}")
    private String queueOuput;
    @Value("${project.mq.queue.output.alt}")
    private String queueOutputAlt;
    
    @Value("${project.mq.receive-timeout}")
    private long receiveTimeout;
//  @Value("${project.mq.username}")
//  private String username;
//  @Value("${project.mq.password}")
//  private String password;
    @Bean
    public MQQueueConnectionFactory mqQueueConnectionFactory() {
        MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
        mqQueueConnectionFactory.setHostName(host);
        try {
            mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            mqQueueConnectionFactory.setCCSID(1208);
            mqQueueConnectionFactory.setChannel(channel);
            mqQueueConnectionFactory.setPort(port);
            mqQueueConnectionFactory.setQueueManager(queueManager);
        } catch (JMSException e) {
        	log.error(e.getMessage(),e);
        }
        return mqQueueConnectionFactory;
    }

    @Bean
    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) {
        UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
//        userCredentialsConnectionFactoryAdapter.setUsername(username);
//        userCredentialsConnectionFactoryAdapter.setPassword(password);
        userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
        return userCredentialsConnectionFactoryAdapter;
    }

    @Bean
    @Primary
    public CachingConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
        cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
        cachingConnectionFactory.setSessionCacheSize(500);
        cachingConnectionFactory.setReconnectOnException(true);
        return cachingConnectionFactory;
    }

    @Bean
    public PlatformTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(cachingConnectionFactory);
        return jmsTransactionManager;
    }
    @Bean
    public JmsOperations jmsOperations(CachingConnectionFactory cachingConnectionFactory) {
        JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
        jmsTemplate.setReceiveTimeout(receiveTimeout);
        return jmsTemplate;
    }
    public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getQueueManager() {
		return queueManager;
	}

	public void setQueueManager(String queueManager) {
		this.queueManager = queueManager;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getQueueInput() {
		return queueInput;
	}

	public void setQueueInput(String queueInput) {
		this.queueInput = queueInput;
	}

	public String getQueueInputalt() {
		return queueInputalt;
	}

	public void setQueueInputalt(String queueInputalt) {
		this.queueInputalt = queueInputalt;
	}

	public String getQueueOuput() {
		return queueOuput;
	}

	public void setQueueOuput(String queueOuput) {
		this.queueOuput = queueOuput;
	}

	public String getQueueOutputAlt() {
		return queueOutputAlt;
	}

	public void setQueueOutputAlt(String queueOutputAlt) {
		this.queueOutputAlt = queueOutputAlt;
	}

	public long getReceiveTimeout() {
		return receiveTimeout;
	}

	public void setReceiveTimeout(long receiveTimeout) {
		this.receiveTimeout = receiveTimeout;
	}

	
}
