package org.whut.meterFrameManagement.communication.server;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.whut.meterFrameManagement.communicationframe.TestSendFrame;
import org.whut.meterFrameManagement.communicationframe.frames.FrameFactory;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrame;
import org.whut.platform.fundamental.logger.PlatformLogger;

import java.nio.charset.Charset;

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
        byte[] receiveBytes = message.toString().getBytes(Charset.forName("utf-8"));
        System.out.print("服务端收到：");
        for(int i = 0;i<receiveBytes.length;i++){
            System.out.print(receiveBytes[i]+" ");
        }
        System.out.println();
        /*TO DO
        解析帧
         */
        ReceiveFrame rf = parseFrame(receiveBytes);

        byte[] sendBytes = TestSendFrame.getSendFrame();
        String sendStr = new String(sendBytes,Charset.forName("utf-8"));
        session.write(sendStr);
    }

    public ReceiveFrame parseFrame(byte[] receiveBytes){
        ReceiveFrame rf = new ReceiveFrame();
        rf.ParseFrom(receiveBytes,TestSendFrame.getKeyString());
        return rf;
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
