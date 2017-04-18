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
import org.whut.meterFrameManagement.util.hex.Hex;

import java.net.InetSocketAddress;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zmz
 * Date: 16-9-2
 * Time: 下午3:28
 * To change this template use File | Settings | File Templates.
 */
public class TestClient {
    //做测试用
    public static final String METERID = "0120151212163";//0120151000088
    private static IoConnector connector;
    private void init() {
        connector = new NioSocketConnector();
        connector.setConnectTimeoutMillis(30000);
        connector.getFilterChain().addLast( "logger", new LoggingFilter() );
        connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new DataCodecFactory()));
        connector.setHandler(new TestClientHandler());
    }

    public IoSession connect(){
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("10.138.83.25",6601));
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
        for(int i=0;i< METERID.length();i++){
            request[i+2] = (byte)METERID.charAt(i);
        }
        request[15] = 0x16;
        IoBuffer ioBuffer = IoBuffer.wrap(request);
        session.write(ioBuffer);
    }

    //回传测试
    public static byte[] getReceiveFrame(byte funCode) {
        byte[] bytes = new byte[0];
        switch (funCode){
            case 0x3E:
                byte[] bytes1 = new byte[63];
                bytes1[0] = 0x3E;
                bytes1[1] = 0x3C;
                for(int i=0;i<METERID.length();i++){
                    bytes1[i+2] = (byte) METERID.charAt(i);
                }
                // SYQL
                bytes1[15] = 0;
                bytes1[16] = 0;
                bytes1[17] = 0;
                bytes1[18] = 40;
                // ZYQL
                bytes1[19] = 0;
                bytes1[20] = 0;
                bytes1[21] = 0;
                bytes1[22] = 70;
                // XTZTZJ
                bytes1[23] = 0;
                // 上月用气量
                bytes1[24] = 0;
                bytes1[25] = 20;
                // DJ
                bytes1[26] = 0;
                bytes1[27] = (byte) 2.0;
                // FDQL1
                bytes1[28] = 0;
                bytes1[29] = 70;
                // FDQL2、FDQL3、FDQL4
                for (int j = 0; j < 6; j++) {
                    bytes1[j + 30] = 0;
                }
                // XTSJ
                int iva = (int) new Date().getTime()/1000;
                String stmp = Integer.toHexString(iva);
                while(stmp.length()<4*2){
                    stmp = "0"+stmp;
                }
                for (int j = 0; j < 4; j++)
                {
                    String ss = stmp.substring(j * 2, j * 2 + 2);
                    bytes1[j + 36] = (byte) Integer.parseInt(ss,16);
                }
                // DSSJ
                for (int j = 0; j < 4; j++) {
                    bytes1[j + 40] = 0;
                }
                // N
                bytes1[44] = 0;
                // Ci、IDi
                bytes1[45] = 1;
                bytes1[46] = 1;
                bytes1[47] = 1;
                bytes1[48] = 1;
                bytes1[49] = 1;
                bytes1[50] = 1;
                bytes1[51] = 1;
                bytes1[52] = 1;
                bytes1[53] = 1;
                bytes1[54] = 1;
                bytes1[55] = 1;
                bytes1[56] = 1;
                bytes1[57] = 1;
                bytes1[58] = 1;
                bytes1[59] = 1;
                bytes1[60] = 1;
                // ID
                bytes1[61] = 1;
                // CS
                int cs = 0;
                for (int i = 0; i < 62; i++) {
                    cs += Byte.toUnsignedInt(bytes1[i]);
                }
                cs = cs % 256;
                bytes1[62] = (byte) cs;

                String keyString = "77DD9400EEB5A0DADA40120151212163"+"77DD9400EEB5A0DADA40120151212163";
                //String keyString = "640836FBC4A6FD68431AE03CF44C0232" + "640836FBC4A6FD68431AE03CF44C0232";
                byte[] key = Hex.hexStringToBytes(keyString,keyString.length()/2);
                try {
                    bytes = AES.encrypt(bytes1,key);
                } catch (Exception e) {
                    e.printStackTrace();
                }
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
