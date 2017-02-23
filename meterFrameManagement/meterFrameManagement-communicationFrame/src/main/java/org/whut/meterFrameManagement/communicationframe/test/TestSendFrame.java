package org.whut.meterFrameManagement.communicationframe.test;

import org.whut.meterFrameManagement.communicationframe.enums.ValveCtrStyle;
import org.whut.meterFrameManagement.communicationframe.frames.FrameFactory;
import org.whut.meterFrameManagement.util.date.DateUtil;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by zhang_minzhong on 2016/12/13.
 */
public class TestSendFrame {
    public static byte[] getSendFrame(byte funCode) {
        String keyStr = getKeyString();
        Date date = DateUtil.createDate("2016-9-1 00:00:00");
        byte[] sendBytes = new byte[0];
        switch (funCode){
            case 0x01:
                sendBytes = FrameFactory.getValveControlFrame("1049721501423", keyStr, (byte) 1, ValveCtrStyle.允许开启, null, 123);
                break;
            case 0x02:
                break;
            case 0x03:
                sendBytes = FrameFactory.getValveControlFrame("1049721501423", keyStr, (byte) 1, ValveCtrStyle.临时关闭, null, 0);
                break;
            case 0x04:
                //rst = "更新表内剩余气量";
                //rst = "";
                break;
            case 0x05:
                //rst = "读取表具数据";
                sendBytes = FrameFactory.getMeterDataFrame("1049721501423", keyStr, (byte) 1, null,180000);
                break;
            case 0x06:
                sendBytes = FrameFactory.getMeterUseSetFrame("1049721501423",keyStr,(byte)1,(byte)0,600,(byte)0,200,(byte)0,300,(byte)0,23450,180000);
                break;
            case 0x07:
                sendBytes = FrameFactory.getSetChargingModeFrame("1049721501423",keyStr,(byte)1,(byte)0xDF);
                break;
            case 0x08:
                //rst = "更新单价";
                sendBytes = FrameFactory.getChangePriceFrame("1049721501423", keyStr, (byte) 1, (double) 2, 2.5, (double) 3, 3.5,
                        200, 300, 400, date, (byte) 30, null, 0);
                break;
            case 0x09:
                //rst = "";
                break;
            case 0x0A:
                sendBytes = FrameFactory.getChangeMoneyFrame("1049721501423", keyStr, (byte)1, (double) 1000, 0);
                break;
            case 0x0B:
                //rst = "初始化表具数据";
                break;
            case 0x0C:
                //rst = "强制关闭表具阀门";
                sendBytes = FrameFactory.getValveControlFrame("1049721501423", keyStr, (byte) 1, ValveCtrStyle.强制关闭, null, 0);
                break;
            case 0x0D:
                //rst = "设置表内服务器通讯号码";
                sendBytes = FrameFactory.getSetServerNoFrame("1049721501423",keyStr,(byte)1,18000,1,"16666666666");
                break;
            case 0x0E:
                //rst = "报告换表数据";
                break;
            case 0x0F:
                //rst = "设置服务器IP以及端口号";
                sendBytes = FrameFactory.getSetIPAndPortFrame("1049721501423",keyStr,(byte)1,"127.0.0.1","6868",0);
                break;
            case 0x10:
                //rst = "设置心跳包频率";
                sendBytes = FrameFactory.getSetBeatHeartRateFrame("1049721501423",keyStr,(byte)1,60,0);
                break;
            case 0x11:
                //rst = "心跳包";
                sendBytes = FrameFactory.getSetBeatHeartFrame("1049721501423",keyStr,(byte)1,0);
                break;
            case 0x12:
                //rst = "设置中继器所辖表具";
                sendBytes = FrameFactory.getSetMeter2ConcentratorFrame("1049721500000","1049721501423",keyStr,(byte)1,0);
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
                sendBytes = FrameFactory.getSetKeyFrame("1049721501423",keyStr,(byte)1,0);
                break;
            case 0x17:
                //rst = "报告设备SIM卡号(PDA)";
                break;
            case 0x18:
                //rst = "撤销中继器所辖表具";
                sendBytes = FrameFactory.getRecallMeter2ConcentratorFrame("1049721500000","1049721501423",keyStr,(byte)1,0);
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
                sendBytes = FrameFactory.getMeterDataFrame("1049721501423",keyStr,(byte)1,date,0);
                break;
            case 0x1F:
                //rst = "设置表具定时更新单价";
                break;
            case 0x20:
                //rst = "设置表具定时关闭阀门";
                sendBytes = FrameFactory.getValveControlFrame("1049721501423", keyStr, (byte) 1, ValveCtrStyle.定时关闭, date, 0);
                break;
            case 0x21:
                //rst = "设置表具透支模式";
                sendBytes = FrameFactory.getChangeOverdraftFrame("1049721501423", keyStr, (byte) 1,0,0);
                break;
            case 0x22:
                //rst = "设置表具通讯模块启动周期";
                sendBytes = FrameFactory.getCommunicationUpCycleFrame("1049721501423", keyStr, (byte) 1,12,0);
                break;
            case 0x23:
                //rst = "表具开通";
                sendBytes = FrameFactory.getMeterOpenFrame("1049721501423", keyStr, (byte) 1, (double) 1000,
                        (double) 2, (double) 2.5, (double) 3, (double) 3.5, 200, 300, 400,
                        keyStr, date, (byte) 30, (byte) 28, 200, 450);;
                break;
            case 0x24:
                //rst = "表具开通";
                break;
            case 0x25:
                //rst = "设置表具抄表日";
                sendBytes = FrameFactory.getMeterSetCBRFrame("1049721501423", keyStr, (byte) 1,0,28);
                break;
            case 0x26:
                //rst = "读取表具定时抄表数据";
                sendBytes = FrameFactory.getTimerDataFrame("1049721501423", keyStr, (byte) 1,date,0);
                break;
            case 0x27:
                //rst = "读取表具定时更新单价数据";
                sendBytes = FrameFactory.getTimerDataOfChangePriceFrame("1049721501423", keyStr, (byte) 1,date,0);
                break;
            case 0x28:
                //rst = "清除表具故障标识";
                sendBytes = FrameFactory.getClearErrorMarkFrame("1049721501423", keyStr, (byte) 1,0);
                break;
            case 0x29:
                //rst = "读取表具历史周期量";
                sendBytes = FrameFactory.getReadCycleInfoFrame("1049721501423", keyStr, (byte) 1,0);
                break;
            case 0x2A:
                //rst = "表具校准时间";

            case 0x2B:
                sendBytes = FrameFactory.getChangePriceFrame("1049721501423", keyStr, (byte) 1, (double) 2, 2.5, (double) 3, 3.5,
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
                sendBytes = FrameFactory.getNotifyTXZTFrame("1049721500000","1049721501423",keyStr,(byte)1,0,0);
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

    public static List<Map<String,byte[]>> getFrames(){
        List<Map<String,byte[]>> list = new ArrayList<Map<String, byte[]>>();
        Map<String,byte[]> map1 = new HashMap<String, byte[]>();
        Map<String,byte[]> map2 = new HashMap<String, byte[]>();
        Map<String,byte[]> map3 = new HashMap<String, byte[]>();
        Map<String,byte[]> map4 = new HashMap<String, byte[]>();
        Map<String,byte[]> map5 = new HashMap<String, byte[]>();
        Map<String,byte[]> map6 = new HashMap<String, byte[]>();
        map1.put("1049721501423",TestSendFrame.getSendFrame((byte)0x01));
        map2.put("1049721501423",TestSendFrame.getSendFrame((byte)0x05));
        map3.put("1049721501423",TestSendFrame.getSendFrame((byte)0x06));
        map4.put("1049721501423",TestSendFrame.getSendFrame((byte)0x2B));
        map5.put("1049721501423",TestSendFrame.getSendFrame((byte)0x0A));
        map6.put("1049721501423",TestSendFrame.getSendFrame((byte)0x23));
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);
        list.add(map5);
        list.add(map6);
        return list;
    }
    public static String getKeyString(){
        String keyStr = "";
        for (int i = 1; i <= 16; i++) {
            String temp = Integer.toHexString(i);
            if (temp.length() < 2)
                temp = "0" + temp;
            keyStr += temp;
        }
        return keyStr;
    }

    //回传测试
    public static byte[] getReceiveFrame(byte funCode){
        String keyStr  =getKeyString();
        byte[] receiveBytes = new byte[0];
        switch (funCode){
            case (byte)0x81:
                receiveBytes = FrameFactory.getAllowOpenValveBackFrame("1049721501423",keyStr,(byte)1);
                break;
            case (byte)0x85:
                receiveBytes = FrameFactory.getMeterDataBackFrame("1049721501423",keyStr,(byte)1,234.5,400,2);
                break;
            case 0x3E:
                Date xtsj = new Date();
                Date begin = DateUtil.createDate("2000-01-01 00:00:00");
                long sub = xtsj.getTime() - begin.getTime();
                receiveBytes = FrameFactory.getUnifiedReturnFrame("1049721501423",keyStr,(byte)1,123.4,500,(byte)7,300,2.0,200,250,300,200,
                        sub, sub+1800000,3,129,1,130,2,131,3,0,0,0,0,0,0,0,0,0,0);
                break;
            default:
                break;
        }
        return receiveBytes;
    }
}
