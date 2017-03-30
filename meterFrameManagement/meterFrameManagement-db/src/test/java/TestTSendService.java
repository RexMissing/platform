import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
<<<<<<< HEAD
import org.whut.meterFrameManagement.db.service.FrameKeyService;
=======
import org.whut.meterFrameManagement.db.business.ReceiveFrameBusiness;
import org.whut.meterFrameManagement.db.business.SendFrameBusiness;
import org.whut.meterFrameManagement.db.entity.TReceive;
import org.whut.meterFrameManagement.db.entity.TSend;
import org.whut.meterFrameManagement.db.service.TReceiveService;
import org.whut.meterFrameManagement.db.service.TSendService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
>>>>>>> 9d973009daac65b53fdc482f0bc8c6cdc5367508

/**
 * Created by zhang_minzhong on 2017/3/28.
 */
public class TestTSendService {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/db-applicationContext.xml");
<<<<<<< HEAD
        FrameKeyService frameKeyService = (FrameKeyService) context.getBean("frameKeyService");
        //frameKeyService.add2DB("0120151212163", "640836FBC4A6FD68431AE03CF44C0232");
        //System.out.println(frameKeyService.getKey("0120151212163"));
        frameKeyService.updatekey("0120151212163", "640836FBC4A6FD68431AE03CF44C0232");
        /*TSendService tSendService = (TSendService) context.getBean("tSendService");
        tSendService.addSendFrame("123","456",new Timestamp(new Date().getTime()));*/
        /*ReceiveFrameBusiness receiveFrameBusiness = (ReceiveFrameBusiness) context.getBean("receiveFrameBusiness");
        receiveFrameBusiness.addReceiveFrame("123","456",new Timestamp(new Date().getTime()));
        List<TReceive> receiveList = receiveFrameBusiness.getAllReceiveFrame();
        for(int i=0;i<receiveList.size();i++){
            TReceive tReceive = receiveList.get(i);
            System.out.println(tReceive.getId()+" "+tReceive.getMeterID()+" "+tReceive.getReceiveFrame()+" "+tReceive.getReceiveDate());
        }*/
=======
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
>>>>>>> 9d973009daac65b53fdc482f0bc8c6cdc5367508
    }
}
