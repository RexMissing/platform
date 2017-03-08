package org.whut.meterFrameManagement.communicationframe.receive;

import org.whut.meterFrameManagement.communicationframe.enums.FrameDirection;
import org.whut.meterFrameManagement.communicationframe.enums.FrameResult;

import java.util.List;

/**
 * Created by zhang_minzhong on 2017/2/28.
 */
public class ReceiveData {
    private String meterID;
    private String funCode;
    private int frameID;
    private FrameDirection direction;
    private FrameResult frameResult;
    private int xtztzj;
    private String fmwz;
    private String fmcw;
    private String cggz;
    private String tzzt;
    private String xtsjc;
    private double remainMoney;
    private int meterRead;

    private int preSumAmount;
    private double price;
    private int amount1;
    private int amount2;
    private int amount3;
    private int sumAmount;
    private String meterTime;
    private int funCount;
    private List<FunCodeFrameId> list;

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

    public String getCggz() {
        return cggz;
    }

    public void setCggz(String cggz) {
        this.cggz = cggz;
    }

    public FrameDirection getDirection() {
        return direction;
    }

    public void setDirection(FrameDirection direction) {
        this.direction = direction;
    }

    public String getFmcw() {
        return fmcw;
    }

    public void setFmcw(String fmcw) {
        this.fmcw = fmcw;
    }

    public String getFmwz() {
        return fmwz;
    }

    public void setFmwz(String fmwz) {
        this.fmwz = fmwz;
    }

    public int getFrameID() {
        return frameID;
    }

    public void setFrameID(int frameID) {
        this.frameID = frameID;
    }

    public FrameResult getFrameResult() {
        return frameResult;
    }

    public void setFrameResult(FrameResult frameResult) {
        this.frameResult = frameResult;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public int getFunCount() {
        return funCount;
    }

    public void setFunCount(int funCount) {
        this.funCount = funCount;
    }

    public List<FunCodeFrameId> getList() {
        return list;
    }

    public void setList(List<FunCodeFrameId> list) {
        this.list = list;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public int getMeterRead() {
        return meterRead;
    }

    public void setMeterRead(int meterRead) {
        this.meterRead = meterRead;
    }

    public String getMeterTime() {
        return meterTime;
    }

    public void setMeterTime(String meterTime) {
        this.meterTime = meterTime;
    }

    public int getPreSumAmount() {
        return preSumAmount;
    }

    public void setPreSumAmount(int preSumAmount) {
        this.preSumAmount = preSumAmount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getRemainMoney() {
        return remainMoney;
    }

    public void setRemainMoney(double remainMoney) {
        this.remainMoney = remainMoney;
    }

    public int getSumAmount() {
        return sumAmount;
    }

    public void setSumAmount(int sumAmount) {
        this.sumAmount = sumAmount;
    }

    public String getTzzt() {
        return tzzt;
    }

    public void setTzzt(String tzzt) {
        this.tzzt = tzzt;
    }

    public String getXtsjc() {
        return xtsjc;
    }

    public void setXtsjc(String xtsjc) {
        this.xtsjc = xtsjc;
    }

    public int getXtztzj() {
        return xtztzj;
    }

    public void setXtztzj(int xtztzj) {
        this.xtztzj = xtztzj;
    }
}
