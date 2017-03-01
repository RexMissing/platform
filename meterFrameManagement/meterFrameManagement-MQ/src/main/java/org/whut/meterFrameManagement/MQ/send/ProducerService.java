package org.whut.meterFrameManagement.mq.send;

import javax.jms.Destination;

/**
 * Created by zhang_minzhong on 2017/3/1.
 */
public interface ProducerService {
    public void sendMessage(Destination destination,String message);
}
