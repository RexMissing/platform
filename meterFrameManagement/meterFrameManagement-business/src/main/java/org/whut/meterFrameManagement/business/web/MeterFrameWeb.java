package org.whut.meterFrameManagement.business.web;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.communicationframe.entity.*;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveData;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrameRepository;
import org.whut.meterFrameManagement.MQ.send.SendProducer;
import org.whut.meterFrameManagement.util.date.DateUtil;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
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
        rgp.setCzfs1((byte) 2);
        rgp.setZyql(345);
        rgp.setCzfs2((byte) 0);
        rgp.setBzq(100);
        rgp.setCzfs3((byte) 0);
        rgp.setSzq(200);
        rgp.setFs((byte) 0);
        rgp.setLszqyl(567);
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
        cpp.setP0(2);
        cpp.setP1(2.5);
        cpp.setP2(3);
        cpp.setP3(3.5);
        cpp.setA1(200);
        cpp.setA2(300);
        cpp.setA3(400);
        cpp.setBeginDT(DateUtil.createDate("2016-9-1 00:00:00"));
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
        cmp.setFrameID((byte) 1);
        cmp.setKey(meterKey);
        cmp.setMoney(1234.5);
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

    @POST
    @Path("/setKey")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String setKey(@FormParam("meterID")String meterID,@FormParam("funCode")String funCode,@FormParam("meterKey")String meterKey){
        meterID = meterID.trim();
        if(meterID.length()!=13)
            return "{\"result\":\"表号不正确\"}";
        funCode = funCode.trim();
        SetBeatHeartParam setBeatHeartParam = new SetBeatHeartParam();
        setBeatHeartParam.setMeterID(meterID);
        setBeatHeartParam.setFunCode(funCode);
        setBeatHeartParam.setFrameID((byte) 1);
        setBeatHeartParam.setKey(meterKey);
        setBeatHeartParam.setTimeCorrection(123);
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "[]";
        try {
            s = objectMapper.writeValueAsString(setBeatHeartParam);
            sendProducer.dispatchMessage(s);
        } catch (Exception e) {
            return "{\"result\":\"指令发送失败\"}";
        }
        return "{\"result\":\"指令发送成功\"}";
    }

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
        readMeterParam.setFrameID((byte) 1);
        readMeterParam.setKey(meterKey);
        readMeterParam.setDate(new Date());
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
        vcp.setFrameID((byte) 1);
        vcp.setKey(meterKey);
        vcp.setType(4);//4定时关阀门
        vcp.setAtDT(new Date());
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
        meterOpenParam.setA1(200);
        meterOpenParam.setA2(300);
        meterOpenParam.setA3(400);
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
        cpp.setFrameID((byte) 1);
        cpp.setKey(meterKey);
        //(double) 2, 2.5, (double) 3, 3.5,
        //200, 300, 400, date, (byte) 30, null,
        cpp.setP0(2);
        cpp.setP1(2.5);
        cpp.setP2(3);
        cpp.setP3(3.5);
        cpp.setA1(200);
        cpp.setA2(300);
        cpp.setA3(400);
        cpp.setBeginDT(DateUtil.createDate("2016-9-1 00:00:00"));
        cpp.setClen((byte) 30);
        cpp.setAtDT(new Date());
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




    //查询统一回传数据
    @POST
    @Path("/getReceiveData")
    @Produces(MediaType.APPLICATION_JSON+";charset=UTF-8")
    public String getReceiveData(){
        List<ReceiveData> list = ReceiveFrameRepository.receiveList;
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
