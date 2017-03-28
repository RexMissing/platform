import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.whut.meterFrameManagement.db.entity.TSend;
import org.whut.meterFrameManagement.db.service.TReceiveService;
import org.whut.meterFrameManagement.db.service.TSendService;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2017/3/28.
 */
public class Test {
    public static void main(String[] args) {
        String systemPath = System.getProperty("user.dir");
        String path = systemPath+"\\meterFrameManagement\\meterFrameManagement-db\\src\\main\\resources\\META-INF\\spring\\db-applicationContext.xml";
        System.out.println(path);
        ApplicationContext context = new FileSystemXmlApplicationContext(path);
        TSendService tSendService = (TSendService)context.getBean("tSendService");
        if (tSendService!=null){
            System.out.println("not null");
            TSend tSend = new TSend();
            tSend.setMeterID("123");
            tSend.setSendFrame("456");
            tSend.setSendDate(new Timestamp(new Date().getTime()));
            tSendService.addSendFrame(tSend);
        }
        TReceiveService tReceiveService = (TReceiveService)context.getBean("tReceiveService");
        if(tReceiveService!=null){
            System.out.println("not null");
        }
    }
}
