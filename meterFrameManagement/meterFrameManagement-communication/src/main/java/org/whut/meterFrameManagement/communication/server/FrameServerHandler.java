package org.whut.meterFrameManagement.communication.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.whut.meterFrameManagement.communicationframe.receive.CFunction;
import org.whut.meterFrameManagement.communicationframe.receive.MeterStatus;
import org.whut.meterFrameManagement.communicationframe.test.TestSendFrame;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrame;
import org.whut.platform.fundamental.logger.PlatformLogger;

import java.nio.charset.Charset;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/1/3.
 */
public class FrameServerHandler extends IoHandlerAdapter {

    public static final PlatformLogger logger = PlatformLogger.getLogger(FrameServerHandler.class);

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        super.exceptionCaught(session, cause);
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
    }

    public FrameServerHandler() {
        super();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {

        byte[] receiveBytes = ((IoBuffer)message).array();
        System.out.print("服务端收到：");
        for(int i = 0;i<receiveBytes.length;i++){
            System.out.print(Byte.toUnsignedInt(receiveBytes[i])+" ");
        }
        System.out.println();
        /*
        解析帧
         */
        ReceiveFrame rf =  new ReceiveFrame();
        rf.ParseFrom(receiveBytes,TestSendFrame.getKeyString());
        System.out.println("命令码：" + Integer.toHexString(Byte.toUnsignedInt(rf.getFuncCode())));
        System.out.println("表号："+rf.getMeterID());
        System.out.println("帧id:"+rf.getFrameID());
        System.out.println("传送方向："+rf.getFrmDirection());
        System.out.println("帧的执行结果："+rf.getFrmResult());
        MeterStatus meterStatus = rf.MeterST;
        System.out.println("系统状态字节："+meterStatus.getXtzt());
        System.out.println("阀门位置："+meterStatus.getFMWZ());
        System.out.println("阀门位置错："+meterStatus.getFMCW());
        System.out.println("计量传感器错："+meterStatus.getCGGZ());
        System.out.println("透支标志："+meterStatus.getTZZT());
        System.out.println("系统数据错："+meterStatus.getXTSJC());
        System.out.println("剩余金额："+meterStatus.getRemainMoney());
        System.out.println("表止码："+meterStatus.getMeterRead());
        if(Byte.toUnsignedInt(rf.getFuncCode())==0x3E){
            System.out.println("以下为统一回传帧中额外数据");
            System.out.println("上月用气总量："+meterStatus.getPresumamount());
            System.out.println("当前使用气价："+meterStatus.getPrice());
            System.out.println("分段气量1："+meterStatus.getAmount1());
            System.out.println("分段气量2："+meterStatus.getAmount2());
            System.out.println("分段气量3："+meterStatus.getAmount3());
            System.out.println("本月已用气量："+meterStatus.getSumamount());
            System.out.println("表具时间："+meterStatus.getMeterTime());
            System.out.println("执行的命令数："+rf.getFuncCount());
            List<CFunction> codeList = rf.getAryFunc();
            for(int i=0;i<codeList.size();i++){
                CFunction cFunction = codeList.get(i);
                System.out.println("命令码：" + Integer.toHexString(Byte.toUnsignedInt(cFunction.getCode())) + "，" + "帧id：" + cFunction.getFid());
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {


        logger.info("server - 消息发出!");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {


        logger.info("server - session关闭连接断开...");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {


        logger.info("server - session创建，建立连接...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {

        logger.info("server-服务端进入空闲状态...");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {

        logger.info("server-服务端与客户端连接打开...");
    }
}
