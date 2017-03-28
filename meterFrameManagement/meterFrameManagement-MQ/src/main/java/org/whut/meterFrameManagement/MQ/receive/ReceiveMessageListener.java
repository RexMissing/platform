package org.whut.meterFrameManagement.MQ.receive;

import org.apache.activemq.command.ActiveMQTextMessage;
import org.whut.meterFrameManagement.communicationframe.key.TestKey;
import org.whut.meterFrameManagement.communicationframe.receive.CFunction;
import org.whut.meterFrameManagement.communicationframe.receive.MeterStatus;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrame;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrameRepository;
import org.whut.meterFrameManagement.util.hex.Hex;
import org.whut.platform.fundamental.activemq.consumer.PooledMessageConsumerBase;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import java.util.List;

/**
 * Created by chenfu on 2017/3/9.
 */
public class ReceiveMessageListener extends PooledMessageConsumerBase {

    @Override
    public void register(Destination destination) {
         receiveMessage(destination);
    }

    @Override
    public void onMessage(Message message) {
        String receiveMessage;
        try {
            receiveMessage = ((ActiveMQTextMessage) message).getText();
            byte[] command = Hex.decode(receiveMessage);
            System.out.print("队列消费的字节数组：");
            for (byte b : command) {
                System.out.print(Byte.toUnsignedInt(b) + " ");
            }
            System.out.println();
            System.out.println();
            // 测试，解析回传帧，并将表具发过来的帧保存在List中
            ReceiveFrame rf =  new ReceiveFrame();
            rf.ParseFrom(command, TestKey.KEYSTR);
            ReceiveFrameRepository.write(rf);

            int funCode = Byte.toUnsignedInt(rf.getFuncCode());
            System.out.println("命令码：" + Integer.toHexString(funCode));
            System.out.println("表号："+rf.getMeterID());
            System.out.println("帧id:"+Byte.toUnsignedInt(rf.getFrameID()));
            System.out.println("传送方向："+rf.getFrmDirection());
            System.out.println("帧的回传结果："+rf.getFrmResult());
            MeterStatus meterStatus = rf.MeterST;
            System.out.println("系统状态字节："+meterStatus.getXtzt());
            System.out.println("阀门位置："+meterStatus.getFMWZ());
            System.out.println("阀门位置错："+meterStatus.getFMCW());
            System.out.println("计量传感器错："+meterStatus.getCGGZ());
            System.out.println("透支标志："+meterStatus.getTZZT());
            System.out.println("系统数据错："+meterStatus.getXTSJC());
            if(funCode==0x3E||funCode==0x05||funCode==0x06||funCode==0x08||funCode==0x09||funCode==0x10||funCode==0x26||funCode==0x27) {
                System.out.println("剩余金额：" + meterStatus.getRemainMoney());
                System.out.println("表止码：" + meterStatus.getMeterRead());
            }
            if(funCode==0x3E){
                System.out.println("以下为统一回传帧中额外数据");
                System.out.println("上周期量："+meterStatus.getPresumamount());
                System.out.println("单价："+meterStatus.getPrice());
                System.out.println("阶梯起始1："+meterStatus.getAmount1());
                System.out.println("阶梯起始2："+meterStatus.getAmount2());
                System.out.println("阶梯起始3："+meterStatus.getAmount3());
                System.out.println("本周期量："+meterStatus.getSumamount());
                System.out.println("表具时间："+meterStatus.getMeterTime());
                System.out.println("执行的命令数："+rf.getFuncCount());
                List<CFunction> codeList = rf.getAryFunc();
                for(int i=0;i<codeList.size();i++){
                    CFunction cFunction = codeList.get(i);
                    System.out.println("命令码：" + Integer.toHexString(Byte.toUnsignedInt(cFunction.getCode()))
                            + "，" + "帧id：" + Byte.toUnsignedInt(cFunction.getFid())
                            +"，"+"是否执行成功："+cFunction.isSuccess());
                }
            }

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
