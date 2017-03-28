package org.whut.meterFrameManagement.communicationframe.convert;

/**
 * Created by zhang_minzhong on 2017/3/7.
 */
public class RunTimeGasParam {
    private String meterID;
    private String funCode;
    private byte frameID;
    private String key;
    private byte czfs1;
    private int zyql;
    private byte czfs2;
    private int bzq;
    private byte czfs3;
    private int szq;
    private byte fs;
    private byte[] lszqyl;
    private long timeCorrection;

    public int getBzq() {
        return bzq;
    }

    public void setBzq(int bzq) {
        this.bzq = bzq;
    }

    public byte getCzfs1() {
        return czfs1;
    }

    public void setCzfs1(byte czfs1) {
        this.czfs1 = czfs1;
    }

    public byte getCzfs2() {
        return czfs2;
    }

    public void setCzfs2(byte czfs2) {
        this.czfs2 = czfs2;
    }

    public byte getCzfs3() {
        return czfs3;
    }

    public void setCzfs3(byte czfs3) {
        this.czfs3 = czfs3;
    }

    public byte getFrameID() {
        return frameID;
    }

    public void setFrameID(byte frameID) {
        this.frameID = frameID;
    }

    public byte getFs() {
        return fs;
    }

    public void setFs(byte fs) {
        this.fs = fs;
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

    public byte[] getLszqyl() {
        return lszqyl;
    }

    public void setLszqyl(byte[] lszqyl) {
        this.lszqyl = lszqyl;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public int getSzq() {
        return szq;
    }

    public void setSzq(int szq) {
        this.szq = szq;
    }

    public long getTimeCorrection() {
        return timeCorrection;
    }

    public void setTimeCorrection(long timeCorrection) {
        this.timeCorrection = timeCorrection;
    }

    public int getZyql() {
        return zyql;
    }

    public void setZyql(int zyql) {
        this.zyql = zyql;
    }
}
