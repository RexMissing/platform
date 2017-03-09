package org.whut.meterFrameManagement.communicationframe.send;

import org.codehaus.jackson.map.ObjectMapper;
import org.whut.meterFrameManagement.communicationframe.entity.*;
import org.whut.meterFrameManagement.communicationframe.enums.ValveCtrStyle;
import org.whut.meterFrameManagement.communicationframe.frames.FrameFactory;
import org.whut.meterFrameManagement.communicationframe.key.TestKey;
import org.whut.meterFrameManagement.util.date.DateUtil;

import java.util.*;

/**
 * Created by zhang_minzhong on 2016/12/13.
 */
public class SendFrameRepository {

    //用于存储json数据的list
    public static List<String> jsonList = new ArrayList<String>();
    //用于存储发送指令的测试list
    public static List<Map<String,byte[]>> sendList = new ArrayList<Map<String, byte[]>>();


    //依次将jsonList中的数据放入sendList的map中，key为json字符串，value为加密后的字节数组
    public static boolean makeSendFrame() {
        sendList.removeAll(sendList);
        for(int i=0;i<jsonList.size();i++){
            String jsonString = jsonList.get(i);
            //从json字符串中解析出命令码
            String funCodeStr = getFunCode(jsonString);
            System.out.println("命令码："+funCodeStr);
            int funCode = Integer.parseInt(funCodeStr,16);
            try {
                switch (funCode){
                    case 0x01:
                        byte[] sendBytes = new byte[0];
                        ObjectMapper objectMapper = new ObjectMapper();
                        ValveControlParam valveControlParam = objectMapper.readValue(jsonString,ValveControlParam.class);
                        sendBytes = FrameFactory.getValveControlFrame(valveControlParam.getMeterID()
                                , valveControlParam.getKey(), valveControlParam.getFrameID()
                                ,ValveCtrStyle.允许开启,null, valveControlParam.getTimeCorrection());
                        Map<String,byte[]> map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x03:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        valveControlParam = objectMapper.readValue(jsonString,ValveControlParam.class);
                        sendBytes = FrameFactory.getValveControlFrame(valveControlParam.getMeterID()
                                , valveControlParam.getKey(), valveControlParam.getFrameID()
                                ,ValveCtrStyle.临时关闭,null, valveControlParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x05:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ReadMeterParam rmp = objectMapper.readValue(jsonString,ReadMeterParam.class);
                        sendBytes = FrameFactory.getMeterDataFrame(rmp.getMeterID(),rmp.getKey(),
                                rmp.getFrameID(),rmp.getDate(),rmp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x06:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        RunTimeGasParam rgp = objectMapper.readValue(jsonString,RunTimeGasParam.class);
                        sendBytes = FrameFactory.getMeterUseSetFrame(rgp.getMeterID(),rgp.getKey()
                                ,rgp.getFrameID(),rgp.getCzfs1(),rgp.getZyql(),rgp.getCzfs2(),rgp.getBzq()
                                ,rgp.getCzfs3(),rgp.getSzq(),rgp.getFs(),rgp.getLszqyl()
                                ,rgp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x07:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ChargeModeParam cmp = objectMapper.readValue(jsonString,ChargeModeParam.class);
                        sendBytes = FrameFactory.getSetChargingModeFrame(cmp.getMeterID(),cmp.getKey()
                                ,cmp.getFrameID(),cmp.getSfms());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x08:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ChangePriceParam cpp = objectMapper.readValue(jsonString,ChangePriceParam.class);
                        sendBytes = FrameFactory.getChangePriceFrame(cpp.getMeterID(),cpp.getKey()
                                ,cpp.getFrameID(),cpp.getP0(),cpp.getP1(),cpp.getP2(),cpp.getP3()
                                ,cpp.getA1(),cpp.getA2(),cpp.getA3(),cpp.getBeginDT(),cpp.getClen()
                                ,cpp.getAtDT(),cpp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x0A:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ChangeMoneyParam changeMoneyParam = objectMapper.readValue(jsonString,ChangeMoneyParam.class);
                        sendBytes = FrameFactory.getChangeMoneyFrame(changeMoneyParam.getMeterID()
                                ,changeMoneyParam.getKey(),changeMoneyParam.getFrameID()
                                ,changeMoneyParam.getMoney(),changeMoneyParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x0C:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ValveControlParam vcp = objectMapper.readValue(jsonString,ValveControlParam.class);
                        sendBytes = FrameFactory.getValveControlFrame(vcp.getMeterID(),vcp.getKey()
                                ,vcp.getFrameID(),ValveCtrStyle.强制关闭
                                ,vcp.getAtDT(),vcp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x0D:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ChangeServerNumParam csnp = objectMapper.readValue(jsonString,ChangeServerNumParam.class);
                        sendBytes = FrameFactory.getSetServerNoFrame(csnp.getMeterID(),csnp.getKey()
                                ,csnp.getFrameID(),csnp.getTimeCorrection(),csnp.getN()
                                ,csnp.getServerNum());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x0F:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        SetIPAndPortParam setIPPort = objectMapper.readValue(jsonString,SetIPAndPortParam.class);
                        sendBytes = FrameFactory.getSetIPAndPortFrame(setIPPort.getMeterID()
                                ,setIPPort.getKey(),setIPPort.getFrameID()
                                ,setIPPort.getServerIP(),setIPPort.getServerPort()
                                ,setIPPort.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x10:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        SetBeatHeartRateParam sbhrp = objectMapper.readValue(jsonString,SetBeatHeartRateParam.class);
                        sendBytes = FrameFactory.getSetBeatHeartRateFrame(sbhrp.getMeterID(),sbhrp.getKey()
                                ,sbhrp.getFrameID(),sbhrp.getRate(),sbhrp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x11:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        SetBeatHeartParam sbhp = objectMapper.readValue(jsonString,SetBeatHeartParam.class);
                        sendBytes = FrameFactory.getSetBeatHeartFrame(sbhp.getMeterID(), sbhp.getKey()
                                , sbhp.getFrameID(), sbhp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x12:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        Meter2ConcentratorParam m2cp = objectMapper.readValue(jsonString,Meter2ConcentratorParam.class);
                        sendBytes = FrameFactory.getSetMeter2ConcentratorFrame(m2cp.getConcentratorID()
                                ,m2cp.getMeterID(),m2cp.getKey()
                                ,m2cp.getFrameID(),m2cp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x16:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        SetBeatHeartParam setBeatHeartParam = objectMapper.readValue(jsonString,SetBeatHeartParam.class);
                        sendBytes = FrameFactory.getSetKeyFrame(setBeatHeartParam.getMeterID(),
                                setBeatHeartParam.getKey(),setBeatHeartParam.getFrameID(),
                                setBeatHeartParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x18:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        Meter2ConcentratorParam meter2ConcentratorParam = objectMapper.readValue(jsonString,Meter2ConcentratorParam.class);
                        sendBytes = FrameFactory.getRecallMeter2ConcentratorFrame(
                                meter2ConcentratorParam.getConcentratorID(),
                                meter2ConcentratorParam.getMeterID(),
                                meter2ConcentratorParam.getKey(),
                                meter2ConcentratorParam.getFrameID(),
                                meter2ConcentratorParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x1E:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ReadMeterParam readMeterParam = objectMapper.readValue(jsonString,ReadMeterParam.class);
                        sendBytes = FrameFactory.getMeterDataFrame(readMeterParam.getMeterID()
                                ,readMeterParam.getKey(), readMeterParam.getFrameID()
                                ,readMeterParam.getDate(),readMeterParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x20:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ValveControlParam valveControlParam1 = objectMapper.readValue(jsonString,ValveControlParam.class);
                        sendBytes = FrameFactory.getValveControlFrame(valveControlParam1.getMeterID()
                                ,valveControlParam1.getKey()
                                ,valveControlParam1.getFrameID(),ValveCtrStyle.定时关闭
                                ,valveControlParam1.getAtDT()
                                ,valveControlParam1.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x21:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ChangeOverDraftParam codp = objectMapper.readValue(jsonString,ChangeOverDraftParam.class);
                        sendBytes = FrameFactory.getChangeOverdraftFrame(codp.getMeterID(),codp.getKey()
                                ,codp.getFrameID(),codp.getType()
                                ,codp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x22:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        StartCycleParam startCycleParam = objectMapper.readValue(jsonString,StartCycleParam.class);
                        sendBytes = FrameFactory.getCommunicationUpCycleFrame(
                                startCycleParam.getMeterID(),
                                startCycleParam.getKey(),
                                startCycleParam.getFrameID(),
                                startCycleParam.getQdzq(),
                                startCycleParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x23:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        MeterOpenParam meterOpenParam = objectMapper.readValue(jsonString,MeterOpenParam.class);
                        sendBytes = FrameFactory.getMeterOpenFrame(meterOpenParam.getMeterID()
                                ,meterOpenParam.getKey(),meterOpenParam.getFrameID()
                                ,meterOpenParam.getMoney(),meterOpenParam.getP0()
                                ,meterOpenParam.getP1(),meterOpenParam.getP2()
                                ,meterOpenParam.getP3(),meterOpenParam.getA1()
                                ,meterOpenParam.getA2(),meterOpenParam.getA3()
                                ,meterOpenParam.getNkey(),meterOpenParam.getBeginDT()
                                ,meterOpenParam.getClen(),meterOpenParam.getCbr()
                                ,meterOpenParam.getBzql(),meterOpenParam.getSzql());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x25:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        SetCBRParam setCBRParam = objectMapper.readValue(jsonString,SetCBRParam.class);
                        sendBytes = FrameFactory.getMeterSetCBRFrame(setCBRParam.getMeterID(),
                                setCBRParam.getKey(),setCBRParam.getFrameID(),
                                setCBRParam.getTimeCorrection(),setCBRParam.getCbr());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x26:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ReadMeterTimerParam rmtp = objectMapper.readValue(jsonString,ReadMeterTimerParam.class);
                        sendBytes = FrameFactory.getTimerDataFrame(rmtp.getMeterID(),rmtp.getKey()
                                ,rmtp.getFrameID(),rmtp.getAtDT()
                                ,rmtp.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x27:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ReadMeterTimerParam readMeterTimerParam = objectMapper.readValue(jsonString,ReadMeterTimerParam.class);
                        sendBytes = FrameFactory.getTimerDataOfChangePriceFrame(
                                readMeterTimerParam.getMeterID()
                                , readMeterTimerParam.getKey()
                                , readMeterTimerParam.getFrameID()
                                , readMeterTimerParam.getAtDT()
                                , readMeterTimerParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x29:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        SetBeatHeartParam setBeatHeartParam1 = objectMapper.readValue(jsonString,SetBeatHeartParam.class);
                        sendBytes = FrameFactory.getReadCycleInfoFrame(
                                setBeatHeartParam1.getMeterID(),
                                setBeatHeartParam1.getKey(),
                                setBeatHeartParam1.getFrameID(),
                                setBeatHeartParam1.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x2B:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        ChangePriceParam changePriceParam = objectMapper.readValue(jsonString,ChangePriceParam.class);
                        sendBytes = FrameFactory.getChangePriceFrame(
                                changePriceParam.getMeterID(),changePriceParam.getKey()
                                ,changePriceParam.getFrameID(),changePriceParam.getP0()
                                ,changePriceParam.getP1(),changePriceParam.getP2(),changePriceParam.getP3()
                                ,changePriceParam.getA1(),changePriceParam.getA2(),changePriceParam.getA3()
                                ,changePriceParam.getBeginDT(),changePriceParam.getClen()
                                ,changePriceParam.getAtDT(),changePriceParam.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    case 0x2E:
                        sendBytes = new byte[0];
                        objectMapper = new ObjectMapper();
                        NotifyTXZTParam nTXZTP = objectMapper.readValue(jsonString,NotifyTXZTParam.class);
                        sendBytes = FrameFactory.getNotifyTXZTFrame(nTXZTP.getMobileID()
                                ,nTXZTP.getMeterID(),nTXZTP.getKey()
                                ,nTXZTP.getFrameID(),nTXZTP.getTxzt()
                                ,nTXZTP.getTimeCorrection());
                        map = new HashMap<String, byte[]>();
                        map.put(jsonString,sendBytes);
                        sendList.add(map);
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    /*public static void setSendList(String funCodeStr){
        sendList.removeAll(sendList);
        Map<String,byte[]> map = new HashMap<String, byte[]>();
        byte funCode = (byte) Integer.parseInt(funCodeStr,16);
        map.put(MeterID.METERID,getSendFrame(MeterID.METERID,funCode));
        sendList.add(map);
    }*/

    public static byte[] getSendFrame(String meterID,byte funCode) {
        String keyStr = TestKey.KEYSTR;
        System.out.println("密钥字符串："+keyStr);
        Date date = DateUtil.createDate("2016-9-1 00:00:00");
        byte[] sendBytes = new byte[0];
        switch (funCode){
            case 0x01:
                sendBytes = FrameFactory.getValveControlFrame(meterID, keyStr, (byte) 1, ValveCtrStyle.允许开启, null, 123);
                break;
            case 0x02:
                break;
            case 0x03:
                sendBytes = FrameFactory.getValveControlFrame(meterID, keyStr, (byte) 1, ValveCtrStyle.临时关闭, null, 0);
                break;
            case 0x04:
                //rst = "更新表内剩余气量";
                //rst = "";
                break;
            case 0x05:
                //rst = "读取表具数据";
                sendBytes = FrameFactory.getMeterDataFrame(meterID, keyStr, (byte) 1, null,180000);
                break;
            case 0x06:
                sendBytes = FrameFactory.getMeterUseSetFrame(meterID,keyStr,(byte)1,(byte)0,600,(byte)0,200,(byte)0,300,(byte)0,23450,180000);
                break;
            case 0x07:
                sendBytes = FrameFactory.getSetChargingModeFrame(meterID,keyStr,(byte)1,(byte)0xDF);
                break;
            case 0x08:
                //rst = "更新单价";
                sendBytes = FrameFactory.getChangePriceFrame(meterID, keyStr, (byte) 1, (double) 2, 2.5, (double) 3, 3.5,
                        200, 300, 400, date, (byte) 30, null, 0);
                break;
            case 0x09:
                //rst = "";
                break;
            case 0x0A:
                sendBytes = FrameFactory.getChangeMoneyFrame(meterID, keyStr, (byte)1, (double) 1000, 0);
                break;
            case 0x0B:
                //rst = "初始化表具数据";
                break;
            case 0x0C:
                //rst = "强制关闭表具阀门";
                sendBytes = FrameFactory.getValveControlFrame(meterID, keyStr, (byte) 1, ValveCtrStyle.强制关闭, null, 0);
                break;
            case 0x0D:
                //rst = "设置表内服务器通讯号码";
                sendBytes = FrameFactory.getSetServerNoFrame(meterID,keyStr,(byte)1,18000,1,"16666666666");
                break;
            case 0x0E:
                //rst = "报告换表数据";
                break;
            case 0x0F:
                //rst = "设置服务器IP以及端口号";
                sendBytes = FrameFactory.getSetIPAndPortFrame(meterID,keyStr,(byte)1,"127.0.0.1","3535",0);
                break;
            case 0x10:
                //rst = "设置心跳包频率";
                sendBytes = FrameFactory.getSetBeatHeartRateFrame(meterID,keyStr,(byte)1,60,0);
                break;
            case 0x11:
                //rst = "心跳包";
                sendBytes = FrameFactory.getSetBeatHeartFrame(meterID,keyStr,(byte)1,0);
                break;
            case 0x12:
                //rst = "设置中继器所辖表具";
                sendBytes = FrameFactory.getSetMeter2ConcentratorFrame(meterID,"1049721501423",keyStr,(byte)1,0);
                break;
            case 0x13:
                //rst = "报告设备安装地理定位信息(PDA)";

                break;
            case 0x14:
                //rst = "报告设备安装地点(PDA)";
                break;
            case 0x15:
                //rst = "报告表具所属中继器(PDA)";
                break;
            case 0x16:
                //rst = "设置密钥";
                sendBytes = FrameFactory.getSetKeyFrame(meterID,keyStr,(byte)1,0);
                break;
            case 0x17:
                //rst = "报告设备SIM卡号(PDA)";
                break;
            case 0x18:
                //rst = "撤销中继器所辖表具";
                sendBytes = FrameFactory.getRecallMeter2ConcentratorFrame(meterID,"1049721501423",keyStr,(byte)1,0);
                break;
            case 0x19:
                //rst = "表具上报电池电压变化";
                break;
            case 0x1A:
                //rst = "表具上报系统状态";
                break;
            case 0x1B:
                //rst = "表具上报定时抄表数据";
                break;
            case 0x1C:
                //rst = "表具上报定时更新单价";
                break;
            case 0x1D:
                //rst = "表具上报定时关闭阀门情况";
                break;
            case 0x1E:
                //rst = "设置表具定时抄表时间";
                sendBytes = FrameFactory.getMeterDataFrame(meterID,keyStr,(byte)1,date,0);
                break;
            case 0x1F:
                //rst = "设置表具定时更新单价";
                break;
            case 0x20:
                //rst = "设置表具定时关闭阀门";
                sendBytes = FrameFactory.getValveControlFrame(meterID, keyStr, (byte) 1, ValveCtrStyle.定时关闭, date, 0);
                break;
            case 0x21:
                //rst = "设置表具透支模式";
                sendBytes = FrameFactory.getChangeOverdraftFrame(meterID, keyStr, (byte) 1,0,0);
                break;
            case 0x22:
                //rst = "设置表具通讯模块启动周期";
                sendBytes = FrameFactory.getCommunicationUpCycleFrame(meterID, keyStr, (byte) 1,12,0);
                break;
            case 0x23:
                //rst = "表具开通";
                sendBytes = FrameFactory.getMeterOpenFrame(meterID, keyStr, (byte) 1, (double) 1000,
                        (double) 2, (double) 2.5, (double) 3, (double) 3.5, 200, 300, 400,
                        keyStr, date, (byte) 30, (byte) 28, 200, 450);;
                break;
            case 0x24:
                //rst = "表具开通";
                break;
            case 0x25:
                //rst = "设置表具抄表日";
                sendBytes = FrameFactory.getMeterSetCBRFrame(meterID, keyStr, (byte) 1,0,28);
                break;
            case 0x26:
                //rst = "读取表具定时抄表数据";
                sendBytes = FrameFactory.getTimerDataFrame(meterID, keyStr, (byte) 1,date,0);
                break;
            case 0x27:
                //rst = "读取表具定时更新单价数据";
                sendBytes = FrameFactory.getTimerDataOfChangePriceFrame(meterID, keyStr, (byte) 1,date,0);
                break;
            case 0x28:
                //rst = "清除表具故障标识";
                sendBytes = FrameFactory.getClearErrorMarkFrame(meterID, keyStr, (byte) 1,0);
                break;
            case 0x29:
                //rst = "读取表具历史周期量";
                sendBytes = FrameFactory.getReadCycleInfoFrame(meterID, keyStr, (byte) 1,0);
                break;
            case 0x2A:
                //rst = "表具校准时间";

            case 0x2B:
                sendBytes = FrameFactory.getChangePriceFrame(meterID, keyStr, (byte) 1, (double) 2, 2.5, (double) 3, 3.5,
                        200, 300, 400, date, (byte) 30, DateUtil.createDate("2016-12-22 00:00:00"), 0);
                break;
            case 0x2C:
                //rst = "报告安装SIM卡序号(PDA)";
                break;
            case 0x2D:
                //rst = "报告表具恢复正常(PDA)";
                break;
            case 0x2E:
                //rst = "通知（移动设备）现场设备通信状态";
                sendBytes = FrameFactory.getNotifyTXZTFrame(meterID,"1049721501423",keyStr,(byte)1,0,0);
                break;
            case 0x2F:
                //rst = "通知设备服务器密钥";
                //message = FrameFactory
                break;
            case 0x30:
                //rst = "表具更换SIM卡上报(PDA)";
                break;
            default:
                //rst = "";
                break;
        }
        return sendBytes;
    }

    /**
     * 从json字符串中解析出命令码
     * @param json
     * @return
     */
    public static String getFunCode(String json){
        String indexStr = "\"funCode\":\"";
        int funCodeIndex = json.indexOf(indexStr)+indexStr.length();
        //System.out.println("funCode位置："+funCodeIndex);
        String funCodeStr = json.substring(funCodeIndex,funCodeIndex+2);
        return funCodeStr;
    }

    /**
     * 从json字符串中解析出表号
     * @param json
     * @return
     */
    public static String getMeterID(String json) {
        String indexStr = "\"meterID\":\"";
        int meterIDIndex = json.indexOf(indexStr)+indexStr.length();
        String meterID = json.substring(meterIDIndex,meterIDIndex+13);
        return meterID;
    }
}
