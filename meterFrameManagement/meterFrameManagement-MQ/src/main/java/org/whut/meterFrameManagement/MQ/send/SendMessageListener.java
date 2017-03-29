package org.whut.meterFrameManagement.MQ.send;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.whut.meterFrameManagement.communicationframe.send.SendFrameRepository;
import org.whut.meterFrameManagement.db.business.SendFrameBusiness;
import org.whut.platform.fundamental.activemq.consumer.PooledMessageConsumerBase;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

/**
 * Created by zhang_minzhong on 2017/3/2.
 */
public class SendMessageListener extends PooledMessageConsumerBase {
    private SendFrameBusiness sendFrameBusiness;

    public SendFrameBusiness getSendFrameBusiness() {
        return sendFrameBusiness;
    }

    public void setSendFrameBusiness(SendFrameBusiness sendFrameBusiness) {
        this.sendFrameBusiness = sendFrameBusiness;
    }

    @Override
    public void register(Destination destination) {
        receiveMessage(destination);
    }

    @Override
    public void onMessage(Message message) {
        String jsonString = "{}";
        if (message instanceof ActiveMQTextMessage) {
            try {
                System.out.println(this.getMessageConsumer() + "  服务器收到json：" + ((ActiveMQTextMessage)message).getText());
                jsonString = ((ActiveMQTextMessage)message).getText();
                //String s = new String(sendMessage);
                sendFrameBusiness.makeSendFrame(jsonString);
            } catch (JMSException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }
}
