package org.whut.meterFrameManagement.MQ.receive;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.whut.meterFrameManagement.MQ.queue.QueueName;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by chenfu on 2017/3/9.
 */
public class ReceiveMsgConsumerStartListener implements ServletContextListener {

    private WebApplicationContext springContext;
    private ReceiveMessageListener receiveMessageListener;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        receiveMessageListener = (ReceiveMessageListener) springContext.getBean("receiveMessageListener");
        receiveMessageListener.register(new Queue() {
            @Override
            public String getQueueName() throws JMSException {
                return QueueName.receiveQueue;
            }
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        receiveMessageListener.stop();
    }
}
