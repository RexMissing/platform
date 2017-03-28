package org.whut.meterFrameManagement.db.entity;

import java.sql.Timestamp;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
public class TSend {
    private int id;
    private String meterID;
    private String sendFrame;
    private Timestamp sendDate;

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

    public String getSendFrame() {
        return sendFrame;
    }

    public void setSendFrame(String sendFrame) {
        this.sendFrame = sendFrame;
    }

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }
}
