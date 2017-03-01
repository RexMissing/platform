package org.whut.meterFrameManagement.mq.send;

import org.whut.meterFrameManagement.communicationframe.store.SendFrameRepository;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 * Created by zhang_minzhong on 2017/3/1.
 */
public class ConsumerMessageListener implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        String sendMessage = "{}";
        try {
            sendMessage = textMessage.getText();
            System.out.println("服务器收到json："+sendMessage);
            SendFrameRepository.jsonList.add(sendMessage);
            /*ObjectMapper objectMapper = new ObjectMapper();
            ValveControl valveControl = objectMapper.readValue(sendMessage, ValveControl.class);
            System.out.println(valveControl.getMeterID()+","
                    + Byte.toUnsignedInt(valveControl.getFunCode())+","
                    +valveControl.getFrameID()+"," +valveControl.getKey()+","+valveControl.getType()+","
                    +valveControl.getAtDT()+","+valveControl.getTimeCorrection());*/
        } catch (JMSException e) {
            e.printStackTrace();
        } /*catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
}
