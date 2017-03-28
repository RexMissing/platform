package org.whut.meterFrameManagement.communication.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.whut.meterFrameManagement.MQ.receive.ReceiveProducer;
import org.whut.meterFrameManagement.communicationframe.send.SendFrameRepository;
import org.whut.meterFrameManagement.util.date.DateUtil;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zhang_minzhong on 2017/1/3.
 */
public class FrameServerHandler extends IoHandlerAdapter {

    @Autowired
    private ReceiveProducer receiveProducer;

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        //super.exceptionCaught(session, cause);
        System.out.println("异常");
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        super.inputClosed(session);
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
       //System.out.println("messageReceived方法线程id："+Thread.currentThread().getId()+"，名称："+Thread.currentThread().getName());
        byte[] request = ((IoBuffer)message).array();
        System.out.print("服务端收到：");
        for(int i = 0;i<request.length;i++){
            System.out.print(Byte.toUnsignedInt(request[i])+" ");
        }
        System.out.println();
        if(request[0] == 0x68 && request[request.length-1] == 0x16){
            String meterId = "";
            for(int i=0;i<13;i++){
                meterId += (char)request[2+i];
            }
            System.out.println("查询表号："+meterId);
            System.out.println();
            if(Byte.toUnsignedInt(request[1]) == 0xA1){//签到
                byte[] response = new byte[7];
                response[0] = 0x68;
                response[1] = (byte)0xA1;
                Date endDate = new Date();
                long end = endDate.getTime();
                Date beginDate = DateUtil.createDate("2000-1-1 00:00:00");
                long begin = beginDate.getTime();
                int sub = (int)((end-begin)/1000);
                String hexStr = Integer.toHexString(sub);
                while(hexStr.length()<4*2){
                    hexStr = "0" + hexStr;
                }
                for(int i=0;i<4;i++){
                    String temp = hexStr.substring(i*2,i*2+2);
                    response[i+2] = (byte)Integer.parseInt(temp,16);
                }
                /*int cs = 0;
                for(int i=0;i<6;i++){
                    cs += Byte.toUnsignedInt(response[i]);
                }
                cs%=256;
                response[6] = (byte)cs;*/
                response[6] = 0x16;
                IoBuffer ioBuffer = IoBuffer.wrap(response);
                session.write(ioBuffer);
                session.closeOnFlush();
            }
            if(Byte.toUnsignedInt(request[1]) == 0xA2){//查询
                SendFrameRepository.makeSendFrame();//启动服务器时用
                List<Map<String,byte[]>> sendList = SendFrameRepository.sendList;
                List<String> jsonList = SendFrameRepository.jsonList;
                System.out.println("指令剩余条数：" + sendList.size());
                boolean flag = false;
                for(int i = 0;i<sendList.size();i++){
                    Map.Entry<String,byte[]> entry = sendList.get(i).entrySet().iterator().next();
                    String jsonString = entry.getKey();//启动服务器时用
                    String sendMeterID = SendFrameRepository.getMeterID(jsonString);//启动服务器时用
                    //String sendMeterID = entry.getKey();//直接启动main方法时用
                    byte[] bytes = entry.getValue();
                    if(meterId.equals(sendMeterID)) {
                        byte[] response = new byte[bytes.length+4];
                        response[0] = 0x68;
                        response[1] = (byte)0xA2;
                        response[2] = (byte)bytes.length;
                        response[response.length-1] = 0x16;
                        for(int j=0;j<bytes.length;j++){
                            response[j+3] = bytes[j];;
                        }
                        IoBuffer ioBuffer = IoBuffer.wrap(response);
                        session.write(ioBuffer);

                        //启动服务器时用
                        for(int j=0;j<jsonList.size();i++){
                            if(jsonList.get(j) == jsonString){//必须用==
                                jsonList.remove(jsonString);
                                break;
                            }
                        }
                        flag = true;
                    }
                    if(flag){
                        break;
                    }
                }
                if(flag == false){
                    byte[] response = new byte[4];
                    response[0] = 0x68;
                    response[1] = (byte)0xA2;
                    response[2] = 0;
                    response[3] = 0x16;
                    IoBuffer ioBuffer = IoBuffer.wrap(response);
                    session.write(ioBuffer);
                }
                session.closeOnFlush();
            }
            if(Byte.toUnsignedInt(request[1]) == 0xA3){//回传
                int h = Byte.toUnsignedInt(request[15]);
                byte[] command = new byte[h];
                for(int i=0;i<command.length;i++){
                    command[i] = request[i+16];
                }
                /*
                  解析帧
                */
                // 测试，只有表具发过来的回传帧进队列
                String mqMessage = Hex.BytesToHexString(command);
                receiveProducer.dispatchMessage(mqMessage);
                byte[] response = new byte[3];
                response[0] = 0x68;
                response[1] = (byte)0xA3;
                response[2] = 0x16;
                IoBuffer ioBuffer = IoBuffer.wrap(response);
                session.write(ioBuffer);
                session.closeOnFlush();
            }
        }
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        //logger.info("server - 消息发出!");
        System.out.println("server - 消息发出...");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {
        //logger.info("server - session关闭连接断开...");
        System.out.println("server - session关闭,连接断开...");
    }

    @Override
    public void sessionCreated(IoSession session) throws Exception {
        //logger.info("server - session创建，建立连接...");
        System.out.println("server - session创建，建立连接...");
    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        //logger.info("server-服务端进入空闲状态...");
        System.out.println("server - session id="+session.getId()+" 进入空闲状态...");
        session.closeNow();
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        //logger.info("server-服务端与客户端连接打开...");
        System.out.println("server - 服务端与客户端连接打开...");
    }
}
