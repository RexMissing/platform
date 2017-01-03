package client;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;
import org.whut.meterFrameManagement.communication.codec.DataCodecFactory;

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
        ConnectFuture connectFuture = connector.connect(new InetSocketAddress("127.0.0.1",6868));
        //等待建立连接
        connectFuture.awaitUninterruptibly();
        System.out.println("连接成功");

        IoSession session = connectFuture.getSession();

       /* session.write("Thread---"+id);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }*/

        Scanner scanner = new Scanner(System.in);
        boolean quit = false;
        while(!quit){
            String s = scanner.nextLine();
            session.write(s);
            if(s.equalsIgnoreCase("quit"))
                quit = true;
        }
        if(session!=null){
            if(session.isConnected())
                session.getCloseFuture().awaitUninterruptibly();
        }
        connector.dispose(true);

    }
    static class ClientTread implements Runnable{
        private int id;
        public ClientTread(int id){
            this.id = id;
        }

        @Override
        public void run() {

        }
    }
}
