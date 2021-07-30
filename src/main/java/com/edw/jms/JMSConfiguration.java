package com.edw.jms;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.ConnectionFactory;

/**
 * <pre>
 *     com.edw.jms.JMSConfiguration
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 *
 */
@Configuration
public class JMSConfiguration {


    @Value("${spring.activemq.broker-url}")
    private String brokerUrl;

    @Value("${spring.artemis.user}")
    private String user;

    @Value("${spring.artemis.password}")
    private String password;

    @Bean
    public ActiveMQConnectionFactory connectionFactory() {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory();
        try {
            connectionFactory.setBrokerURL(brokerUrl);
            connectionFactory.setUser(user);
            connectionFactory.setPassword(password);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return connectionFactory;
    }

    public ConnectionFactory cachingConnectionFactory() {
        CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory(connectionFactory());
        cachingConnectionFactory.setCacheProducers(true);
        cachingConnectionFactory.setSessionCacheSize(5);
        return new CachingConnectionFactory(connectionFactory());
    }

    @Bean
    @Primary
    public JmsTemplate jmsTemplate() {
        return new JmsTemplate(cachingConnectionFactory());
    }
}
