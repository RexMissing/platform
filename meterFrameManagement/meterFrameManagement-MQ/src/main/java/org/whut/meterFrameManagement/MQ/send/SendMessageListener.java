package org.whut.meterFrameManagement.mq.send;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.whut.meterFrameManagement.communicationframe.send.SendFrameRepository;
import org.whut.platform.fundamental.activemq.consumer.PooledMessageConsumerBase;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by zhang_minzhong on 2017/3/2.
 */
public class SendMessageListener extends PooledMessageConsumerBase {

    @Override
    public void register(Destination destination) {
        receiveMessage(destination);
    }

    @Override
    public void onMessage(Message message) {
        String sendMessage = "{}";
        if (message instanceof ActiveMQTextMessage) {
            try {
                System.out.println(this.getMessageConsumer() + "  服务器收到json：" + ((ActiveMQTextMessage)message).getText());
                sendMessage = ((ActiveMQTextMessage)message).getText();
                String s = new String(sendMessage);
                SendFrameRepository.jsonList.add(s);
            } catch (JMSException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
