import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.whut.meterFrameManagement.db.business.ReceiveFrameBusiness;
import org.whut.meterFrameManagement.db.entity.TReceive;
import org.whut.meterFrameManagement.db.entity.TSend;
import org.whut.meterFrameManagement.db.service.TReceiveService;
import org.whut.meterFrameManagement.db.service.TSendService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/28.
 */
public class TestTSendService {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/db-applicationContext.xml");
        //TSendService tSendService = (TSendService)context.getBean("tSendService");
        //tSendService.addSendFrame("123","456",new Timestamp(new Date().getTime()));
        ReceiveFrameBusiness receiveFrameBusiness = (ReceiveFrameBusiness) context.getBean("receiveFrameBusiness");
        receiveFrameBusiness.addReceiveFrame("123","456",new Timestamp(new Date().getTime()));
        List<TReceive> receiveList = receiveFrameBusiness.getAllReceiveFrame();
        for(int i=0;i<receiveList.size();i++){
            TReceive tReceive = receiveList.get(i);
            System.out.println(tReceive.getId()+" "+tReceive.getMeterID()+" "+tReceive.getReceiveFrame()+" "+tReceive.getReceiveDate());
        }
    }
}
