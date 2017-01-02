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
    public static byte[] getKeyBytes(String key){
        byte[] keyBytes = new byte[16];
        for(int i=0;i<16;i++){
            keyBytes[i] = (byte) Integer.parseInt(key.substring(i * 2, i * 2 + 2),16);
        }
        return keyBytes;
    }

    /// <summary>
    /// 表具开通帧
    /// </summary>
    /// <param name="meterId">表具编号</param>
    /// <param name="key">表具密钥</param>
    /// <param name="fid">帧ID</param>
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
    public static byte[] getMeterOpenFrame(String meterId,String key,byte fid,double money,
                                           double p0,double p1,double p2,double p3,int a1,int a2,int a3,
                                           String nkey,Date beginDT,byte clen,byte cbr,int bzql,int szql){
        //String strResult = "";
        //生成新密钥数组
        byte[] bKey = getKeyBytes(nkey);
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterId);
        sf.setFuncCode((byte) 0x23);
        sf.setFrameID(fid);
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
        sf.addParam(c.get(Calendar.YEAR)/100,1);
        sf.addParam(c.get(Calendar.YEAR)%100,1);
        sf.addParam(c.get(Calendar.MONTH)+1,1);
        sf.addParam(c.get(Calendar.DAY_OF_MONTH),1);
        sf.addParam(clen,1);
        sf.addParam(0,1);
        sf.addParam(cbr, 1);
        sf.addParam(bzql, 2, true);
        sf.addParam(szql, 2, true);
        byte[] frameBytes = sf.ProcFrame(key);
        return frameBytes;
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
    public static byte[] getChangeMoneyFrame(String resid,String key,int fid,double money,int timeCorrection){
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setFrameID((byte)(fid%256));
        sf.setFuncCode((byte)0x0A);
        double dMon;
        int czfs = 0;
        if(money>0){
            dMon = money;
            czfs = 0;
        }else{
            dMon = money*-1;
            czfs = 1;
        }
        sf.addParam(dMon,4);
        sf.addParam(czfs,1);
        sf.addParam(fid/256,1);
        sf.setTimeCorrection(timeCorrection);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    // 读取表具数据
    // <param name="resid">表具编号</param>
    // <param name="key">表具秘钥</param>
    // <param name="fid">帧ID</param>
    // <param name="tmCorrection">时间修正值</param>
    public static byte[] getMeterDataFrame(String resid,String key,byte fid,Date date, int timeCorrection){
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setTimeCorrection(timeCorrection);
        //及时读表
        if(date == null) {
            sf.setFuncCode((byte)0x5);
        }
        //定期读表
        else {
            sf.setFuncCode((byte)0x1E);
            long end = date.getTime();
            long begin = DateUtil.createDate("2000-1-1 00:00:00").getTime();
            int iTM = (int)((end - begin)/1000);
            sf.addParam(iTM, 4);
        }
        sf.setFrameID(fid);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    /// <summary>
    /// 变更价格
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">帧ID</param>
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
    public static byte[] getChangePriceFrame(String resid,String key,byte fid,double p0,
                                             double p1,double p2,double p3,int a1,int a2,int a3,
                                             Date beginDT,byte clen,Date atDT,int timeCorrection){
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setFrameID(fid);
        sf.setTimeCorrection(timeCorrection);
        if(atDT!=null) {
            long millisecond = atDT.getTime() - DateUtil.createDate("2000-01-01 00:00:00").getTime();
            millisecond = millisecond/1000;
            sf.setFuncCode((byte) 0x1F);
            sf.addParam((int) millisecond, 4);
        }
        else{
            sf.setFuncCode((byte)0x08);
        }
        sf.addParam(p0,2);
        sf.addParam(p1,2);
        sf.addParam(a1,2,true);
        sf.addParam(p2,2);
        sf.addParam(a2, 2, true);
        sf.addParam(p3, 2);
        sf.addParam(a3, 2, true);
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(beginDT.getTime());
        sf.addParam(c.get(Calendar.YEAR) / 100, 1);
        sf.addParam(c.get(Calendar.YEAR) % 100, 1);
        sf.addParam(c.get(Calendar.MONTH)+1, 1);
        sf.addParam(c.get(Calendar.DAY_OF_MONTH), 1);
        sf.addParam(clen, 1);
        sf.addParam(0, 1);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    /// <summary>
    /// 更改透支方式
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">帧ID</param>
    /// <param name="style">透支方式。0：10元，1：3天，2：无限</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static byte[] getChangeOverdraftFrame(String resid,String key,byte fid,int style,int tmCorrection)
    {
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setFuncCode((byte) 0x21);
        sf.setFrameID(fid);
        sf.setTimeCorrection(tmCorrection);
        sf.addParam(style, 1);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    /// <summary>
    /// 变更服务号码
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">帧ID</param>
    /// <param name="serverno">服务号码</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns></returns>
    public static byte[] getChangeServerNoFrame(String resid,String key,byte fid,String serverno,int tmCorrection)
    {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte) 0x0D);
        sf.setMeterID(resid);
        sf.setFrameID(fid);
        sf.setTimeCorrection(tmCorrection);
        sf.addParam(1, 1);
        sf.addParam(serverno);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    /// <summary>
    /// 阀门控制
    /// </summary>
    /// <param name="resid">表具编号</param>
    /// <param name="key">表具秘钥</param>
    /// <param name="fid">帧ID</param>
    /// <param name="vcs">阀门控制类别</param>
    /// <param name="atDT">定时时间</param>
    /// <param name="tmCorrection">时间修正值</param>
    /// <returns>帧命令字符串</returns>
    public static byte[] getValveControlFrame(String resid,String key,byte fid,ValveCtrStyle vcs,Date atDT,int tmCorrection)
    {
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setFrameID(fid);
        switch (vcs)
        {
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
        sf.setTimeCorrection(tmCorrection);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    //要求校对表具时间
    public static byte[] getCheckMeterTimeFrame(String meterID, String key, byte frameID){
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte)41);
        sf.setFrameID(frameID);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    /*
     * 强制对时
     * @param resid   表具编号
     * @param key     表具秘钥
     * @param fid     帧ID
     * @param secTS
     * @param style
     * @return  帧命令字符串
     */
    public static byte[] getMandatoryTimeFrame(String resid,String key,byte fid,int secTS,byte style)
    {
        SendFrame sf = new SendFrame();
        sf.setMeterID(resid);
        sf.setFuncCode((byte) 0xFF);
        sf.setFrameID(fid);
        sf.addParam(Byte.toUnsignedInt(style), 1);
        sf.addParam(Math.abs(secTS), 4);

        byte[] frame = sf.ProcFrame(key);
        return frame;
    }

    // 设置表具周期量，包括本周期量和上周期量
    public static byte[] getMeterUseSetFrame(String meterID, String meterKey, byte frameID, int sum, int cur, int pre, byte mode) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte)0x06);
        sf.setFrameID(frameID);
        if ((mode / 4) > 0)
        {
            sf.addParam(2, 1);
            sf.addParam(sum, 4, true);
        }
        else
        {
            if (sum >= 0)
            {
                sf.addParam(0, 1);
                sf.addParam(sum, 4, true);
            }
            else
            {
                sf.addParam(1, 1);
                sf.addParam(sum * -1, 4, true);
            }
        }
        if ((mode % 4) > 1)
        {
            sf.addParam(2, 1);
            sf.addParam(cur, 2, true);
        }
        else
        {
            if (cur >= 0)
            {
                sf.addParam(0, 1);
                sf.addParam(cur, 2, true);
            }
            else
            {
                sf.addParam(1, 1);
                sf.addParam(cur * -1, 2, true);
            }
        }
        if ((mode % 2) == 1)
        {
            sf.addParam(2, 1);
            sf.addParam(pre, 2, true);
        }
        else
        {
            if (pre >= 0)
            {
                sf.addParam(0, 1);
                sf.addParam(pre, 2, true);
            }
            else
            {
                sf.addParam(1, 1);
                sf.addParam(pre * -1, 2, true);
            }
        }
        byte[] frame = sf.ProcFrame(meterKey);
        return frame;
    }

    //更改表具每月抄表日
    public static byte[] getMeterSetCBRFrame(String meterID, String meterKey, byte frameID, int timeCorrection, int cbr) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte)0x25);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        sf.addParam(cbr, 1);
        byte[] frame = sf.ProcFrame(meterKey);
        return frame;
    }

    // 读取历史阶梯周期使用量
    public static byte[] getReadCycleInfoFrame(String meterID, String meterKey, byte frameID, int timeCorrection) {
        SendFrame sf = new SendFrame();
        sf.setMeterID(meterID);
        sf.setFuncCode((byte)0x29);
        sf.setFrameID(frameID);
        sf.setTimeCorrection(timeCorrection);
        byte[] frame = sf.ProcFrame(meterKey);
        return frame;
    }

    // 服务号码变更
    // N:第几个短信平台号码
    public static byte[] getSetServerNoFrame(String meterID, String key, int frameID, int timeCorrection, int N, String serverNo) {
        SendFrame sf = new SendFrame();
        sf.setFuncCode((byte)0x0D);
        sf.setMeterID(meterID);
        sf.setFrameID((byte) 0);
        sf.setTimeCorrection(0);
        sf.addParam(1, 1);
        sf.addParam(serverNo);
        byte[] frame = sf.ProcFrame(key);
        return frame;
    }
}
