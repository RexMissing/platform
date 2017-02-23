package org.whut.meterFrameManagement.communication.server;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.whut.meterFrameManagement.communication.codec.DataCodecFactory;
import org.whut.meterFrameManagement.communicationframe.test.TestSendFrame;
import org.whut.platform.fundamental.logger.PlatformLogger;

import java.net.InetSocketAddress;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by zhang_minzhong on 2017/1/3.
 */
public class FrameServer implements Runnable {

    //public static final PlatformLogger logger = PlatformLogger.getLogger(FrameServer.class);
    //private static IoAcceptor acceptor;

    @Override
    public void run() {
        int port = 3535;//FundamentalConfigProvider.get("meterFrame.port");
        //logger.info("socket server listen:"+portString);
        System.out.println("socket server listen:"+port);
        try{
            IoAcceptor acceptor = new NioSocketAcceptor();
            // 添加一个日志过滤器
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            // 添加一个编码过滤器
            acceptor.getFilterChain().addLast("mycodec",
                    new ProtocolCodecFilter(new DataCodecFactory()));

            acceptor.getFilterChain().addLast("mythreadpool",new ExecutorFilter(100));
            // 绑定业务处理器,这段代码要在acceptor.bind()方法之前执行，因为绑定套接字之后就不能再做这些准备
            acceptor.setHandler(new FrameServerHandler());
            // 设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 120);
            // 绑定一个监听端口
            acceptor.bind(new InetSocketAddress(port));
        }catch (Exception e){
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 向每个客户端发送消息
     */
    /*public static void sendMessage(byte funCode){

        IoSession session;
        Map<Long,IoSession> conMap = acceptor.getManagedSessions();
        Iterator<Long> iterator = conMap.keySet().iterator();
        System.out.println("session个数："+conMap.size());
        while (iterator.hasNext()) {
            long key = iterator.next();
            session = conMap.get(key);
            byte[] bytes = TestSendFrame.getSendFrame(funCode);
            if(bytes.length>0){
                IoBuffer ioBuffer = IoBuffer.wrap(bytes);
                session.write(ioBuffer);
            }
        }
    }*/
    public static void main(String[] args) {
        FrameServer frameServer = new FrameServer();
        frameServer.run();
        /*while(true){
            System.out.print("输入命令码：");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            byte funCode = 0;
            try {
                funCode = (byte)Integer.parseInt(s,16);
            }catch (Exception e){
                continue;
            }
            sendMessage(funCode);
        }*/
    }
}
