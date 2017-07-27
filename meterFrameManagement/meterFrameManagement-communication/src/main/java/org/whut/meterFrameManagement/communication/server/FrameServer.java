package org.whut.meterFrameManagement.communication.server;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.executor.ExecutorFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.whut.meterFrameManagement.communication.codec.DataCodecFactory;

import java.net.InetSocketAddress;

/**
 * Created by zhang_minzhong on 2017/1/3.
 */
//用于main方法测试
public class FrameServer implements Runnable  {

    @Override
    public void run() {
        listen();
    }

    public void listen() {
        int port = 6601;//FundamentalConfigProvider.get("meterFrame.port");
        //logger.info("socket server listen:"+portString);
        System.out.println("socket server listen:"+port);
        try{
            IoAcceptor acceptor = new NioSocketAcceptor();
            // 添加一个日志过滤器
            acceptor.getFilterChain().addLast("logger", new LoggingFilter());
            // 添加一个编码过滤器
            acceptor.getFilterChain().addLast("mycodec",
                    new ProtocolCodecFilter(new DataCodecFactory()));
            //让事件处理在单独的线程进行
            acceptor.getFilterChain().addLast("mythreadpool",new ExecutorFilter());
            // 绑定业务处理器,这段代码要在acceptor.bind()方法之前执行，因为绑定套接字之后就不能再做这些准备
            acceptor.setHandler(new FrameServerHandler());
            // 设置读取数据的缓冲区大小
            acceptor.getSessionConfig().setReadBufferSize(2048);
            acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
            // 绑定一个监听端口
            acceptor.bind(new InetSocketAddress(port));
        }catch (Exception e){
            //logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

   /* public static void main(String[] args) {
        FrameServer frameServer = new FrameServer();
        frameServer.listen();
        while(true){
            System.out.print("输入命令码：");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            SendFrameRepository.setSendList(s);

        }
    }*/
}
