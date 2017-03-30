package org.whut.meterFrameManagement.db.entity;

/**
 * Created by chenfu on 2017/3/30.
 */
public class FrameKey {
    private int id;
    private String meterID;
    private String keyStr;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyStr() {
        return keyStr;
    }

    public void setKeyStr(String keyStr) {
        this.keyStr = keyStr;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }
}
