package org.whut.meterFrameManagement.communication.serverlistener;

import org.whut.meterFrameManagement.communication.server.FrameServer;
import org.whut.platform.fundamental.logger.PlatformLogger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by zhang_minzhong on 2017/1/3.
 */
public class FrameServerListener implements ServletContextListener {

    private static final PlatformLogger logger = PlatformLogger.getLogger(FrameServerListener.class);
    private Thread meterFrameThread;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        meterFrameThread = new Thread(new FrameServer());
        meterFrameThread.start();
        logger.info("server is started!");
    }

    //tomcat关闭时，关闭线程，释放端口
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("server is closed!");
    }
}
