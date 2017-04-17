package client;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
<<<<<<< HEAD
import org.whut.meterFrameManagement.communicationframe.key.MeterID;
import org.whut.meterFrameManagement.communicationframe.key.TestKey;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrame;
=======
import org.whut.meterFrameManagement.aes256.AES;
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
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

    // 关闭重连后，下一个请求命令
    private byte nextFunCode;

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
<<<<<<< HEAD
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
=======
        if(receiveBytes[0] == 0x68 && receiveBytes[receiveBytes.length-1] == 0x16){
            int command = Byte.toUnsignedInt(receiveBytes[1]);
            if(command == 0xA1){
                byte[] bytes = Arrays.copyOfRange(receiveBytes,2,6);
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
                String hex = Hex.BytesToHexString(bytes);
                long sub = Long.parseLong(hex, 16);
                Date date = DateUtil.createDate("2000-1-1 00:00:00");
                long end = sub * 1000 + date.getTime();
                Date date1 = new Date(end);
<<<<<<< HEAD
                System.out.println("系统时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
                break;
            case (byte) 0xA2:
                returnInt = Byte.toUnsignedInt(receiveBytes[2]);
                if (returnInt == 0) {
                    System.out.println("服务器已无指令.");
                } else {
                    bytes = Arrays.copyOfRange(receiveBytes, 3, 3 + returnInt);
                    String hexStr = Hex.BytesToHexString(bytes);
                    System.out.println("16进制加密命令帧串：" + hexStr);
                    ReceiveFrame receiveFrame = new ReceiveFrame();
                    receiveFrame.ParseFrom(bytes, TestKey.KEYSTR);
                    System.out.println("表号：" + receiveFrame.getMeterID());
                    System.out.println("命令码：" + Integer.toHexString(Byte.toUnsignedInt(receiveFrame.getFuncCode())));
                    System.out.println("帧ID：" + receiveFrame.getFrameID());
                    System.out.println("传送方向：" + receiveFrame.getFrmDirection());
                    System.out.println("回传帧结果：" + receiveFrame.getFrmResult());
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
                for (int i = 0; i < MeterID.METERID.length(); i++) {
                    request[i + 2] = (byte) MeterID.METERID.charAt(i);
                }
                request[15] = 0x16;
                IoBuffer ioBuffer = IoBuffer.wrap(request);
                session.write(ioBuffer);
                break;
            case (byte) 0xA3:
                byte[] bytes = TestClient.getReceiveFrame((byte) 0x3E);
                request = new byte[bytes.length + 17];
                request[0] = 0x68;
                request[1] = (byte) 0xA3;
                for (int i = 0; i < MeterID.METERID.length(); i++) {
                    request[i + 2] = (byte) MeterID.METERID.charAt(i);
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
=======
                System.out.println("系统时间："+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date1));
            }
            if(command == 0xA2&&receiveBytes[2]>0){
                byte[] bytes = Arrays.copyOfRange(receiveBytes,3,receiveBytes.length-1);
                String key = "77DD9400EEB5A0DADA40120151212163"+"77DD9400EEB5A0DADA40120151212163";
                byte[] keyBytes = Hex.hexStringToBytes(key,key.length()/2);
                byte[] result = AES.decrypt(bytes,keyBytes);
                byte[] meterBytes = Arrays.copyOfRange(result,2,15);
                StringBuffer meterID = new StringBuffer();
                for(int i=0;i<meterBytes.length;i++)
                    meterID.append((char)meterBytes[i]);
                String resultString = "h"+Hex.BytesToHexString(result)+"16";
                System.out.println("客户端解密后："+resultString);
                System.out.println("表号："+meterID);
            }
>>>>>>> 4875370d3f95f5e9b262f025ddb2f356a2778e56
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("客户端" + session.getId() + "发送信息：" + message.toString());
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        System.out.println("客户端" + session.getId() + "与:" + session.getRemoteAddress().toString() + "断开连接");
        TestClient client = new TestClient();
        if (nextFunCode == (byte) 0xAA) {
            System.out.println("通讯完成.");
            client.disconnect();
        } else {
            // 重新连接
            IoSession newSession = client.connect();
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
