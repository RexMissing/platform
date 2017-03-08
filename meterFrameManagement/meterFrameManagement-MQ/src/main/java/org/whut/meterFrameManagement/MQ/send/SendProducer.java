package org.whut.meterFrameManagement.mq.send;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.whut.meterFrameManagement.mq.queue.QueueName;
import org.whut.platform.fundamental.activemq.api.PooledMessageDispatcher;
import org.whut.platform.fundamental.activemq.api.PooledMessageProducer;

import javax.jms.Destination;
import javax.jms.MessageNotWriteableException;
import javax.jms.ObjectMessage;

/**
 * Created by zhang_minzhong on 2017/3/2.
 */
public class SendProducer implements PooledMessageDispatcher {
    private PooledMessageProducer pooledMessageProducer;

    public PooledMessageProducer getPooledMessageProducer() {
        return pooledMessageProducer;
    }

    public void setPooledMessageProducer(PooledMessageProducer pooledMessageProducer) {
        this.pooledMessageProducer = pooledMessageProducer;
    }

    @Override
    public void dispatchMessage(String messageBody) {
        try {
            ActiveMQTextMessage message = new ActiveMQTextMessage();
            message.setText(messageBody);
            pooledMessageProducer.sendQueue(QueueName.sendQueue,message);
        } catch (MessageNotWriteableException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public void exceptionProcess() {

    }
}
