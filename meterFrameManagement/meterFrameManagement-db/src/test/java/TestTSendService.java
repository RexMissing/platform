import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.whut.meterFrameManagement.db.service.FrameKeyService;

/**
 * Created by zhang_minzhong on 2017/3/28.
 */
public class TestTSendService {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/db-applicationContext.xml");
        FrameKeyService frameKeyService = (FrameKeyService) context.getBean("frameKeyService");
        frameKeyService.updatekey("0120151212163", "640836FBC4A6FD68431AE03CF44C0232");
        //System.out.println(frameKeyService.getKey("0120151212163"));
        //SendFrameBusiness sendFrameBusiness = (SendFrameBusiness) context.getBean("sendFrameBusiness");
        /*TSend tSend = new TSend();
        tSend.setMeterID("123");
        tSend.setFunCode(0x01);
        tSend.setFrameID(1);
        tSend.setSendFrame("abc");
        tSend.setSendDate(new Timestamp(new Date().getTime()));
        tSend.setSent(false);
        sendFrameBusiness.addSendFrame(tSend);*/
        //int i = sendFrameBusiness.getLastFrameID("0120151212163",0x0A);
        //System.out.println(i);
    }
}
