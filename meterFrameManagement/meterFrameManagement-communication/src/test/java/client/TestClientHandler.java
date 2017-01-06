package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.whut.meterFrameManagement.aes256.AES;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrame;
import org.whut.meterFrameManagement.communicationframe.test.TestSendFrame;

import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: zmz
 * Date: 16-9-2
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class TestClientHandler extends IoHandlerAdapter {
    @Override
    public void exceptionCaught(IoSession arg0, Throwable arg1)
            throws Exception {
        // TODO Auto-generated method stub
        arg1.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession arg0, Object message) throws Exception {
        byte[] receiveBytes = ((IoBuffer)message).array();
        System.out.print("客户端收到：");
        for (int i=0;i<receiveBytes.length;i++){
            System.out.print(Byte.toUnsignedInt(receiveBytes[i])+" ");
        }
        System.out.println();
        ReceiveFrame rf = new ReceiveFrame();
        rf.ParseFrom(receiveBytes,TestSendFrame.getKeyString());
        System.out.println("表号：" + rf.getMeterID());
        System.out.println("帧id:"+rf.getFrameID());
    }

    @Override
    public void messageSent(IoSession arg0, Object message) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("客户端发送信息："+message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("客户端与:"+session.getRemoteAddress().toString()+"断开连接");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("客户端与:"+session.getRemoteAddress().toString()+"建立连接");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        // TODO Auto-generated method stub
        System.out.println( "IDLE " + session.getIdleCount( status ));
    }

    @Override
    public void sessionOpened(IoSession arg0) throws Exception {
        // TODO Auto-generated method stub
        System.out.println("打开连接");
    }
}
