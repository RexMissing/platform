package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.whut.meterFrameManagement.aes256.AES;
import org.whut.meterFrameManagement.communication.codec.DataCodecFactory;
<<<<<<< HEAD
import org.whut.meterFrameManagement.communicationframe.key.MeterID;
=======
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
import org.whut.meterFrameManagement.util.hex.Hex;

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
<<<<<<< HEAD

    private static IoConnector connector;
    private void init() {
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(30000);
=======
    //做测试用
    public static final String METERID = "0120151212164";//0120151000088

    public static void connect(){
        IoConnector connector = new NioSocketConnector();
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new DataCodecFactory()));
        connector.setHandler(new TestClientHandler());
<<<<<<< HEAD
    }

    public IoSession connect(){
        /*int sum = 0;
        for (int i = 0; i < 5000; i++) {
=======
        /*for (int i = 0; i < 20; i++) {
            ConnectFuture connectFuture = connector.connect(new InetSocketAddress("10.27.6.212",6601));
            System.out.println("等待建立连接......");
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
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
<<<<<<< HEAD
                    for(int j=0;j< MeterID.METERID.length();j++){
                        request[j+2] = (byte)MeterID.METERID.charAt(j);
=======
                    for (char c : METERID.toCharArray()) {
                        request[i+2] = (byte) c;
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
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
<<<<<<< HEAD
        }
        System.out.println("成功建立" + sum + "个连接");*/
=======
        }*/
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
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
                    for(int i=0;i< METERID.length();i++){
                        request[i+2] = (byte) METERID.charAt(i);
                    }
                    request[15] = 0x16;
                    IoBuffer ioBuffer = IoBuffer.wrap(request);
                    session.write(ioBuffer);
                    break;
                case (byte)0xA2:
                    request = new byte[16];
                    request[0] = 0x68;
                    request[1] = (byte)0xA2;
                    for(int i=0;i<METERID.length();i++){
                        request[i+2] = (byte) METERID.charAt(i);
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
                    for(int i=0;i<METERID.length();i++){
                        request[i+2] = (byte) METERID.charAt(i);
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
<<<<<<< HEAD
        }*/
        return session;
=======
        }
        connector.dispose();
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
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
<<<<<<< HEAD
    public static byte[] getReceiveFrame(byte funCode){
        byte[] receiveBytes = new byte[0];
        switch (funCode){
            case 0x3E:
                String s = "6C4E41C8833CEB9F8E44328CC2E02D0179C988F599E836363740C5897611F3D015963D812E0242539C9D0E934536B5DE5B11C495632EEDB0611B0962ABE37A82";
                receiveBytes = Hex.hexStringToBytes(s, s.length() / 2);
=======
    public static byte[] getReceiveFrame(byte funCode) {
        byte[] bytes = new byte[0];
        switch (funCode){
            case 0x3E:
                byte[] bytes1 = new byte[62];
                bytes1[0] = 0x3E;
                bytes1[1] = 0x3C;
                for(int i=0;i<METERID.length();i++){
                    bytes1[i+2] = (byte) METERID.charAt(i);
                }
                String keyString = "77DD9400EEB5A0DADA40120151212163"+"77DD9400EEB5A0DADA40120151212163";
                byte[] key = Hex.hexStringToBytes(keyString,keyString.length()/2);
                try {
                    bytes = AES.encrypt(bytes1,key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
                break;
            default:
                break;
        }
        return bytes;
    }

    // 断开连接
    public void disconnect() {
        connector.dispose();
    }
}
