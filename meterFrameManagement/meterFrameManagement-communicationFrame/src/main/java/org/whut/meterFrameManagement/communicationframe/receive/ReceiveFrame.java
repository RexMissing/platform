package org.whut.meterFrameManagement.communicationframe.receive;

import org.whut.meterFrameManagement.aes256.AES;
import org.whut.meterFrameManagement.communicationframe.base.CommandFrame;
import org.whut.meterFrameManagement.communicationframe.enums.FrameDirection;
import org.whut.meterFrameManagement.communicationframe.enums.FrameResult;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by chenfu on 2016/12/9.
 */

// 帧解析类，解析收到的字符串帧
public class ReceiveFrame extends CommandFrame {

    protected String dataStr;//帧中的数据区域字符串，不含表号
    private List<CFunction> aryFunc;//存统一回传帧中的命令码和帧id
    private int funcCount;//统一回传帧包含的帧的数量
    public MeterStatus MeterST;//表具状态数据对象
    public PDAInfo PDA;//PDA设备数据类型
    public byte reportCode;//表具统一上报码

    //构造方法
    public ReceiveFrame() {
        dataStr = "";
        aryFunc = new ArrayList<CFunction>();
        MeterST = new MeterStatus();
        PDA = new PDAInfo();
    }

    public List<CFunction> getAryFunc() {
        return aryFunc;
    }

    public int getFuncCount() {
        return funcCount;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public byte getReportCode() {
        return reportCode;
    }

    public void setReportCode(byte reportCode) {
        this.reportCode = reportCode;
    }

    /*
    对字符串帧进行解析，将解析后数据放到对象字段，同时可判断帧是否合法
    成功解析帧，返回true，失败返回false
     */
    public boolean ParseFrom(String frameStr) {
        System.out.println("16进制字符串帧："+frameStr);
        //帧头判断
        if (!frameStr.substring(0,1).toUpperCase().equals("H")) {
            return false;
        }
        //取得命令码
        try {
            funcCode = (byte) Integer.parseInt(frameStr.substring(1, 3), 16);
        } catch (Exception e) {
            return false;
        }
        //取得数据区域长度(L)
        int dataLen;
        //如果为统一回传帧，dataLen直接等于=0x3C
        if (funcCode == 0x3E) {
            dataLen = 0x3C;
        } else {
            try {
                dataLen = Integer.valueOf(frameStr.substring(3, 5), 16);
            } catch (Exception e) {
                return false;
            }
        }
        //判断截止码是否为16
        try {
            if (!frameStr.substring(dataLen * 2 - 6, dataLen * 2 - 4).equals("16")) {//
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        //表具编号（或设备编号）
        meterID = frameStr.substring(5, 18);
        //数据区域
        dataStr = frameStr.substring(18, 18 + (dataLen - 14) * 2);
        //System.out.print("数据字符串(不包含表号和帧id)：" + dataStr + "  ");
        //System.out.println("长度：" + (dataLen - 14) * 2);

        //帧ID FrameID
        try {
            int begin = 18 + dataStr.length();
            frameID = (byte) Integer.parseInt(frameStr.substring(begin, begin + 2), 16);
        } catch (Exception e) {
            return false;
        }
        //校验和
        int CS;
        try {
            int begin = 20 + dataStr.length();
            CS = Integer.valueOf(frameStr.substring(begin, begin + 2), 16);
            System.out.println("校验和：" + CS);
        } catch (Exception e) {
            return false;
        }
        //结束符 0x16
        int begin = 22 + dataStr.length();
        if (!frameStr.substring(begin, 2 + begin).equals("16")) {
            return false;
        }

        //对命令码进行分解；命令码说明： D7：传送方向，1表示回传，0表示起始帧；；D6:执行结果，0表示正常；1表示异常; D5-D0:功能码

        int itmp = Byte.toUnsignedInt(funcCode) / 0x40;
        if (itmp == 0) {
            frmDirection = FrameDirection.REQUEST;
            frmResult = FrameResult.SUCCESS;
        } else if (itmp == 2) {
            frmDirection = FrameDirection.REPLY;
            frmResult = FrameResult.SUCCESS;
        } else if (itmp == 3) {
            frmDirection = FrameDirection.REPLY;
            frmResult = FrameResult.FAIL;
        }
        funcCode = (byte) (Byte.toUnsignedInt(funcCode) % 64);

        ParseDataStr(); //对数据域进行解析
        return true;
    }

    private void ParseDataStr() {
        MeterST.setMeterID(meterID);
        MeterST.getFromStr(dataStr);
        if (funcCode == 0x3E) {
            //统一回传帧
            funcCount = Integer.parseInt(dataStr.substring(58, 60), 16);
            if (funcCount > 8) {
                funcCount = 8;
            }
            for (int i = 0; i < funcCount; i++) {
                CFunction cf = new CFunction();
                cf.setCode((byte) Integer.parseInt(dataStr.substring(60 + i * 4, 62 + i * 4), 16));
                cf.setFid((byte) Integer.parseInt(dataStr.substring(62 + i * 4, 64 + i * 4), 16));
                aryFunc.add(cf);
            }
        }
        if (funcCode == 0x3D) {
            //统一上报帧
            try {
                reportCode = (byte) Integer.parseInt(dataStr.substring(60, 62), 16);
            } catch (Exception e) {
                reportCode = 0;
            }
        }
        PDA.setPdaID(meterID);
        PDA.getFromStr(dataStr, funcCode);
    }

    //对帧字节数组进行解析，将解析后的数据放到对象字段，同时可以判断帧是否合法
    public boolean ParseFrom(byte[] frame, String sKey) {

        //解密
        byte[] key = getKey(sKey);
        byte[] buff = new byte[0];
        try {
            buff = AES.decrypt(frame, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*System.out.print("解密后帧字节数组：");
        for(int i=0;i<buff.length;i++){
            System.out.print(Byte.toUnsignedInt(buff[i])+" ");
        }
        System.out.println("字节长度："+buff.length);*/

        //将解密后的明文转换为16进制可见字符串帧

        //起始码
        String str = "h";
        //命令码，数据长度域
        byte[] funcAndLen = Arrays.copyOfRange(buff, 1, 3);
        str += Hex.BytesToHexString(funcAndLen);
        //表号
        for (int i = 3; i < 16; i++) {
            str += (char) buff[i];
        }
        //表号之后部分
        byte[] afterMeterId = Arrays.copyOfRange(buff, 16, buff.length);
        str += Hex.BytesToHexString(afterMeterId);
        //调用ParseFrom函数，解析帧
        return ParseFrom(str);
    }

}
