package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.whut.meterFrameManagement.communication.codec.DataCodecFactory;
import org.whut.meterFrameManagement.communicationframe.frames.FrameFactory;
import org.whut.meterFrameManagement.communicationframe.test.FrameStore;
import org.whut.meterFrameManagement.util.date.DateUtil;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: zmz
 * Date: 16-9-2
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {
    public static void connect(){
        IoConnector connector = new NioSocketConnector();
        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new DataCodecFactory()));
        connector.setHandler(new TestClientHandler());
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1",3535));
        System.out.println("等待建立连接......");
        IoSession session = null;
        try {
            connectFuture.awaitUninterruptibly();
            session = connectFuture.getSession();
        }catch (Exception e){
            System.out.println("连接失败");
            System.exit(0);
        }
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
                    byte [] bytes = getReceiveFrame(funCode);
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
    public static void main(String[] args) {
        /*for(int i=0;i<100;i++){
            new Thread(new ClientTread(i)).start();
        }*/
        TestClient.connect();
    }

    //回传测试
    public static byte[] getReceiveFrame(byte funCode){
        String keyStr = "";
        for (int i = 1; i <= 16; i++) {
            String temp = Integer.toHexString(i);
            if (temp.length() < 2)
                temp = "0" + temp;
            keyStr += temp;
        }
        byte[] receiveBytes = new byte[0];
        switch (funCode){
            case (byte)0x81:
                receiveBytes = FrameFactory.getAllowOpenValveBackFrame("1049721501423", keyStr, (byte) 1);
                break;
            case (byte)0x85:
                receiveBytes = FrameFactory.getMeterDataBackFrame("1049721501423",keyStr,(byte)1,234.5,400,2);
                break;
            case 0x3E:
                Date xtsj = new Date();
                Date begin = DateUtil.createDate("2000-01-01 00:00:00");
                long sub = xtsj.getTime() - begin.getTime();
                receiveBytes = FrameFactory.getUnifiedReturnFrame("1049721501423",keyStr,(byte)1,123.4,500,(byte)7,300,2.0,200,250,300,200,
                        sub, sub+1800000,3,129,1,130,2,131,3,0,0,0,0,0,0,0,0,0,0);
                break;
            default:
                break;
        }
        return receiveBytes;
    }
}
