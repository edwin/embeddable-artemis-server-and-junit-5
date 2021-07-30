package com.edw.jms;

import org.apache.activemq.artemis.core.config.impl.SecurityConfiguration;
import org.apache.activemq.artemis.core.server.embedded.EmbeddedActiveMQ;
import org.apache.activemq.artemis.spi.core.security.ActiveMQJAASSecurityManager;
import org.apache.activemq.artemis.spi.core.security.jaas.InVMLoginModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <pre>
 *     com.edw.jms.JMSServiceTest
 * </pre>
 *
 * @author Muhammad Edwin < edwin at redhat dot com >
 *
 */
@SpringBootTest
public class JMSServiceTest {

    @Autowired
    private JMSService jmsService;

    private static EmbeddedActiveMQ embedded = new EmbeddedActiveMQ();

    @BeforeAll
    public static void before() throws Exception {
        SecurityConfiguration configuration = new SecurityConfiguration();
        configuration.addUser("user", "password");
        configuration.addRole("user", "admin");

        final ActiveMQJAASSecurityManager securityManager = new ActiveMQJAASSecurityManager (InVMLoginModule.class.getName(), configuration);

        embedded.setSecurityManager(securityManager);
        embedded.start();
    }

    @AfterAll
    public static void after() throws Exception {
        embedded.stop();
    }

    /**
     * send message to local embeddable jms, and read it.
     *
     * @throws Exception
     */
    @Test
    @DisplayName("This test is to check if a valid message is being sent to MQ thru JMS is success, and we can read it after")
    public void sendJMSData_expectSuccess() throws Exception {
        Assertions.assertDoesNotThrow(()-> jmsService.send("trysmething"));
        Thread.sleep(2000);
        Assertions.assertEquals("trysmething", jmsService.read());
    }

}
