package org.whut.meterFrameManagement.communicationframe.frames;

import org.whut.meterFrameManagement.communicationframe.enums.ValveCtrStyle;
import org.whut.meterFrameManagement.communicationframe.send.SendFrame;
import org.whut.meterFrameManagement.util.date.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by zhang_minzhong on 2016/12/16.
 */
public class FrameFactory {

    //字符串密钥转密钥字节数组
    public static byte[] getKeyBytes(String key) {
        byte[] keyBytes = new byte[16];
        for (int i = 0; i < 16; i++) {
            keyBytes[i] = (byte) Integer.parseInt(key.substring(i * 2, i * 2 + 2), 16);
        }
        return keyBytes;
    }

    /// <summary>
    /// 阀门控制
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="frameID">帧ID</param>
    /// <param name="vcs">阀门控制类别</param>
    /// <param name="atDT">定时时间</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns>帧命令字符串</returns>
    public static byte[] getValveControlFrame(String meterID, String key, byte frameID, ValveCtrStyle vcs, Date atDT, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        switch (vcs) {
            case 允许开启:
                sf.setFuncCode((byte) 0x01);
                break;
            case 临时关闭:
                sf.setFuncCode((byte) 0x03);
                break;
            case 强制关闭:
                sf.setFuncCode((byte) 0x0C);
                break;
            case 定时关闭:
                sf.setFuncCode((byte) 0x20);
                long millisecond = atDT.getTime() - DateUtil.createDate("2000-01-01 00:00:00").getTime();
                millisecond = millisecond / 1000;
                sf.addParam((int) millisecond, 4);
                break;
            default:
                break;
        }
        sf.setTimeCorrection(timeCorrection);

        return sf.ProcFrame(key);
    }

    // 读取表具数据
    // <param name="resid">表具编号</param>
    // <param name="key">表具秘钥</param>
    // <param name="frameID">帧ID</param>
    // <param name="tmCorrection">时间修正值</param>
    public static byte[] getMeterDataFrame(String meterID, String key, byte frameID, Date date, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setTimeCorrection(timeCorrection);
        //及时读表
        if (date == null) {
            sf.setFuncCode((byte) 0x05);
        }
        //定期读表
        else {
            sf.setFuncCode((byte) 0x1E);
            long end = date.getTime();
            long begin = DateUtil.createDate("2000-1-1 00:00:00").getTime();
            int iTM = (int) ((end - begin) / 1000);
            sf.addParam(iTM, 4);
        }
        sf.setFrameID(frameID);
        //byte[] frame = sf.ProcFrame(key);
        return sf.ProcFrame(key);
    }

    // 设置表具内的运行气量，包括本周期量和上周期量
    public static byte[] getMeterUseSetFrame(String meterID, String key, byte frameID,byte czfs1, int zyql,byte czfs2, int bzq,
                                             byte czfs3,int szq,byte fs,int lszqyl,long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte) 0x06);
        sf.setFrameID(frameID);
        sf.addParam(czfs1,1);
        sf.addParam(zyql,4,true);
        sf.addParam(czfs2,1);
        sf.addParam(bzq,2,true);
        sf.addParam(czfs3,1);
        sf.addParam(szq,2,true);
        sf.addParam(fs,1);
        sf.addParam(lszqyl,24,true);
        sf.setTimeCorrection(timeCorrection);
        /*if ((mode / 4) > 0) {
            sf.addParam(2, 1);
            sf.addParam(sum, 4, true);
        } else {
            if (sum >= 0) {
                sf.addParam(0, 1);
                sf.addParam(sum, 4, true);
            } else {
                sf.addParam(1, 1);
                sf.addParam(sum * -1, 4, true);
            }
        }
        if ((mode % 4) > 1) {
            sf.addParam(2, 1);
            sf.addParam(cur, 2, true);
        } else {
            if (cur >= 0) {
                sf.addParam(0, 1);
                sf.addParam(cur, 2, true);
            } else {
                sf.addParam(1, 1);
                sf.addParam(cur * -1, 2, true);
            }
        }
        if ((mode % 2) == 1) {
            sf.addParam(2, 1);
            sf.addParam(pre, 2, true);
        } else {
            if (pre >= 0) {
                sf.addParam(0, 1);
                sf.addParam(pre, 2, true);
            } else {
                sf.addParam(1, 1);
                sf.addParam(pre * -1, 2, true);
            }
        }*/
        //byte[] frame = sf.ProcFrame(meterKey);
        return sf.ProcFrame(key);
    }

    /**
     * 设置收费模式命令（测试用，一般都是预收费）
     * @param meterID
     * @param key
     * @param frameID
     * @param sfms
     * @return
     */
    public static byte[] getSetChargingModeFrame(String meterID, String key,byte frameID,byte sfms) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x07);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        // sfms：收费模式，DFH（预收费）、20H（非预收费）
        sf.addParam(Byte.toUnsignedInt(sfms),1);
        return sf.ProcFrame(key);
    }

    /// <summary>
    /// 变更价格
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="frameID">帧ID</param>
    /// <param name="p0">初始价格</param>
    /// <param name="p1">阶梯价格1</param>
    /// <param name="p2">阶梯价格2</param>
    /// <param name="p3">阶梯价格3</param>
    /// <param name="a1">起始量1</param>
    /// <param name="a2">起始量2</param>
    /// <param name="a3">起始量3</param>
    /// <param name="beginDT">阶梯价格起始日期</param>
    /// <param name="clen">阶梯周期长度</param>
    /// <param name="atDT">定时更改时间点，为NULL表示即时更改</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static byte[] getChangePriceFrame(String meterID, String key, byte frameID, double p0,
                                             double p1, double p2, double p3, int a1, int a2, int a3,
                                             Date beginDT, byte clen, Date atDT, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        if (atDT != null) {
            long millisecond = atDT.getTime() - DateUtil.createDate("2000-01-01 00:00:00").getTime();
            millisecond = millisecond / 1000;
            sf.setFuncCode((byte) 0x2B);
            sf.addParam((int) millisecond, 4);
        } else {
            sf.setFuncCode((byte) 0x08);
        }
        sf.addParam(p0, 2);
        sf.addParam(p1, 2);
        sf.addParam(a1, 2, true);
        sf.addParam(p2, 2);
        sf.addParam(a2, 2, true);
        sf.addParam(p3, 2);
        sf.addParam(a3, 2, true);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(beginDT.getTime());
        sf.addParam(c.get(Calendar.YEAR) / 100, 1);
        sf.addParam(c.get(Calendar.YEAR) % 100, 1);
        sf.addParam(c.get(Calendar.MONTH) + 1, 1);
        sf.addParam(c.get(Calendar.DAY_OF_MONTH), 1);
        sf.addParam(clen, 1);
        sf.addParam(0, 1);
        //byte[] frame = sf.ProcFrame(key);
        return sf.ProcFrame(key);
    }

    /// <summary>
    /// 变更金额帧
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">充值ID</param>
    /// <param name="money">充值金额，可为负数</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static byte[] getChangeMoneyFrame(String meterID, String key, byte frameID, double money, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setFuncCode((byte) 0x0A);
        double dMon;
        int czfs = 0;
        if (money > 0) {
            dMon = money;
            czfs = 0;
        } else {
            dMon = money * -1;
            czfs = 1;
        }
        sf.addParam(dMon, 4);
        sf.addParam(czfs, 1);
        sf.setTimeCorrection(timeCorrection);
        //byte[] frame = sf.ProcFrame(key);
        return sf.ProcFrame(key);
    }

    // 服务号码变更
    // N:第几个短信平台号码
    public static byte[] getSetServerNoFrame(String meterID, String key, byte frameID, long timeCorrection, int N, String serverNo) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x0D);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(1, 1);
        sf.addParam(serverNo);
        //byte[] frame = sf.ProcFrame(key);
        return sf.ProcFrame(key);
    }

    /**
     * 设置服务器IP以及端口号
     *
     * @param meterID        设备编号
     * @param key            密钥
     * @param frameID        帧ID
     * @param serverIP       服务器IP
     * @param serverPort     服务器端口号
     * @param timeCorrection 时间修正值
     * @return
     */
    public static byte[] getSetIPAndPortFrame(String meterID, String key, byte frameID, String serverIP, String serverPort, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x0F);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(serverIP);
        sf.addParam(serverPort);

        return sf.ProcFrame(key);
    }

    /**
     * 设置心跳包频率
     *
     * @param meterID        设备编号
     * @param key            密钥
     * @param frameID        帧ID
     * @param rate           频率
     * @param timeCorrection 时间修正值
     * @return
     */
    public static byte[] getSetBeatHeartRateFrame(String meterID, String key, byte frameID, int rate, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x10);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(rate, 1);

        return sf.ProcFrame(key);
    }

    /**
     * 发送心跳包
     *
     * @param meterID
     * @param key
     * @param frameID
     * @param timeCorrection
     * @return
     */
    public static byte[] getSetBeatHeartFrame(String meterID, String key, byte frameID, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x11);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);

        return sf.ProcFrame(key);
    }

    /**
     * 设置集中器所辖表具
     *
     * @param concentratorID 集中器编号
     * @param meterID        表具编号
     * @param key
     * @param frameID
     * @param timeCorrection 时间修正值
     * @return
     */
    public static byte[] getSetMeter2ConcentratorFrame(String concentratorID, String meterID, String key, byte frameID, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x12);
        sf.setMeterID(concentratorID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(meterID);

        return sf.ProcFrame(key);
    }

    /**
     * 设置密钥
     *
     * @param meterID
     * @param key
     * @param frameID
     * @param timeCorrection
     * @return
     */
    public static byte[] getSetKeyFrame(String meterID, String key, byte frameID, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x16);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(key);

        return sf.ProcFrame(key);
    }

    /**
     * 撤销中继器所辖表具
     *
     * @param concentratorID 集中器编号
     * @param meterID        表具编号
     * @param key
     * @param frameID
     * @param timeCorrection
     * @return
     */
    public static byte[] getRecallMeter2ConcentratorFrame(String concentratorID, String meterID, String key, byte frameID, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x18);
        sf.setMeterID(concentratorID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(meterID);

        return sf.ProcFrame(key);
    }

    /// <summary>
    /// 更改透支方式
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="frameID">帧ID</param>
    /// <param name="style">透支方式。0：10元，1：3天，2：无限</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static byte[] getChangeOverdraftFrame(String meterID, String key, byte frameID, int style, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte) 0x21);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(style, 1);

        return sf.ProcFrame(key);
    }

    /**
     * 设置表具通讯模块启动周期
     *
     * @param meterID
     * @param qdzq           通讯模块启动周期，单位是小时
     * @param key
     * @param frameID
     * @param timeCorrection 时间修正值
     * @return
     */
    public static byte[] getCommunicationUpCycleFrame(String meterID,String key, byte frameID,int qdzq,long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x22);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(qdzq, 1);

        return sf.ProcFrame(key);
    }

    /// <summary>
    /// 表具开通帧
    /// </summary>
    /// <param name="meterId">表具编号</param>
    /// <param name="key">表具密钥</param>
    /// <param name="frameID">帧ID</param>
    /// <param name="money">开通金额</param>
    /// <param name="p0">初始价格</param>
    /// <param name="p1">阶梯价格1</param>
    /// <param name="p2">阶梯价格2</param>
    /// <param name="p3">阶梯价格3</param>
    /// <param name="a1">起始量1</param>
    /// <param name="a2">起始量2</param>
    /// <param name="a3">起始量3</param>
    /// <param name="nkey">新秘钥</param>
    /// <param name="beginDT">开通日期</param>
    /// <param name="clen">周期长度</param>
    /// <param name="cbr">抄表日</param>
    /// <param name="bzql">本周期量</param>
    /// <param name="szql">上周期量</param>
    /// <returns></returns>
    public static byte[] getMeterOpenFrame(String meterId, String key, byte frameID, double money,
                                           double p0, double p1, double p2, double p3, int a1, int a2, int a3,
                                           String nkey, Date beginDT, byte clen, byte cbr, int bzql, int szql) {
        //String strResult = "";
        //生成新密钥数组
        byte[] bKey = getKeyBytes(nkey);
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterId);
        sf.setFuncCode((byte) 0x23);
        sf.setFrameID(frameID);
        sf.addParam(p0, 2);
        sf.addParam(p1, 2);
        sf.addParam(a1, 2, true);
        sf.addParam(p2, 2);
        sf.addParam(a2, 2, true);
        sf.addParam(p3, 2);
        sf.addParam(a3, 2, true);
        sf.addParam(money, 4);
        sf.addParam(bKey);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(beginDT.getTime());
        //System.out.println(c.get(Calendar.MONTH)+1);
        sf.addParam(c.get(Calendar.YEAR) / 100, 1);
        sf.addParam(c.get(Calendar.YEAR) % 100, 1);
        sf.addParam(c.get(Calendar.MONTH) + 1, 1);
        sf.addParam(c.get(Calendar.DAY_OF_MONTH), 1);
        sf.addParam(clen, 1);
        sf.addParam(0, 1);
        sf.addParam(cbr, 1);
        sf.addParam(bzql, 2, true);
        sf.addParam(szql, 2, true);
        //byte[] frameBytes = sf.ProcFrame(key);
        return sf.ProcFrame(key);
    }

    /**
     * 设置表具成用户态
     *
     * @param meterID
     * @param key
     * @param frameID
     * @param yqdj           用气单价
     * @param timeCorrection
     * @return
     */
    public static byte[] getSetMeter2UserModeFrame(String meterID, String key, byte frameID, double yqdj, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x24);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(yqdj, 2);

        return sf.ProcFrame(key);
    }

    //更改表具每月抄表日(设置周期读表时间)
    // cbr:1个字节定期读表时间
    public static byte[] getMeterSetCBRFrame(String meterID, String key, byte frameID, long timeCorrection, int cbr) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte) 0x25);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(cbr, 1);
        //byte[] frame = sf.ProcFrame(meterKey);
        return sf.ProcFrame(key);
    }

    /**
     * 读表具定时数据 (用于表具主动上报失败情况)
     *
     * @param meterID
     * @param key
     * @param frameID
     * @param atDT           定时时间
     * @param timeCorrection
     * @return
     */
    public static byte[] getTimerDataFrame(String meterID, String key, byte frameID, Date atDT, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x26);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        long millisecond = atDT.getTime() - DateUtil.createDate("2000-01-01 00:00:00").getTime();
        millisecond = millisecond / 1000;
        sf.addParam((int) millisecond, 4);

        return sf.ProcFrame(key);
    }

    /**
     * 读表具定时更新单价时数据 (用于表具主动上报失败情况)
     *
     * @param meterID
     * @param key
     * @param frameID
     * @param atDT           定时时间
     * @param timeCorrection 时间修正值
     * @return
     */
    public static byte[] getTimerDataOfChangePriceFrame(String meterID, String key, byte frameID, Date atDT, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x27);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        long millisecond = atDT.getTime() - DateUtil.createDate("2000-01-01 00:00:00").getTime();
        millisecond = millisecond / 1000;
        sf.addParam((int) millisecond, 4);

        return sf.ProcFrame(key);
    }

    /**
     * 清除表具出错标志
     *
     * @param meterID
     * @param key
     * @param frameID
     * @param timeCorrection
     * @return
     */
    public static byte[] getClearErrorMarkFrame(String meterID, String key, byte frameID, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x28);
        sf.setMeterID(meterID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);

        return sf.ProcFrame(key);
    }

    // 读取历史阶梯周期使用量
    public static byte[] getReadCycleInfoFrame(String meterID, String key, byte frameID, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte) 0x29);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        //byte[] frame = sf.ProcFrame(meterKey);
        return sf.ProcFrame(key);
    }

    /**
     * 通知（移动设备）现场设备通信状态
     *
     * @param mobileID       移动设备编号
     * @param meterID        现场设备编号
     * @param key
     * @param frameID
     * @param txzt           通信状态，0正常，1异常
     * @param timeCorrection
     * @return
     */
    public static byte[] getNotifyTXZTFrame(String mobileID, String meterID, String key, byte frameID, int txzt, long timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x2E);
        sf.setMeterID(mobileID);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(meterID);
        sf.addParam(txzt, 1);

        return sf.ProcFrame(key);
    }

    /**
     * 强制对时
     *
     * @param meterID 表具编号
     * @param key     密钥
     * @param frameID 帧ID
     * @param secTS
     * @param style
     * @return
     */
    public static byte[] getMandatoryTimeFrame(String meterID, String key, byte frameID, int secTS, byte style) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte) 0xFF);
        sf.setFrameID(frameID);
        sf.addParam(Byte.toUnsignedInt(style), 1);
        sf.addParam(Math.abs(secTS), 4);
        return sf.ProcFrame(key);
    }




    /*//以下方法做回传测试
    public static byte[] getAllowOpenValveBackFrame(String meterID, String keyStr, byte frameID) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte)0x81);
        sf.setFrameID(frameID);
        sf.addParam(2,1);
        return  sf.ProcFrame(keyStr);

    }

    public static byte[] getMeterDataBackFrame(String meterID, String keyStr, byte frameID, double syje, int zyql,int xtztzj) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte)0x85);
        sf.setFrameID(frameID);
        sf.addParam(syje,4);
        sf.addParam(zyql,4);
        sf.addParam(xtztzj,1);
        sf.addParam(0,1);//保留字节
        return sf.ProcFrame(keyStr);
    }

    public static byte[] getUnifiedReturnFrame(String meterID, String keyStr, byte frameID, double remainMoney, int zyql, byte xtztzj,
                                               int syyql, double currentPrice, int ql1, int ql2, int ql3, int currentMonthCount,
                                               long xtsj, long dssj, int N, int code1, int fID1, int code2, int fID2, int code3, int fID3,
                                               int code4, int fID4, int code5, int fID5, int code6, int fID6, int code7, int fID7, int code8, int fID8) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte)0x3E);
        sf.setFrameID(frameID);
        sf.addParam(remainMoney,4);
        sf.addParam(zyql,4);
        sf.addParam(xtztzj,1);
        sf.addParam(syyql,2);
        sf.addParam(currentPrice,2);
        sf.addParam(ql1,2);
        sf.addParam(ql2,2);
        sf.addParam(ql3,2);
        sf.addParam(currentMonthCount,2);
        sf.addParam((int)(xtsj/1000),4);
        sf.addParam((int)(dssj/1000),4);
        sf.addParam(N,1);
        sf.addParam(code1, 1);
        sf.addParam(fID1,1);
        sf.addParam(code2,1);
        sf.addParam(fID2,1);
        sf.addParam(code3,1);
        sf.addParam(fID3,1);
        sf.addParam(code4,1);
        sf.addParam(fID4,1);
        sf.addParam(code5,1);
        sf.addParam(fID5,1);
        sf.addParam(code6,1);
        sf.addParam(fID6,1);
        sf.addParam(code7,1);
        sf.addParam(fID7,1);
        sf.addParam(code8,1);
        sf.addParam(fID8,1);
        return sf.ProcFrame(keyStr);
    }*/
}
