package org.whut.meterFrameManagement.communicationframe.convert;

/**
 * Created by zhang_minzhong on 2017/3/7.
 */
public class SetBeatHeartParam {
    private String meterID;
    private String funCode;
    private byte frameID;
    private String key;
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

    public long getTimeCorrection() {
        return timeCorrection;
    }

    public void setTimeCorrection(long timeCorrection) {
        this.timeCorrection = timeCorrection;
    }
}
