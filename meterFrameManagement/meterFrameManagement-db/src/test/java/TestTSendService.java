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
    }
}
