package org.whut.meterManagement.smsmeterservice.entity;

import org.whut.meterManagement.database.DB;
import org.whut.meterManagement.sqldatalib.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhang_minzhong on 2016/12/21.
 */
public class MeterPrice {
    private double price;
    private int strategyID;
    private String strategyName;
    private double price1;
    private double price2;
    private double price3;
    private int amount1;
    private int amount2;
    private int amount3;
    private int cycleLength;

    public int getAmount1() {
        return amount1;
    }

    public void setAmount1(int amount1) {
        this.amount1 = amount1;
    }

    public int getAmount2() {
        return amount2;
    }

    public void setAmount2(int amount2) {
        this.amount2 = amount2;
    }

    public int getAmount3() {
        return amount3;
    }

    public void setAmount3(int amount3) {
        this.amount3 = amount3;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(int cycleLength) {
        this.cycleLength = cycleLength;
    }

    public double getPrice1() {
        return price1;
    }

    public void setPrice1(double price1) {
        this.price1 = price1;
    }

    public double getPrice2() {
        return price2;
    }

    public void setPrice2(double price2) {
        this.price2 = price2;
    }

    public double getPrice3() {
        return price3;
    }

    public void setPrice3(double price3) {
        this.price3 = price3;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(int strategyID) {
        this.strategyID = strategyID;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public static MeterPrice getMeterPrice(int saleStrategyID,SqlHelper sqlh){
        ResultSet rs=null;
        MeterPrice mtp = new MeterPrice();
        try {
            rs = sqlh.executeQuery("select * from TSaleStrategy where FStrategyID=" + saleStrategyID);
            rs.next();
            mtp.setStrategyID(saleStrategyID);
            mtp.setPrice(rs.getDouble("FPrice"));
            mtp.setStrategyName(rs.getString("FStrategyName"));
            mtp.setPrice1(rs.getDouble("FStagePrice1"));
            mtp.setPrice2(rs.getDouble("FStagePrice2"));
            mtp.setPrice3(rs.getDouble("FStagePrice3"));
            mtp.setAmount1(rs.getInt("FBeginAmount1"));
            mtp.setAmount2(rs.getInt("FBeginAmount2"));
            mtp.setAmount3(rs.getInt("FBeginAmount3"));
            mtp.setCycleLength(rs.getInt("FCycleLength"));
        } catch (SQLException e) {
            DB.closeConn(rs);
            e.printStackTrace();
        } finally {
            DB.closeConn(rs);
        }
        return mtp;
    }
}
