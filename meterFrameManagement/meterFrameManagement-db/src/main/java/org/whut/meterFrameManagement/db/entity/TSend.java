package org.whut.meterFrameManagement.db.entity;

import java.sql.Timestamp;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
public class TSend {
    private int id;
    private String meterID;
    private int funCode;
    private int frameID;
    private String sendFrame;
    private Timestamp sendDate;
    private boolean sent;
    private Timestamp sentDate;

    public int getFrameID() {
        return frameID;
    }

    public void setFrameID(int frameID) {
        this.frameID = frameID;
    }

    public int getFunCode() {
        return funCode;
    }

    public void setFunCode(int funCode) {
        this.funCode = funCode;
    }

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

    public Timestamp getSendDate() {
        return sendDate;
    }

    public void setSendDate(Timestamp sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendFrame() {
        return sendFrame;
    }

    public void setSendFrame(String sendFrame) {
        this.sendFrame = sendFrame;
    }

    public boolean isSent() {
        return sent;
    }

    public void setSent(boolean sent) {
        this.sent = sent;
    }

    public Timestamp getSentDate() {
        return sentDate;
    }

    public void setSentDate(Timestamp sentDate) {
        this.sentDate = sentDate;
    }
}
