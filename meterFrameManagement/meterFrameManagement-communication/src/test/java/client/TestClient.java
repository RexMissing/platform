package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.whut.meterFrameManagement.communication.codec.DataCodecFactory;
import org.whut.meterFrameManagement.communicationframe.key.MeterID;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.net.InetSocketAddress;

/**
 * Created with IntelliJ IDEA.
 * User: zmz
 * Date: 16-9-2
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {

    private static IoConnector connector;
    private void init() {
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new DataCodecFactory()));
        connector.setHandler(new TestClientHandler());
    }

    public IoSession connect(){
        /*int sum = 0;
        for (int i = 0; i < 5000; i++) {
            IoSession session = null;
            ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1",6601));
            try {
                System.out.println("等待建立连接......");
                connectFuture.awaitUninterruptibly();
                sum++;
                if (connectFuture.isConnected()) {
                    session = connectFuture.getSession();
                    byte[] request = new byte[16];
                    request[0] = 0x68;
                    request[1] = (byte)0xA1;
                    for(int j=0;j< MeterID.METERID.length();j++){
                        request[j+2] = (byte)MeterID.METERID.charAt(j);
                    }
                    request[15] = 0x16;
                    IoBuffer ioBuffer = IoBuffer.wrap(request);
                    session.write(ioBuffer);
                    *//*String str = "hello";
                    byte[] bytes = str.getBytes();
                    session.write(IoBuffer.wrap(bytes));*//*
                }
            }catch (Exception e){
                e.printStackTrace();
                System.out.println("连接失败");
                System.exit(0);
            }
        }
        System.out.println("成功建立" + sum + "个连接");*/
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1",6601));
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
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        /*byte[] request;
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
                    for(int i=0;i< MeterID.METERID.length();i++){
                        request[i+2] = (byte)MeterID.METERID.charAt(i);
                    }
                    request[15] = 0x16;
                    IoBuffer ioBuffer = IoBuffer.wrap(request);
                    session.write(ioBuffer);
                    break;
                case (byte)0xA2:
                    request = new byte[16];
                    request[0] = 0x68;
                    request[1] = (byte)0xA2;
                    for(int i=0;i<MeterID.METERID.length();i++){
                        request[i+2] = (byte)MeterID.METERID.charAt(i);
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
                    for(int i=0;i<MeterID.METERID.length();i++){
                        request[i+2] = (byte)MeterID.METERID.charAt(i);
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
                Thread.sleep(300);
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
        }*/
        return session;
    }
    public static void main(String[] args) {
        TestClient client = new TestClient();
        client.init();
        IoSession session = client.connect();
        byte[] request = new byte[16];
        request[0] = 0x68;
        request[1] = (byte)0xA1;
        for(int i=0;i< MeterID.METERID.length();i++){
            request[i+2] = (byte)MeterID.METERID.charAt(i);
        }
        request[15] = 0x16;
        IoBuffer ioBuffer = IoBuffer.wrap(request);
        session.write(ioBuffer);
    }

    //回传测试
    public static byte[] getReceiveFrame(byte funCode){
        byte[] receiveBytes = new byte[0];
        switch (funCode){
            case 0x3E:
                String s = "6C4E41C8833CEB9F8E44328CC2E02D0179C988F599E836363740C5897611F3D015963D812E0242539C9D0E934536B5DE5B11C495632EEDB0611B0962ABE37A82";
                receiveBytes = Hex.hexStringToBytes(s, s.length() / 2);
                break;
            default:
                break;
        }
        return receiveBytes;
    }

    // 断开连接
    public void disconnect() {
        connector.dispose();
    }
}
