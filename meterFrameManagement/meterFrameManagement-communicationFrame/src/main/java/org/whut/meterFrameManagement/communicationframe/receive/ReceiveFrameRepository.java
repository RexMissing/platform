package org.whut.meterFrameManagement.communicationframe.receive;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/2/28.
 */
public class ReceiveFrameRepository {

    public static List<ReceiveData> receiveList = new ArrayList<ReceiveData>();

    public static void write(ReceiveFrame rf) {
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
            for(int i=0;i<codeList.size();i++){
                FunCodeFrameId funCodeFrameId = new FunCodeFrameId();
                CFunction cFunction = codeList.get(i);
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
        receiveList.add(receiveData);
    }
}
