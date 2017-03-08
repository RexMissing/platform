package org.whut.meterFrameManagement.communicationframe.receive;

import org.whut.meterFrameManagement.util.date.DateUtil;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: zhang_minzhong
 * Date: 16-12-12
 * Time: 上午10:34
 * To change this template use File | Settings | File Templates.
 */

/// <summary>
/// 表具数据类，存储表具回传帧中表具状态数据
/// </summary>
public class MeterStatus {
    private String meterID;//表号
    private double remainMoney;//剩余金额
    private int meterRead;//表止码
    private double price;//当前使用气价
    private byte xtzt;//系统状态字节

    private Date meterTime;//表具时间

    private int amount1;
    private int amount2;
    private int amount3;
    private int sumamount;//本月已用气总量
    private int presumamount;//上月用气总量

    // 构造方法
    public MeterStatus() {
        meterID = "";
        meterTime = DateUtil.createDate("2000-01-01 00:00:00");
    }

    // 构造方法。重载1
    public MeterStatus(String meterID) {
        this.meterID = meterID;
        meterTime = DateUtil.createDate("2000-01-01 00:00:00");
    }

    // 构造方法。重载2
    // <param name="mid">表具编号</param>
    // <param name="dataStr">表具状态数据字符串</param>
    public MeterStatus(String meterID, String dataStr) {
        this.meterID = meterID;
        getFromStr(dataStr);
    }

    // 表具编号.
    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    // 表具剩余金额。
    public double getRemainMoney() {
        if ((Byte.toUnsignedInt(xtzt) / 64) % 2 == 1) {//透支
            return remainMoney * -1;
        } else {
            return remainMoney;
        }
    }

    // 表具读数。基表止码
    public int getMeterRead() {
        return meterRead;
    }

    public void setMeterRead(int meterRead) {
        this.meterRead = meterRead;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // 系统状态字节
    public byte getXtzt() {
        return xtzt;
    }

    public void setXtzt(byte xtzt) {
        this.xtzt = xtzt;
    }

    // 传感故障
    public byte getCGGZ() {
        int i = Byte.toUnsignedInt(xtzt) / 4;
        i = i % 2;
        return (byte) i;
    }

    // 阀门位置错。  0：正常；    1：错误
    public byte getFMCW() {
        int i = Byte.toUnsignedInt(xtzt) / 2;
        i %= 2;
        return (byte) i;
    }

    // 阀门位置,0表示阀门开，1表示阀门关
    public byte getFMWZ() {
        int i = Byte.toUnsignedInt(xtzt) % 2;
        return (byte) i;
    }

    // 表具透支状态。0：正常状态；  1：透支状态
    public byte getTZZT() {
        int i = Byte.toUnsignedInt(xtzt) / 64;
        i = i % 2;
        return (byte) i;
    }

    // 系统数据错 0：正常；  1：错误
    public byte getXTSJC() {
        int i = Byte.toUnsignedInt(xtzt) / 128;
        return (byte) i;
    }

    public void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }

    public Date getMeterTime() {
        return meterTime;
    }

    public void setMeterTime(Timestamp meterTime) {
        this.meterTime = meterTime;
    }

    public int getAmount1() {
        return amount1;
    }

    public void setAmount1(int amount1) {
        this.amount1 = amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }

    public int getAmount3() {
        return amount3;
    }

    public void setAmount3(int amount3) {
        this.amount3 = amount3;
    }

    public int getPresumamount() {
        return presumamount;
    }

    public void setPresumamount(int presumamount) {
        this.presumamount = presumamount;
    }

    public int getSumamount() {
        return sumamount;
    }

    public void setSumamount(int sumamount) {
        this.sumamount = sumamount;
    }

    public void getFromStr(String dataStr) {
        remainMoney = 0.0;
        meterRead = 0;
        price = 0.00;
        amount1 = amount2 = amount3 = sumamount = presumamount = 0;
        if (dataStr.length() <= 2) {
            xtzt = (byte) Integer.parseInt(dataStr.substring(0, 2), 16);
        }
        if (dataStr.length() < 10)
            return;
        remainMoney += Integer.parseInt(dataStr.substring(0, 2), 16) * 10000;
        remainMoney += Integer.parseInt(dataStr.substring(2, 4), 16) * 100;
        remainMoney += Integer.parseInt(dataStr.substring(4, 6), 16);
        remainMoney += Integer.parseInt(dataStr.substring(6, 8), 16) * 0.01;
        remainMoney = (double) Math.round(remainMoney*100)/100;

        meterRead += Integer.parseInt(dataStr.substring(8, 10), 16) * 1000000;
        meterRead += Integer.parseInt(dataStr.substring(10, 12), 16) * 1000;
        meterRead += Integer.parseInt(dataStr.substring(12, 14), 16) * 100;
        meterRead += Integer.parseInt(dataStr.substring(14, 16), 16);
        //meterRead += Integer.parseInt(dataStr.substring(8,16),16);
        xtzt = (byte) Integer.parseInt(dataStr.substring(16, 18), 16);

        if (dataStr.length() >= 92) { //数据字符串包含46个字节数据,统一回传帧数据
            price += Integer.parseInt(dataStr.substring(22, 24), 16);
            price += Integer.parseInt(dataStr.substring(24, 26), 16)*0.01;
            price = (double)Math.round(price*100)/100;
            amount1 += Integer.parseInt(dataStr.substring(26, 28), 16) * 100;
            amount1 += Integer.parseInt(dataStr.substring(28, 30), 16);
            //amount1 = Integer.parseInt(dataStr.substring(26,30),16);
            amount2 += Integer.parseInt(dataStr.substring(30, 32), 16) * 100;
            amount2 += Integer.parseInt(dataStr.substring(32, 34), 16);
            //amount2 = Integer.parseInt(dataStr.substring(30,34),16);
            amount3 += Integer.parseInt(dataStr.substring(34, 36), 16) * 100;
            amount3 += Integer.parseInt(dataStr.substring(36, 38), 16);
            //amount3 = Integer.parseInt(dataStr.substring(34,38),16);
            sumamount += Integer.parseInt(dataStr.substring(38, 40), 16) * 100;
            sumamount += Integer.parseInt(dataStr.substring(40, 42), 16);
            //sumamount = Integer.parseInt(dataStr.substring(38,42),16);
            //上月用气量
            presumamount += Integer.parseInt(dataStr.substring(18, 20), 16) * 100;
            presumamount += Integer.parseInt(dataStr.substring(20, 22), 16);
            //presumamount = Integer.parseInt(dataStr.substring(18,22),16);
            //取得表具时间
            Long longTime = Long.parseLong(dataStr.substring(42, 50), 16);

            meterTime.setTime(meterTime.getTime() + longTime*1000);
        }
    }

    public static void main(String[] args) {
        new MeterStatus();
    }

}
