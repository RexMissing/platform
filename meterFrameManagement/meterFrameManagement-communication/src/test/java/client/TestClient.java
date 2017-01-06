package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.whut.meterFrameManagement.communication.codec.DataCodecFactory;
import org.whut.meterFrameManagement.communicationframe.test.TestSendFrame;

import java.net.InetSocketAddress;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: zmz
 * Date: 16-9-2
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {
    public static void main(String[] args) {
        /*for(int i=0;i<100;i++){
            new Thread(new ClientTread(i)).start();
        }*/
        IoConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new DataCodecFactory()));
        connector.setHandler(new TestClientHandler());
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("10.27.13.113",6868));
        //等待建立连接
        connectFuture.awaitUninterruptibly();
        IoSession session = connectFuture.getSession();
        System.out.println("连接成功");
        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while(!quit){
            System.out.print("输入回传命令码：");
            String s = scanner.nextLine();
            //session.write(s);
            if(s.equalsIgnoreCase("quit"))
                quit = true;
            byte funCode = 0;
            try {
                funCode = (byte)Integer.parseInt(s,16);
            }catch (Exception e){
                continue;
            }
            byte [] bytes = TestSendFrame.getReceiveFrame(funCode);
            session.write(IoBuffer.wrap(bytes));
        }
        connector.dispose();
        System.out.println("断开");
    }
}
