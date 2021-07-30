package com.edw.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <pre>
 *     com.edw.jms.JMSService
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 *
 */
@Service
public class JMSService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Value("${queue-name}")
    private String queueName;

    @Transactional
    public void send(String parameter) throws Exception  {
        jmsTemplate.convertAndSend(queueName, parameter);
    }

    @Transactional
    public String read() throws Exception  {
        return (String) jmsTemplate.receiveAndConvert(queueName);
    }
}
