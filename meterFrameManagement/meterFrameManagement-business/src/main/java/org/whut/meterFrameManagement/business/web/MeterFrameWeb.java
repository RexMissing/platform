package org.whut.meterFrameManagement.business.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.communicationframe.query.ReceiveData;
import org.whut.meterFrameManagement.communicationframe.query.ReceiveFrameStore;
import org.whut.meterFrameManagement.mq.send.ProducerService;
import org.whut.meterFrameManagement.protocol.test.ValveControl;

import javax.jms.Destination;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/2/26.
 */
@Component
@Path("/frame")
public class MeterFrameWeb {

    @Autowired
    private ProducerService sendProducer;
    @Autowired
    private Destination queueDestination;

    @POST
    @Path("/allowValveOpen")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String sendFrame(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ValveControl valveControl = new ValveControl();
        valveControl.setMeterID(meterID);
        valveControl.setFunCode((byte) Integer.parseInt(funCode, 16));
        valveControl.setFrameID((byte) 1);
        valveControl.setKey("0102030405060708090A0B0C0D0E0F10");
        valveControl.setAtDT(null);
        valveControl.setType(1);//1允许开启
        valveControl.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(valveControl);
            sendProducer.sendMessage(queueDestination,s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }



    @POST
    @Path("/getReceiveData")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String getReceiveData(){
        List<ReceiveData> list = ReceiveFrameStore.receiveList;
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            System.out.println("list长度："+list.size());
            s = objectMapper.writeValueAsString(list);
            System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
