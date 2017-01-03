package org.whut.meterFrameManagement.communicationframe.base;


import org.whut.meterFrameManagement.communicationframe.enums.FrameDirection;
import org.whut.meterFrameManagement.communicationframe.enums.FrameResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenfu on 2016/12/9.
 *
 * CommandFrame 命令帧基类
 */
public class CommandFrame {

    protected FrameDirection frmDirection;//帧的传送方向
    protected FrameResult frmResult;//帧的执行结果
    protected byte frameID;//帧id
    protected byte funcCode;//命令码
    protected String meterID;//设备编号，如表具编号，PDA编号
    protected List<FrameParam> ParamList;//数据区（不含表号）

    /**
     * 构造方法
     */
    public CommandFrame() {
        ParamList = new ArrayList<FrameParam>();
        funcCode = 0;
        meterID = "";
        frameID = 0;
    }

    public FrameDirection getFrmDirection() {
        return frmDirection;
    }

    public void setFrmDirection(FrameDirection frmDirection) {
        this.frmDirection = frmDirection;
    }

    public FrameResult getFrmResult() {
        return frmResult;
    }

    public void setFrmResult(FrameResult frmResult) {
        this.frmResult = frmResult;
    }

    public byte getFrameID() {
        return frameID;
    }

    public void setFrameID(byte frameID) {
        this.frameID = frameID;
    }

    public byte getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(byte funcCode) {
        this.funcCode = funcCode;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public static String codedesc(byte code) {
        String rst;
        switch (code) {
            case 0x01:
                rst = "允许表具开启阀门";
                break;
            case 0x02:
                rst = "";
                break;
            case 0x03:
                rst = "关闭表具阀门";
                break;
            case 0x04:
                //rst = "更新表内剩余气量";
                rst = "";
                break;
            case 0x05:
                rst = "读取表具数据";
                break;
            case 0x06:
                //rst = "更新表内总用气量";
                rst = "设置表具周期量";
                break;
            case 0x07:
                rst = "设置收费模式";
                break;
            case 0x08:
                rst = "更新单价";
                break;
            case 0x09:
                rst = "";
                break;
            case 0x0A:
                rst = "更新表内可用金额";
                break;
            case 0x0B:
                rst = "初始化表具数据";
                break;
            case 0x0C:
                rst = "强制关闭表具阀门";
                break;
            case 0x0D:
                rst = "设置表内服务器通讯号码";
                break;
            case 0x0E:
                rst = "报告换表数据";
                break;
            case 0x0F:
                rst = "设置服务器IP以及端口号";
                break;
            case 0x10:
                rst = "设置心跳包频率";
                break;
            case 0x11:
                rst = "心跳包";
                break;
            case 0x12:
                rst = "设置中继器所辖表具";
                break;
            case 0x13:
                rst = "报告设备安装地理定位信息(PDA)";
                break;
            case 0x14:
                rst = "报告设备安装地点(PDA)";
                break;
            case 0x15:
                rst = "报告表具所属中继器(PDA)";
                break;
            case 0x16:
                rst = "设置密钥";
                break;
            case 0x17:
                rst = "报告设备SIM卡号(PDA)";
                break;
            case 0x18:
                rst = "撤销中继器所辖表具";
                break;
            case 0x19:
                rst = "表具上报电池电压变化";
                break;
            case 0x1A:
                rst = "表具上报系统状态";
                break;
            case 0x1B:
                rst = "表具上报定时抄表数据";
                break;
            case 0x1C:
                rst = "表具上报定时更新单价";
                break;
            case 0x1D:
                rst = "表具上报定时关闭阀门情况";
                break;
            case 0x1E:
                rst = "设置表具定时抄表时间";
                break;
            case 0x1F:
                rst = "设置表具定时更新单价";
                break;
            case 0x20:
                rst = "设置表具定时关闭阀门";
                break;
            case 0x21:
                rst = "设置表具透支模式";
                break;
            case 0x22:
                rst = "设置表具通讯模块启动周期";
                break;
            case 0x23:
                rst = "表具开通";
                break;
            case 0x24:
                rst = "表具开通";
                break;
            case 0x25:
                rst = "设置表具抄表日";
                break;
            case 0x26:
                rst = "读取表具定时抄表数据";
                break;
            case 0x27:
                rst = "读取表具定时更新单价数据";
                break;
            case 0x28:
                rst = "清除表具故障标识";
                break;
            case 0x29:
                rst = "读取表具历史周期量";
                break;
            case 0x2A:
                rst = "表具校准时间";
                break;
            case 0x2B:
                rst = "报告表具安装单编号(PDA)";
                break;
            case 0x2C:
                rst = "报表安装SIM卡序号(PDA)";
                break;
            case 0x2D:
                rst = "报告表具恢复正常(PDA)";
                break;
            case 0x2E:
                rst = "通知（移动设备）现场设备通信状态";
                break;
            case 0x2F:
                rst = "通知设备服务器密钥";
                break;
            case 0x30:
                rst = "表具更换SIM卡上报(PDA)";
                break;
            default:
                rst = "";
                break;
        }
        return rst;
    }

    /**
     * 取得帧类型的说明
     * @return
     */
    public String GetFrameDes() {
        return codedesc(funcCode);
    }

    public String GetFrameDes(byte code) {
        return codedesc(code);
    }

    /**
     * 密钥转换函数，将字符串密钥转换为BYTE数组密钥
     * @param sKey
     * @return
     */
    public byte[] getKey(String sKey) {
        String str = sKey;
        if (sKey.length() < 32) {
            for (int i = 1; i <= 32 - sKey.length(); i++)
                str += "0";
        }
        str = str + str;
        byte[] key = new byte[32];
        for (int i = 0; i < 32; i++) {
            key[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
        }
        return key;
    }
}
