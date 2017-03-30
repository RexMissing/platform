package org.whut.meterFrameManagement.business.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.communicationframe.convert.*;
import org.whut.meterFrameManagement.communicationframe.key.TestKey;
import org.whut.meterFrameManagement.communicationframe.receive.*;
import org.whut.meterFrameManagement.MQ.send.SendProducer;
//import org.whut.meterFrameManagement.communicationframe.receive.ReceiveData;
import org.whut.meterFrameManagement.db.business.ReceiveFrameBusiness;
import org.whut.meterFrameManagement.db.entity.TReceive;
import org.whut.meterFrameManagement.util.hex.Hex;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/2/26.
 */
@Component
@Path("/frame")
public class MeterFrameWeb {

    @Autowired
    private SendProducer sendProducer;

    //01
    @POST
    @Path("/allowValveOpen")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String allowValveOpen(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ValveControlParam valveControlParam = new ValveControlParam();
        valveControlParam.setMeterID(meterID);
        valveControlParam.setFunCode(funCode);
        valveControlParam.setFrameID((byte) 1);
        valveControlParam.setKey(meterKey);
        System.out.println("密钥：" + meterKey);
        valveControlParam.setAtDT(null);
        valveControlParam.setType(1);//1允许开启
        valveControlParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(valveControlParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //03
    @POST
    @Path("/temporaryClose")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String temporaryClose(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ValveControlParam valveControlParam = new ValveControlParam();
        valveControlParam.setMeterID(meterID);
        valveControlParam.setFunCode(funCode);
        valveControlParam.setFrameID((byte) 1);
        valveControlParam.setKey(meterKey);
        System.out.println("密钥：" + meterKey);
        valveControlParam.setAtDT(null);
        valveControlParam.setType(2);//2临时关闭
        valveControlParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(valveControlParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //05
    @POST
    @Path("/immediatelyRead")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String immediatelyRead(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ReadMeterParam readMeterParam = new ReadMeterParam();
        readMeterParam.setMeterID(meterID);
        readMeterParam.setFunCode(funCode);
        readMeterParam.setFrameID((byte) 1);
        readMeterParam.setKey(meterKey);
        readMeterParam.setDate(null);
        readMeterParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(readMeterParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //06
    @POST
    @Path("/setRunTimeGas")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setRunTimeGas(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        RunTimeGasParam rgp = new RunTimeGasParam();
        rgp.setMeterID(meterID);
        rgp.setFunCode(funCode);
        rgp.setFrameID((byte) 1);
        rgp.setKey(meterKey);
        rgp.setCzfs1((byte) 0);
        rgp.setZyql(100);
        rgp.setCzfs2((byte) 2);
        rgp.setBzq(26);
        rgp.setCzfs3((byte) 2);
        rgp.setSzq(30);
        rgp.setFs((byte) 0);
        rgp.setLszqyl(new byte[24]);
        rgp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(rgp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //07
    @POST
    @Path("/setChargeMode")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setChargeMode(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ChargeModeParam cmp = new ChargeModeParam();
        cmp.setMeterID(meterID);
        cmp.setFunCode(funCode);
        cmp.setFrameID((byte) 1);
        cmp.setKey(meterKey);
        cmp.setSfms((byte)0xDF);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(cmp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //08
    @POST
    @Path("/changePrice")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String changePrice(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ChangePriceParam cpp = new ChangePriceParam();
        cpp.setMeterID(meterID);
        cpp.setFunCode(funCode);
        cpp.setFrameID((byte) 1);
        cpp.setKey(meterKey);
        //(double) 2, 2.5, (double) 3, 3.5,
        //200, 300, 400, date, (byte) 30, null,
        cpp.setP0(2.7);
        cpp.setP1(3.1);
        cpp.setP2(3.5);
        cpp.setP3(3.8);
        cpp.setA1(20);
        cpp.setA2(30);
        cpp.setA3(40);
        cpp.setBeginDT(new Date());
        cpp.setClen((byte) 30);
        cpp.setAtDT(null);
        cpp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(cpp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //0A
    @POST
    @Path("/changeMoney")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String changeMoney(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ChangeMoneyParam cmp = new ChangeMoneyParam();
        cmp.setMeterID(meterID);
        cmp.setFunCode(funCode);
        cmp.setFrameID((byte) 7);
        cmp.setKey(meterKey);
        cmp.setMoney(234);
        cmp.setCzfs(2);
        cmp.setHxbj(0);
        cmp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(cmp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //0C
    @POST
    @Path("/forceClose")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String forceClose(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ValveControlParam vcp = new ValveControlParam();
        vcp.setMeterID(meterID);
        vcp.setFunCode(funCode);
        vcp.setFrameID((byte) 1);
        vcp.setKey(meterKey);
        vcp.setType(3);//3强制关阀门
        vcp.setAtDT(null);
        vcp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(vcp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //0D
    @POST
    @Path("/changeServerNum")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String changeServerNum(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ChangeServerNumParam csnp = new ChangeServerNumParam();
        csnp.setMeterID(meterID);
        csnp.setFunCode(funCode);
        csnp.setFrameID((byte) 1);
        csnp.setKey(meterKey);
        csnp.setN(1);
        csnp.setServerNum("1234567890123");
        csnp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(csnp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //0F
    @POST
    @Path("/setIPAndPort")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setIPAndPort(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        SetIPAndPortParam setIPAndPortParam = new SetIPAndPortParam();
        setIPAndPortParam.setMeterID(meterID);
        setIPAndPortParam.setFunCode(funCode);
        setIPAndPortParam.setFrameID((byte) 1);
        setIPAndPortParam.setKey(meterKey);
        setIPAndPortParam.setServerIP("127.0.0.1");
        setIPAndPortParam.setServerPort("64329");
        setIPAndPortParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(setIPAndPortParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //10
    @POST
    @Path("/SetBeatHeartRate")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String SetBeatHeartRate(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        SetBeatHeartRateParam sbhrp = new SetBeatHeartRateParam();
        sbhrp.setMeterID(meterID);
        sbhrp.setFunCode(funCode);
        sbhrp.setFrameID((byte) 1);
        sbhrp.setKey(meterKey);
        sbhrp.setRate(60);
        sbhrp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(sbhrp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //11
    @POST
    @Path("/setBeatHeart")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setBeatHeart(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        SetBeatHeartParam sbhp = new SetBeatHeartParam();
        sbhp.setMeterID(meterID);
        sbhp.setFunCode(funCode);
        sbhp.setFrameID((byte) 1);
        sbhp.setKey(meterKey);
        sbhp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(sbhp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //12
    @POST
    @Path("/setMeter2Concentrator")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setMeter2Concentrator(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        Meter2ConcentratorParam m2cp = new Meter2ConcentratorParam();
        m2cp.setMeterID(meterID);
        m2cp.setFunCode(funCode);
        m2cp.setFrameID((byte) 1);
        m2cp.setKey(meterKey);
        m2cp.setConcentratorID("1023548697214");
        m2cp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(m2cp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //16
    @POST
    @Path("/setKey")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setKey(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        SetKeyParam setKeyParam = new SetKeyParam();
        setKeyParam.setMeterID(meterID);
        setKeyParam.setFunCode(funCode);
        setKeyParam.setFrameID((byte) 2);
        setKeyParam.setKey(meterKey);
        setKeyParam.setnKey(meterKey);
        setKeyParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(setKeyParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //18
    @POST
    @Path("/recallMeter2Concentrator")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String recallMeter2Concentrator(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        Meter2ConcentratorParam meter2ConcentratorParam = new Meter2ConcentratorParam();
        meter2ConcentratorParam.setMeterID(meterID);
        meter2ConcentratorParam.setFunCode(funCode);
        meter2ConcentratorParam.setFrameID((byte) 1);
        meter2ConcentratorParam.setKey(meterKey);
        meter2ConcentratorParam.setConcentratorID("1023548697214");
        meter2ConcentratorParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(meter2ConcentratorParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //1E
    @POST
    @Path("/regularRead")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String regularRead(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ReadMeterParam readMeterParam = new ReadMeterParam();
        readMeterParam.setMeterID(meterID);
        readMeterParam.setFunCode(funCode);
        readMeterParam.setFrameID((byte) 5);
        readMeterParam.setKey(meterKey);
        readMeterParam.setDate(new Date(new Date().getTime()+120000));
        readMeterParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(readMeterParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //1F
    @POST
    @Path("/changePriceAtTime")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String changePriceAtTime(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ChangePriceParam cpp = new ChangePriceParam();
        cpp.setMeterID(meterID);
        cpp.setFunCode(funCode);
        cpp.setFrameID((byte) 8);
        cpp.setKey(meterKey);
        //(double) 2, 2.5, (double) 3, 3.5,
        //200, 300, 400, date, (byte) 30, null,
        cpp.setP0(2.7);
        cpp.setP1(3);
        cpp.setP2(3.5);
        cpp.setP3(4.5);
        cpp.setA1(25);
        cpp.setA2(35);
        cpp.setA3(45);
        cpp.setBeginDT(new Date(new Date().getTime()+120000));
        cpp.setClen((byte) 30);
        cpp.setAtDT(new Date(new Date().getTime()+90000));
        cpp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(cpp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //20
    @POST
    @Path("/timingClose")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String timingClose(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ValveControlParam vcp = new ValveControlParam();
        vcp.setMeterID(meterID);
        vcp.setFunCode(funCode);
        vcp.setFrameID((byte) 5);
        vcp.setKey(meterKey);
        vcp.setType(4);//4定时关阀门
        vcp.setAtDT(new Date(new Date().getTime()+90000));
        vcp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(vcp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //21
    @POST
    @Path("/changeOverdraft")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String changeOverdraft(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ChangeOverDraftParam codp = new ChangeOverDraftParam();
        codp.setMeterID(meterID);
        codp.setFunCode(funCode);
        codp.setFrameID((byte) 1);
        codp.setKey(meterKey);
        codp.setType(1);//透支3天
        codp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(codp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //22
    @POST
    @Path("/setStartCycle")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setStartCycle(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        StartCycleParam startCycleParam = new StartCycleParam();
        startCycleParam.setMeterID(meterID);
        startCycleParam.setFunCode(funCode);
        startCycleParam.setFrameID((byte) 1);
        startCycleParam.setKey(meterKey);
        startCycleParam.setQdzq(12);
        startCycleParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(startCycleParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //23
    @POST
    @Path("/meterOpen")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String meterOpen(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        MeterOpenParam meterOpenParam = new MeterOpenParam();
        meterOpenParam.setMeterID(meterID);
        meterOpenParam.setFunCode(funCode);
        meterOpenParam.setFrameID((byte) 1);
        meterOpenParam.setKey(meterKey);
        meterOpenParam.setMoney(1000);
        meterOpenParam.setP0(2);
        meterOpenParam.setP1(2.5);
        meterOpenParam.setP2(3);
        meterOpenParam.setP3(3.5);
        meterOpenParam.setA1(20);
        meterOpenParam.setA2(30);
        meterOpenParam.setA3(40);
        meterOpenParam.setNkey(meterKey);
        meterOpenParam.setBeginDT(new Date());
        meterOpenParam.setClen((byte) 30);
        meterOpenParam.setCbr((byte) 28);
        meterOpenParam.setBzql(200);
        meterOpenParam.setSzql(450);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(meterOpenParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //25
    @POST
    @Path("/setCBR")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setCBR(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        SetCBRParam scbrp = new SetCBRParam();
        scbrp.setMeterID(meterID);
        scbrp.setFunCode(funCode);
        scbrp.setFrameID((byte) 1);
        scbrp.setKey(meterKey);
        scbrp.setCbr(28);
        scbrp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(scbrp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //26
    @POST
    @Path("/readMeterTimer")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String readMeterTimer(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ReadMeterTimerParam rmtp = new ReadMeterTimerParam();
        rmtp.setMeterID(meterID);
        rmtp.setFunCode(funCode);
        rmtp.setFrameID((byte) 1);
        rmtp.setKey(meterKey);
        rmtp.setAtDT(new Date(new Date().getTime()+90000));
        rmtp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(rmtp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //27
    @POST
    @Path("/getTimerDataOfChangePrice")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String getTimerDataOfChangePrice(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        ReadMeterTimerParam rmtp = new ReadMeterTimerParam();
        rmtp.setMeterID(meterID);
        rmtp.setFunCode(funCode);
        rmtp.setFrameID((byte) 1);
        rmtp.setKey(meterKey);
        rmtp.setAtDT(new Date());
        rmtp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(rmtp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //29
    @POST
    @Path("/readCycleInfo")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String readCycleInfo(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        SetBeatHeartParam sbhp = new SetBeatHeartParam();
        sbhp.setMeterID(meterID);
        sbhp.setFunCode(funCode);
        sbhp.setFrameID((byte) 1);
        sbhp.setKey(meterKey);
        sbhp.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(sbhp);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

    //2E
    @POST
    @Path("/notifyTXZT")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String notifyTXZT(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        NotifyTXZTParam nTXZTP = new NotifyTXZTParam();
        nTXZTP.setMeterID(meterID);
        nTXZTP.setFunCode(funCode);
        nTXZTP.setFrameID((byte)1);
        nTXZTP.setKey(meterKey);
        nTXZTP.setMobileID("1234569870453");
        nTXZTP.setTxzt(0);
        nTXZTP.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(nTXZTP);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }



    @Autowired
    ReceiveFrameBusiness receiveFrameBusiness;
    //查询统一回传数据
    @POST
    @Path("/getReceiveData")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String getReceiveData(){
        //List<ReceiveData> list = ReceiveFrameRepository.receiveList;
        List<ReceiveData> showList = new ArrayList<ReceiveData>();
        List<TReceive> receiveList = receiveFrameBusiness.getAllReceiveFrame();
        for(int i=0;i<receiveList.size();i++){
            String receiveFrameString = receiveList.get(i).getReceiveFrame();
            ReceiveFrame rf =  new ReceiveFrame();
            byte[] command = Hex.hexStringToBytes(receiveFrameString,receiveFrameString.length()/2);
            rf.ParseFrom(command, TestKey.KEYSTR);
            ReceiveData receiveData = new ReceiveData();
            receiveData.setMeterID(rf.getMeterID());//表号
            int funCode = Byte.toUnsignedInt(rf.getFuncCode());
            receiveData.setFunCode(Integer.toHexString(funCode));//命令码
            receiveData.setFrameID(rf.getFrameID());//帧ID
            receiveData.setDirection(rf.getFrmDirection());//传送方向
            receiveData.setFrameResult(rf.getFrmResult());//回传帧结果
            MeterStatus meterStatus = rf.MeterST;
            receiveData.setXtztzj(Byte.toUnsignedInt(meterStatus.getXtzt()));//系统状态字节
            //阀门位置
            if(meterStatus.getFMWZ()==0)
                receiveData.setFmwz("开门态");
            else
                receiveData.setFmwz("关门态");
            //阀门位置错
            if(meterStatus.getFMCW()==0)
                receiveData.setFmcw("正常");
            else
                receiveData.setFmcw("出错");
            //计量传感器错
            if(meterStatus.getCGGZ()==0)
                receiveData.setCggz("正常");
            else
                receiveData.setCggz("出错");
            //透支标志
            if(meterStatus.getTZZT()==0)
                receiveData.setTzzt("正常");
            else
                receiveData.setTzzt("透支");
            //系统数据
            if(meterStatus.getXTSJC()==0)
                receiveData.setXtsjc("正常");
            else
                receiveData.setXtsjc("出错");

            if(funCode==0x3E||funCode==0x05||funCode==0x06||funCode==0x08||funCode==0x09||funCode==0x10||funCode==0x26||funCode==0x27) {
                receiveData.setRemainMoney(meterStatus.getRemainMoney());//剩余金额
                receiveData.setMeterRead(meterStatus.getMeterRead());//表止码
            }
            if(funCode==0x3E){
                receiveData.setPreSumAmount(meterStatus.getPresumamount());//上周期量
                receiveData.setPrice(meterStatus.getPrice());//单价
                receiveData.setAmount1(meterStatus.getAmount1());//阶梯起始1
                receiveData.setAmount2(meterStatus.getAmount2());//阶梯起始2
                receiveData.setAmount3(meterStatus.getAmount3());//阶梯起始3
                receiveData.setSumAmount(meterStatus.getSumamount());//本周期量
                receiveData.setMeterTime(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(meterStatus.getMeterTime()));//表具时间
                receiveData.setFunCount(rf.getFuncCount());//执行命令数
                List<FunCodeFrameId> funList = new ArrayList<FunCodeFrameId>();

                List<CFunction> codeList = rf.getAryFunc();
                for(int j=0;j<codeList.size();j++){
                    FunCodeFrameId funCodeFrameId = new FunCodeFrameId();
                    CFunction cFunction = codeList.get(j);
                    String temp = Integer.toHexString(Byte.toUnsignedInt(cFunction.getCode()));
                    String s = temp.length()==1?("0"+temp):temp;
                    funCodeFrameId.setFunCode(s);
                    int id = Byte.toUnsignedInt(cFunction.getFid());
                    funCodeFrameId.setFrameId(id);
                    if(cFunction.isSuccess())
                        funCodeFrameId.setSuccessOrFail("成功");
                    else
                        funCodeFrameId.setSuccessOrFail("失败");
                    funList.add(funCodeFrameId);
                }
                receiveData.setList(funList);
            }
            showList.add(receiveData);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            //System.out.println("list长度："+list.size());
            s = objectMapper.writeValueAsString(showList);
            //System.out.println(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s;
    }
}
