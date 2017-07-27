package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.whut.meterFrameManagement.aes256.AES;
import org.whut.meterFrameManagement.util.date.DateUtil;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zmz
 * Date: 16-9-2
 * Time: 下午3:33
 * To change this template use File | Settings | File Templates.
 */
public class TestClientHandler extends IoHandlerAdapter {

    //做测试用
    public String METERID = "";//0120151000088
    // 关闭重连后，下一个请求命令
    private byte nextFunCode;

    private IoConnector connector;
    public TestClientHandler(String meterID, IoConnector connector) {
        this.METERID = meterID;
        this.connector = connector;
    }

    @Override
    public void exceptionCaught(IoSession arg0, Throwable arg1)
            throws Exception {
        arg1.printStackTrace();
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        byte[] receiveBytes = ((IoBuffer) message).array();
        System.out.print("客户端" + session.getId() + "收到：");
        for (int i = 0; i < receiveBytes.length; i++) {
            System.out.print(Byte.toUnsignedInt(receiveBytes[i]) + " ");
        }
        System.out.println();
        if (receiveBytes[0] == 0x68 && receiveBytes[receiveBytes.length - 1] == 0x16) {
            if (Byte.toUnsignedInt(receiveBytes[1]) == 0xA1) {
                parseFrame(receiveBytes, (byte) 0xA1);
                nextFunCode = (byte) 0xA2;
            }
            if (Byte.toUnsignedInt(receiveBytes[1]) == 0xA2) {
                int H = parseFrame(receiveBytes, (byte) 0xA2);
                if (H == 0) {
                    nextFunCode = (byte) 0xA3;
                }
            }
            if (Byte.toUnsignedInt(receiveBytes[1]) == 0xA3) {
                nextFunCode = (byte) 0xAA;
            }
        }
    }

    /**
     * 解析服务器回传帧
     *
     * @param receiveBytes 回传帧
     * @param funCode      命令码
     * @return 是否还有帧，仅对对A2回传帧有用
     */
    private int parseFrame(byte[] receiveBytes, byte funCode) {
        int returnInt = -1;
        switch (funCode) {
            case (byte) 0xA1:
                byte[] bytes = Arrays.copyOfRange(receiveBytes, 2, 6);
                String hex = Hex.BytesToHexString(bytes);
                long sub = Long.parseLong(hex, 16);
                Date date = DateUtil.createDate("2000-1-1 00:00:00");
                long end = sub * 1000 + date.getTime();
                Date date1 = new Date(end);
                System.out.println("系统时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
                break;
            case (byte) 0xA2:
                returnInt = Byte.toUnsignedInt(receiveBytes[2]);
                if (returnInt == 0) {
                    System.out.println("服务器已无指令.");
                } else {
                    bytes = Arrays.copyOfRange(receiveBytes, 3, 3 + returnInt);
                    String keyString = "77DD9400EEB5A0DADA40120151212163"+"77DD9400EEB5A0DADA40120151212163";
                    //String keyString = "640836FBC4A6FD68431AE03CF44C0232" + "640836FBC4A6FD68431AE03CF44C0232";
                    byte[] key = Hex.hexStringToBytes(keyString,keyString.length()/2);
                    try {
                        System.out.println("表具" + METERID + "收到的命令帧：" +Hex.BytesToHexString(AES.decrypt(bytes, key)));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
        return returnInt;
    }

    /**
     * 向服务器发送A2、A3请求
     *
     * @param session     连接
     * @param requestCode 请求命令码
     */
    private void sendRequest(IoSession session, byte requestCode) {
        byte[] request;
        switch (requestCode) {
            case (byte) 0xA2:
                request = new byte[16];
                request[0] = 0x68;
                request[1] = (byte) 0xA2;
                for (int i = 0; i < METERID.length(); i++) {
                    request[i + 2] = (byte) METERID.charAt(i);
                }
                request[15] = 0x16;
                IoBuffer ioBuffer = IoBuffer.wrap(request);
                session.write(ioBuffer);
                break;
            case (byte) 0xA3:
                byte[] bytes = TestClient.getReceiveFrame((byte) 0x3E, METERID);
                request = new byte[bytes.length + 17];
                request[0] = 0x68;
                request[1] = (byte) 0xA3;
                for (int i = 0; i < METERID.length(); i++) {
                    request[i + 2] = (byte) METERID.charAt(i);
                }
                request[15] = (byte) bytes.length;
                for (int i = 0; i < bytes.length; i++) {
                    request[i + 16] = bytes[i];
                }
                request[request.length - 1] = 0x16;
                if (bytes.length > 0)
                    session.write(IoBuffer.wrap(request));
                break;
            default:
                break;
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("客户端" + session.getId() + "发送信息：" + message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("客户端与服务器断开连接");
        TestClient client = new TestClient();
        if (nextFunCode == (byte) 0xAA) {
            System.out.println("通讯完成.");
            connector.dispose();
        } else {
            // 重新连接
            IoSession newSession = client.connect(connector);
            sendRequest(newSession, nextFunCode);
        }
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("客户端" + session.getId() + "与:" + session.getRemoteAddress().toString() + "建立连接");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("IDLE " + session.getIdleCount(status));
    }

    @Override
    public void sessionOpened(IoSession arg0) throws Exception {
        System.out.println("打开连接");
    }
}
