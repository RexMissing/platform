import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.whut.meterFrameManagement.communicationframe.key.KeyManager;
import org.whut.meterFrameManagement.db.business.SendFrameBusiness;
import org.whut.meterFrameManagement.db.entity.TSend;
import org.whut.meterFrameManagement.db.service.FrameKeyService;

import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/28.
 */
public class TestTSendService {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/db-applicationContext.xml");
        SendFrameBusiness sendFrameBusiness = (SendFrameBusiness) context.getBean("sendFrameBusiness");
        List<TSend> list = sendFrameBusiness.getSendFrame("0120151212163");
        for(TSend tsend:list){
            System.out.println(tsend.getId()+" "+tsend.getMeterID()+" "+tsend.getSendFrame());
        }
    }
}
