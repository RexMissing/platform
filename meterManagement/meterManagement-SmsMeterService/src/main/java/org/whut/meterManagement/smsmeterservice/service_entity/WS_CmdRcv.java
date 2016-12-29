package org.whut.meterManagement.smsmeterservice.service_entity;


import org.whut.meterManagement.sqldatalib.sqlhelper.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by chenfu on 2016/12/29.
 */
public class WS_CmdRcv {
    private int fID;
    private String meterID;
    private String sismsid;
    private int meterRead;
    private double meterMoney;
    private boolean valveState;
    private double price;
    private Date receDate;
    private Date meterTime;
    private int sumAmount;
    private int preAmount;
    private int amount1;
    private int amount2;
    private int amount3;
    private byte functionCode;

    public int getfID() {
        return fID;
    }

    public void setfID(int fID) {
        this.fID = fID;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public String getSismsid() {
        return sismsid;
    }

    public void setSismsid(String sismsid) {
        this.sismsid = sismsid;
    }

    public int getMeterRead() {
        return meterRead;
    }

    public void setMeterRead(int meterRead) {
        this.meterRead = meterRead;
    }

    public double getMeterMoney() {
        return meterMoney;
    }

    public void setMeterMoney(double meterMoney) {
        this.meterMoney = meterMoney;
    }

    public boolean isValveState() {
        return valveState;
    }

    public void setValveState(boolean valveState) {
        this.valveState = valveState;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getReceDate() {
        return receDate;
    }

    public void setReceDate(Date receDate) {
        this.receDate = receDate;
    }

    public Date getMeterTime() {
        return meterTime;
    }

    public void setMeterTime(Date meterTime) {
        this.meterTime = meterTime;
    }

    public int getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(int sumAmount) {
        this.sumAmount = sumAmount;
    }

    public int getPreAmount() {
        return preAmount;
    }

    public void setPreAmount(int preAmount) {
        this.preAmount = preAmount;
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

    public byte getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(byte functionCode) {
        this.functionCode = functionCode;
    }

    public WS_CmdRcv() {
    }

    public static WS_CmdRcv getBySismsid(String sismsid) {
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TReceiveCommand where FSismsid='" + sismsid + "'");
        WS_CmdRcv wcr = new WS_CmdRcv();
        try {
            if (!rs.next()) {
                return null;
            }
            /*wcr.FID = rs.GetIntValue("FID");
            wcr.MeterID = rs.GetStringValue("FMeterID");
            wcr.Sismsid = sismsid;
            wcr.FunctionCode = rs.GetByteValue("FFuncCode");
            wcr.MeterRead = rs.GetIntValue("FMeterRead");
            wcr.MeterMoney = rs.GetDecimalValue("FMoney");
            wcr.ValveState = rs.GetBoolValue("FFMWZ");
            wcr.ReceDate = rs.GetDateTimeValue("FDateTime");
            wcr.Price = rs.GetDecimalValue("FPrice");
            wcr.MeterTime = rs.GetDateTimeValue("FMeterTime");
            wcr.SumAmount = rs.GetIntValue("FSumAmount");
            wcr.PreAmount = rs.GetIntValue("FPreSumAmount");
            wcr.Amount1 = rs.GetIntValue("FAmount1");
            wcr.Amount2 = rs.GetIntValue("FAmount2");
            wcr.Amount3 = rs.GetIntValue("FAmount3");*/
            wcr.setfID(rs.getInt("FID"));
            wcr.setMeterID(rs.getString("FMeterID"));
            wcr.setSismsid(sismsid);
            wcr.setFunctionCode(rs.getByte("FFuncCode"));
            wcr.setMeterRead(rs.getInt("FMeterRead"));
            wcr.setMeterMoney(rs.getDouble("FMoney"));
            wcr.setValveState(rs.getBoolean("FFMWZ"));
            wcr.setReceDate(rs.getDate("FDateTime"));
            wcr.setPrice(rs.getDouble("FPrice"));
            wcr.setMeterTime(rs.getDate("FMeterTime"));
            wcr.setSumAmount(rs.getInt("FSumAmount"));
            wcr.setPreAmount(rs.getInt("FPreSumAmount"));
            wcr.setAmount1(rs.getInt("FAmount1"));
            wcr.setAmount2(rs.getInt("FAmount2"));
            wcr.setAmount3(rs.getInt("FAmount3"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wcr;
    }
}
