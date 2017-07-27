package org.whut.meterFrameManagement.db.entity;

import java.sql.Timestamp;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
public class TReceive {
    private int id;
    private String meterID;
    private String receiveFrame;
    private Timestamp receiveDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public String getReceiveFrame() {
        return receiveFrame;
    }

    public void setReceiveFrame(String receiveFrame) {
        this.receiveFrame = receiveFrame;
    }

    public Timestamp getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Timestamp receiveDate) {
        this.receiveDate = receiveDate;
    }
}
