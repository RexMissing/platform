package org.whut.dataManagement.business.returnMeter.entity;

/**
 * Created by Administrator on 2017/9/5.
 */
public class MonthProductions {
    private String year;
    private int month;
    private int productions;

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

    public int getProductions() {
        return productions;
    }

    public void setProductions(int productions) {
        this.productions = productions;
    }
}
