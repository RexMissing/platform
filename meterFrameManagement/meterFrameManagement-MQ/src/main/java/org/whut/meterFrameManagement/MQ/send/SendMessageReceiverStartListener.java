package org.whut.meterFrameManagement.MQ.send;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.whut.meterFrameManagement.MQ.queue.QueueName;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by zhang_minzhong on 2017/3/2.
 */
public class SendMessageReceiverStartListener implements ServletContextListener {

    private WebApplicationContext springContext;
    private SendMessageListener sendMessageListener;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        springContext = WebApplicationContextUtils.getWebApplicationContext(servletContextEvent.getServletContext());
        sendMessageListener = (SendMessageListener) springContext.getBean("sendMessageListener");
        sendMessageListener.register(new Queue() {
            @Override
            public String getQueueName() throws JMSException {
                return QueueName.sendQueue;
            }
        });
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        sendMessageListener.stop();
    }
}
