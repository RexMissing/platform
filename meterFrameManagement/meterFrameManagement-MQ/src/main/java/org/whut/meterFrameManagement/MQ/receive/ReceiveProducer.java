package org.whut.meterFrameManagement.MQ.receive;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.whut.meterFrameManagement.MQ.queue.QueueName;
import org.whut.platform.fundamental.activemq.api.PooledMessageDispatcher;
import org.whut.platform.fundamental.activemq.api.PooledMessageProducer;

import javax.jms.MessageNotWriteableException;

/**
 * Created by chenfu on 2017/3/9.
 */
public class ReceiveProducer implements PooledMessageDispatcher {

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
            pooledMessageProducer.sendQueue(QueueName.receiveQueue, message);
        } catch (MessageNotWriteableException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionProcess() {

    }


}
