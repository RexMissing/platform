package org.whut.meterFrameManagement.business.web;

import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.communicationframe.test.FrameStore;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by zhang_minzhong on 2017/2/26.
 */
@Component
@Path("/frame")
public class MeterFrameWeb {
    @POST
    @Path("/sendFrame")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String sendFrame(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode){
        System.out.println("meterID长度:"+meterID.length());
        meterID = meterID.trim();
        funCode = funCode.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        System.out.println(meterID+":"+funCode);
        boolean bool = FrameStore.sotre(meterID,funCode);
        if(bool)
            return "{\"result\":\"指令存储成功\"}";
        else
            return
                    "{\"result\":\"指令存储失败\"}";
    }
}
