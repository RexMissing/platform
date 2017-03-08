package org.whut.meterFrameManagement.communicationframe.send;

import org.whut.meterFrameManagement.aes256.AES;
import org.whut.meterFrameManagement.communicationframe.base.CommandFrame;
import org.whut.meterFrameManagement.communicationframe.base.FrameParam;
import org.whut.meterFrameManagement.communicationframe.enums.FrameDirection;
import org.whut.meterFrameManagement.communicationframe.enums.FrameResult;
import org.whut.meterFrameManagement.util.hex.Hex;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhang_minzhong on 2016/12/13.
 */

// 发送命令帧类型，用于组成发送短信命令帧
public class SendFrame extends CommandFrame {
    private boolean V2 = false;
    private long timeCorrection;//表具时间调整值

    public long getTimeCorrection() {
        return timeCorrection;
    }

    public void setTimeCorrection(long timeCorrection) {
        this.timeCorrection = timeCorrection;
        setV2();
    }

    public void setV2() {
        V2 = true;
    }

    //构造方法
    public SendFrame() {
        super();
        frmDirection = FrameDirection.REQUEST;
        frmResult = FrameResult.SUCCESS;
    }

    // 加入整数类型区域，用于生成帧
    // <param name="value">整数值</param>
    // <param name="len">在帧中的长度</param>
    public void addParam(int value, int len)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(value, len);
        ParamList.add(fp);
    }

    // 加入整数类型区域，是否按照字节间100进制的方式转换
    // <param name="sjz">false:不转换，同AddParam(int,int)方法，true转换，将输入整数转换len个长度的字节数组，每个字节间100倍数关系</param>
    public void addParam(int value, int len, boolean sjz)
    {
        if (sjz)
        {
            //System.out.println(value);
            byte[] d = new byte[len];
            Integer v = value;
            String str = v.toString();
            while(str.length()<len*2) {
                str = "0" + str;
            }
            for (int i = 0; i < len; i++)
            {
                d[i] = (byte) Integer.parseInt(str.substring(i*2,i*2+2));

            }
            addParam(d);
        }
        else
        {
            addParam(value, len);
        }
    }

    // 加入浮点数区域，用与生成帧
    // <param name="value">浮点数值</param>
    // <param name="len">在帧中的长度</param>
    public void addParam(double value, int len)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(value, len);
        ParamList.add(fp);
    }

    // 加入字符串区域，用于生成帧
    // <param name="str">字符串值</param>
    public void addParam(String str)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(str, str.length());
        ParamList.add(fp);
    }

    /// <summary>
    /// 加入byte数组区域，用于生成帧
    /// </summary>
    /// <param name="bta"></param>
    public void addParam(byte[] bta)
    {
        FrameParam fp = new FrameParam();
        fp.SetValue(bta, bta.length);
        ParamList.add(fp);
    }

    // 根据各项已设置的属性生成帧
    // <param name="buff">生产的帧字节list</param>
    // <returns>生成结果，等于0表示执行失败，否则返回帧字节list长度</returns>
    protected int MakeBuff(List<Byte> buff)
    {
        byte[] tmpA = new byte[128];
        tmpA[0] = 0x68;
        tmpA[1] = (byte)(funcCode + (frmDirection.ordinal()) * 128 + (frmResult.ordinal()) * 64);
        //长度L
        tmpA[2] = 13;
        //将表号加入到tmpA
        for (int i = 0; i < 13; i++)
        {
            tmpA[3 + i] = (byte) meterID.charAt(i);
        }
        int pz = 16; //tmpA下标位置
       // 处理参数对象
        for (int i = 0; i < ParamList.size(); i++)
        {
            FrameParam fp = ParamList.get(i);
            //System.out.print(fp.getByteLen()+" ");
            tmpA[2] += (byte)fp.getByteLen();
            //System.out.print(tmpA[2]+" ");
            switch (fp.PT)
            {
                case INT: //整数，将int类型转成16进制字符串（长度是int类型*2），再转成4个字节的对应字节数组
                    int iva=0;
                    iva = fp.GetValue(iva);
                   // System.out.println(iva);
                    //String stmp = iva.ToString("X" + (fp.getByteLen() * 2).ToString());
                    String stmp = Integer.toHexString(iva);
                    while(stmp.length()<fp.getByteLen()*2){
                        stmp = "0"+stmp;
                    }
                    //System.out.println(stmp);
                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        String ss = stmp.substring(j * 2, j * 2 + 2);
                        tmpA[pz] = (byte) Integer.parseInt(ss,16);
                        pz++;
                        //System.out.println(tmpA[pz]);
                    }
                    break;
                case FLT: //浮点数
                    double dva=0.0;
                    dva = fp.GetValue(dva);
                    //stmp = string.Format("{0:000000.00}", dva);
                    stmp = new DecimalFormat("000000.00").format(dva);
                    //System.out.println(stmp);
                    //stmp = stmp.Remove(6, 1);
                    stmp = stmp.substring(0,6)+stmp.substring(7);
                    //System.out.println(stmp);
                    //stmp = stmp.Remove(0, (4 - fp.ByteLen) * 2);
                    stmp = stmp.substring((4 - fp.getByteLen()) * 2);
                    //System.out.println(stmp);
                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        String ss = stmp.substring(j * 2, j * 2 + 2);
                        tmpA[pz] = (byte) Integer.parseInt(ss);
                        pz++;
                    }
                    break;
                case STR: //字符串
                    String sva="";
                    sva = fp.GetValue(sva);
                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        tmpA[pz] = (byte) sva.charAt(j);
                        //System.out.println(tmpA[pz]);
                        pz++;
                    }
                    break;
                case BTA:
                    byte[] bva=null;
                    bva = fp.GetValue(bva);

                    for (int j = 0; j < fp.getByteLen(); j++)
                    {
                        tmpA[pz] = bva[j];
                        //System.out.println(tmpA[i]);
                        pz++;
                    }
                    break;
            }
        }
        //增加时间时间调整参数，总共5个字节
        ///第1个字节为控制字节，其中第1位（最高位）表示是否启用，0：不启用，1：启用
        ///第2位表示调整方向，0：增加；1：递减
        ///第2~5字节为调整时间值
        if (V2)
        {
            //System.out.println("V2");
            tmpA[2] += 5;
            if (timeCorrection == 0)
            {
                for (int i = 0; i < 5; i++)
                {
                    tmpA[pz] = 0;
                    pz++;
                }
            }
            else
            {
                if (timeCorrection > 0)
                {
                    tmpA[pz] = (byte) 128; //累加
                    pz++;
                    //String stmp = timeCorrection.toString("X8");
                    String stmp = Long.toHexString(timeCorrection);
                    while(stmp.length()<8){
                        stmp = "0"+stmp;
                    }
                    for (int i = 0; i < 4; i++)
                    {
                        //tmpA[pz] = Convert.ToByte("0x" + stmp.Substring(i * 2, 2), 16);
                        tmpA[pz] = (byte) Integer.parseInt(stmp.substring(i*2,i*2+2),16);
                        pz++;
                    }
                }
                else
                {
                    tmpA[pz] = (byte) 192; //递减
                    pz++;
                    //String stmp = (timeCorrection * -1).ToString("X8");
                    String stmp = Long.toHexString(timeCorrection * -1);
                    while(stmp.length()<8){
                        stmp = "0"+stmp;
                    }
                    for (int i = 0; i < 4; i++)
                    {
                        tmpA[pz] = (byte) Integer.parseInt(stmp.substring(i*2,i*2+2),16);
                        pz++;
                    }
                }
            }
        }
        //帧ID
        tmpA[2] += 1;
        //System.out.println(tmpA[2]);
        tmpA[pz] = frameID;
        //System.out.println(frameID);
        //计算校验和
        int cs = 0;
        for (int j = 0; j <= pz; j++)
        {
            cs += Byte.toUnsignedInt(tmpA[j]);
        }
        cs = cs % 256;
        //System.out.println(cs);
        pz++;
        tmpA[pz] = (byte)cs;
        pz++;
        //截至码
        tmpA[pz] = 0x16;

        //buff = new byte[pz + 1];
        for (int i = 0; i <= pz; i++) {
            buff.add(tmpA[i]);
        }
        return buff.size();
    }

    // 获取BYTE数组组成的帧
    public byte[] ByteFrame()
    {
        //byte[] buff=null;
        List<Byte> buff = new ArrayList<Byte>();
        if (MakeBuff(buff) == 0)
        {
            byte[] b = new byte[1];
            b[0] = 0;
            return b;
        }
        byte[] rst = new byte[buff.size()];
        for (int i = 0; i < buff.size(); i++)
            rst[i] = buff.get(i);
        return rst;
    }

    // 生成帧字节数组，带加密
    public byte[] ProcFrame(String sKey) {
        if (funcCode == 0)
        {
            return new byte[0];
        }
        if (meterID == "")
        {
            return new byte[0];
        }

        byte[] frame = ByteFrame();
        byte[] key = getKey(sKey);

       System.out.print("加密前帧字节数组(含\"68\",\"16\")：");
        for(int i=0;i<frame.length;i++){
            System.out.print(Byte.toUnsignedInt(frame[i])+" ");
        }
        System.out.println("，字节长度："+frame.length);
        System.out.println("16进制形式："+ Hex.BytesToHexString(frame).toUpperCase());
        System.out.print("密钥字节数组：");
        for(int i=0;i<key.length;i++){
            System.out.print(Byte.toUnsignedInt(key[i])+" ");
        }
        System.out.println();

        //除掉68和16在加密
        byte[] bytes = new byte[frame.length-2];
        for(int i=0;i<bytes.length;i++){
            bytes[i] = frame[i+1];
        }
        //加密
        byte[] buff = new byte[0];
        try {
            buff = AES.encrypt(bytes, key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.print("加密后帧字节数组(加密部分不含\"68\",\"16\")：");
        for(int i=0;i<buff.length;i++){
            System.out.print(Byte.toUnsignedInt(buff[i])+" ");
        }
        System.out.println(" ,长度："+buff.length);
        String hexStr = Hex.BytesToHexString(buff);
        System.out.println("加密后16进制帧字符串："+hexStr.toUpperCase());
        System.out.println();
        System.out.println();
        return buff;
    }


}
