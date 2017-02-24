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
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1",3535));
        //等待建立连接
        connectFuture.awaitUninterruptibly();
        IoSession session = connectFuture.getSession();
        System.out.println("连接成功");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String meterID = "1049721501423";
        byte[] request;
        System.out.print("输入请求命令：");
        Scanner scanner = new Scanner(System.in);
        String s1 = scanner.nextLine();
        byte requestCode = 0;
        try {
            requestCode = (byte)Integer.parseInt(s1,16);
        }catch (Exception e){
        }
        while (!s1.trim().equalsIgnoreCase("quit")){
            if (session.isClosing())
                break;
            switch (requestCode){
                case (byte)0xA1:
                    request = new byte[16];
                    request[0] = 0x68;
                    request[1] = (byte)0xA1;
                    for(int i=0;i<meterID.length();i++){
                        request[i+2] = (byte)meterID.charAt(i);
                    }
                    request[15] = 0x16;
                    IoBuffer ioBuffer = IoBuffer.wrap(request);
                    session.write(ioBuffer);

                    break;
                case (byte)0xA2:
                    request = new byte[16];
                    request[0] = 0x68;
                    request[1] = (byte)0xA2;
                    for(int i=0;i<meterID.length();i++){
                        request[i+2] = (byte)meterID.charAt(i);
                    }
                    request[15] = 0x16;
                    ioBuffer = IoBuffer.wrap(request);
                    session.write(ioBuffer);
                    break;
                case (byte)0xA3:
                    System.out.print("输入回传命令码：");
                    String s2 = scanner.nextLine();
                    byte funCode = 0;
                    funCode = (byte)Integer.parseInt(s2,16);
                    byte [] bytes = TestSendFrame.getReceiveFrame(funCode);
                    request = new byte[bytes.length+17];
                    request[0] = 0x68;
                    request[1] = (byte)0xA3;
                    for(int i=0;i<meterID.length();i++){
                        request[i+2] = (byte)meterID.charAt(i);
                    }
                    request[15] = (byte)bytes.length;
                    for(int i=0;i<bytes.length;i++){
                        request[i+16] = bytes[i];
                    }
                    request[request.length-1] = 0x16;
                    if(bytes.length>0)
                        session.write(IoBuffer.wrap(request));
                    break;
                default:
                    break;
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("输入请求命令：");
            scanner = new Scanner(System.in);
            s1 = scanner.nextLine();
            try {
                requestCode = (byte)Integer.parseInt(s1,16);
            }catch (Exception e){
            }
        }
        connector.dispose();
    }
}
