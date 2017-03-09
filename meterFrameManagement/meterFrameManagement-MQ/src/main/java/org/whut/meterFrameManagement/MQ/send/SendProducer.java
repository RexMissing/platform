package org.whut.meterFrameManagement.MQ.send;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.whut.meterFrameManagement.MQ.queue.QueueName;
import org.whut.platform.fundamental.activemq.api.PooledMessageDispatcher;
import org.whut.platform.fundamental.activemq.api.PooledMessageProducer;

import javax.jms.MessageNotWriteableException;

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
