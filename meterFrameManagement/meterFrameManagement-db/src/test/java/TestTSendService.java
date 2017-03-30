import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.whut.meterFrameManagement.db.business.ReceiveFrameBusiness;
import org.whut.meterFrameManagement.db.business.SendFrameBusiness;
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
        SendFrameBusiness sendFrameBusiness = (SendFrameBusiness) context.getBean("sendFrameBusiness");
        /*TSend tSend = new TSend();
        tSend.setMeterID("123");
        tSend.setFunCode(0x01);
        tSend.setFrameID(1);
        tSend.setSendFrame("abc");
        tSend.setSendDate(new Timestamp(new Date().getTime()));
        tSend.setSent(false);
        sendFrameBusiness.addSendFrame(tSend);*/
        int i = sendFrameBusiness.getLastFrameID("0120151212163",0x0A);
        System.out.println(i);
    }
}
