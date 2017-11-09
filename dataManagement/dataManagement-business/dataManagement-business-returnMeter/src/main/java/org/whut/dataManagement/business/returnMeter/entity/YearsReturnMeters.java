package org.whut.dataManagement.business.returnMeter.entity;

/**
 * Created by Administrator on 2017/9/5.
 */
public class YearsReturnMeters {
    private int pyear;
    private int cyear;
    private String fmetername;
    private int ReturnMeters;

    public int getPyear() {
        return pyear;
    }

    public void setPyear(int pyear) {
        this.pyear = pyear;
    }

    public int getCyear() {
        return cyear;
    }

    public void setCyear(int cyear) {
        this.cyear = cyear;
    }

    public String getFmetername() {
        return fmetername;
    }

    public void setFmetername(String fmetername) {
        this.fmetername = fmetername;
    }

    public int getReturnMeters() {
        return ReturnMeters;
    }

    public void setReturnMeters(int returnMeters) {
        ReturnMeters = returnMeters;
    }
}
