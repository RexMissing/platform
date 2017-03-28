package org.whut.meterFrameManagement.communicationframe.convert;

/**
 * Created by zhang_minzhong on 2017/3/7.
 */
public class ChargeModeParam {
    private String meterID;
    private String funCode;
    private byte frameID;
    private String key;
    private byte sfms;

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

    public byte getSfms() {
        return sfms;
    }

    public void setSfms(byte sfms) {
        this.sfms = sfms;
    }
}
