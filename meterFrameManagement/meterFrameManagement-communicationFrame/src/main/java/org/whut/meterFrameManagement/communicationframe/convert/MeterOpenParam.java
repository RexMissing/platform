package org.whut.meterFrameManagement.communicationframe.convert;

import java.util.Date;

/**
 * Created by zhang_minzhong on 2017/3/7.
 */
public class MeterOpenParam {
    private String meterID;
    private String funCode;
    private byte frameID;
    private String key;
    private double money;
    double p0;
    private double p1;
    private double p2;
    private double p3;
    private int a1;
    private int a2;
    private int a3;
    private String nkey;
    private Date beginDT;
    private byte clen;
    private byte cbr;
    private int bzql;
    private int szql;

    public int getA1() {
        return a1;
    }

    public void setA1(int a1) {
        this.a1 = a1;
    }

    public int getA2() {
        return a2;
    }

    public void setA2(int a2) {
        this.a2 = a2;
    }

    public int getA3() {
        return a3;
    }

    public void setA3(int a3) {
        this.a3 = a3;
    }

    public Date getBeginDT() {
        return beginDT;
    }

    public void setBeginDT(Date beginDT) {
        this.beginDT = beginDT;
    }

    public int getBzql() {
        return bzql;
    }

    public void setBzql(int bzql) {
        this.bzql = bzql;
    }

    public byte getCbr() {
        return cbr;
    }

    public void setCbr(byte cbr) {
        this.cbr = cbr;
    }

    public byte getClen() {
        return clen;
    }

    public void setClen(byte clen) {
        this.clen = clen;
    }

    public byte getFrameID() {
        return frameID;
    }

    public void setFrameID(byte frameID) {
        this.frameID = frameID;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getNkey() {
        return nkey;
    }

    public void setNkey(String nkey) {
        this.nkey = nkey;
    }

    public double getP0() {
        return p0;
    }

    public void setP0(double p0) {
        this.p0 = p0;
    }

    public double getP1() {
        return p1;
    }

    public void setP1(double p1) {
        this.p1 = p1;
    }

    public double getP2() {
        return p2;
    }

    public void setP2(double p2) {
        this.p2 = p2;
    }

    public double getP3() {
        return p3;
    }

    public void setP3(double p3) {
        this.p3 = p3;
    }

    public int getSzql() {
        return szql;
    }

    public void setSzql(int szql) {
        this.szql = szql;
    }
}
