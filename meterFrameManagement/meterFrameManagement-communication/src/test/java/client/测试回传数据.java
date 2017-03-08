package client;

import org.whut.meterFrameManagement.communicationframe.receive.CFunction;
import org.whut.meterFrameManagement.communicationframe.receive.MeterStatus;
import org.whut.meterFrameManagement.communicationframe.receive.ReceiveFrame;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/3.
 */
public class 测试回传数据 {
    public static void main(String[] args) {

        ReceiveFrame rf = new ReceiveFrame();
        String s = "6C4E41C8833CEB9F8E44328CC2E02D0179C988F599E836363740C5897611F3D015963D812E0242539C9D0E934536B5DE5B11C495632EEDB0611B0962ABE37A82";
        String key = "9370C3E2C4D289EC94E4735BC8A4C1EE";
        byte[] recBytes = Hex.hexStringToBytes(s,s.length()/2);
        System.out.print("加密后回传帧：");
        for(int i=0;i<recBytes.length;i++){
            System.out.print(Byte.toUnsignedInt(recBytes[i])+" ");
        }
        System.out.println();
        rf.ParseFrom(recBytes,key);
        MeterStatus meterStatus = rf.MeterST;

        int funCode = Byte.toUnsignedInt(rf.getFuncCode());
        System.out.println("命令码：" + Integer.toHexString(funCode));
        System.out.println("表号：" + meterStatus.getMeterID());//rf.getMeterID()
        System.out.println("帧id:"+Byte.toUnsignedInt(rf.getFrameID()));
        System.out.println("传送方向："+rf.getFrmDirection());
        System.out.println("帧的回传结果："+rf.getFrmResult());
        System.out.println("系统状态字节："+meterStatus.getXtzt());
        System.out.println("阀门位置："+meterStatus.getFMWZ());
        System.out.println("阀门位置错："+meterStatus.getFMCW());
        System.out.println("计量传感器错："+meterStatus.getCGGZ());
        System.out.println("透支标志："+meterStatus.getTZZT());
        System.out.println("系统数据错："+meterStatus.getXTSJC());
        if(funCode==0x3E||funCode==0x05||funCode==0x06||funCode==0x08||funCode==0x09||funCode==0x10||funCode==0x26||funCode==0x27) {
            System.out.println("剩余金额：" + meterStatus.getRemainMoney());
            System.out.println("表止码：" + meterStatus.getMeterRead());
        }
        if(funCode==0x3E){
            System.out.println("以下为统一回传帧中额外数据");
            System.out.println("上周期量："+meterStatus.getPresumamount());
            System.out.println("当前使用气价："+meterStatus.getPrice());
            System.out.println("分段气量1："+meterStatus.getAmount1());
            System.out.println("分段气量2："+meterStatus.getAmount2());
            System.out.println("分段气量3："+meterStatus.getAmount3());
            System.out.println("本周期量："+meterStatus.getSumamount());
            System.out.println("表具时间："+new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(meterStatus.getMeterTime()));
            System.out.println("执行的命令数："+rf.getFuncCount());
            List<CFunction> codeList = rf.getAryFunc();
            for(int i=0;i<codeList.size();i++){
                CFunction cFunction = codeList.get(i);
                System.out.println("命令码：" + Integer.toHexString(Byte.toUnsignedInt(cFunction.getCode()))
                        + "，" + "帧id：" + Byte.toUnsignedInt(cFunction.getFid())
                +",SUCCESS:"+cFunction.isSuccess());
            }
        }
    }
}
