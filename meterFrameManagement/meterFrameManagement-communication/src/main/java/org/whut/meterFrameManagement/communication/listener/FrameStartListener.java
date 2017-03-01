package org.whut.meterFrameManagement.communication.listener;

import org.whut.meterFrameManagement.communication.server.FrameServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by zhang_minzhong on 2017/2/26.
 */
public class FrameStartListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        new Thread(new FrameServer()).start();
    }

    //tomcat关闭时，关闭线程，释放端口
    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
