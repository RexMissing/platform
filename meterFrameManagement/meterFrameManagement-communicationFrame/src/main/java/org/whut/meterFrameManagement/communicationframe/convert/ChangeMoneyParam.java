package org.whut.meterFrameManagement.communicationframe.convert;

/**
 * Created by zhang_minzhong on 2017/3/7.
 */
public class ChangeMoneyParam {
    private String meterID;
    private String funCode;
    private byte frameID;
    private String key;
    private double money;
    private int czfs;
    private int hxbj;
    private long timeCorrection;

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

    public int getCzfs() {
        return czfs;
    }

    public void setCzfs(int czfs) {
        this.czfs = czfs;
    }

    public int getHxbj() {
        return hxbj;
    }

    public void setHxbj(int hxbj) {
        this.hxbj = hxbj;
    }

    public long getTimeCorrection() {
        return timeCorrection;
    }

    public void setTimeCorrection(long timeCorrection) {
        this.timeCorrection = timeCorrection;
    }
}
