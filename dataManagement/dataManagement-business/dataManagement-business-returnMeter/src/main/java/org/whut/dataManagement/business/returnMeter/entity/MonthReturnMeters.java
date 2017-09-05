package org.whut.dataManagement.business.returnMeter.entity;

/**
 * Created by Administrator on 2017/9/5.
 */
public class MonthReturnMeters {
    private String year;
    private int month;
    private int ReturnMeters;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getReturnMeters() {
        return ReturnMeters;
    }

    public void setReturnMeters(int returnMeters) {
        ReturnMeters = returnMeters;
    }
}
