package org.whut.meterManagement.smsmeterservice.service_entity;

import org.whut.meterManagement.sqldatalib.sqlhelper.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenfu on 2016/12/22.
 */
/// <summary>
/// 单价对象
/// </summary>
public class WS_Price {

    private int saleStrategyID;
    private String strategyName;
    private double price;
    private double stagePrice1;
    private double stagePrice2;
    private double stagePrice3;
    private int beginAmount1;
    private int beginAmount2;
    private int beginAmount3;
    private int cycleLength;

    public int getSaleStrategyID() {
        return saleStrategyID;
    }

    public void setSaleStrategyID(int saleStrategyID) {
        this.saleStrategyID = saleStrategyID;
    }

    public String getStrategyName() {
        return strategyName;
    }

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getStagePrice1() {
        return stagePrice1;
    }

    public void setStagePrice1(double stagePrice1) {
        this.stagePrice1 = stagePrice1;
    }

    public double getStagePrice2() {
        return stagePrice2;
    }

    public void setStagePrice2(double stagePrice2) {
        this.stagePrice2 = stagePrice2;
    }

    public double getStagePrice3() {
        return stagePrice3;
    }

    public void setStagePrice3(double stagePrice3) {
        this.stagePrice3 = stagePrice3;
    }

    public int getBeginAmount1() {
        return beginAmount1;
    }

    public void setBeginAmount1(int beginAmount1) {
        this.beginAmount1 = beginAmount1;
    }

    public int getBeginAmount2() {
        return beginAmount2;
    }

    public void setBeginAmount2(int beginAmount2) {
        this.beginAmount2 = beginAmount2;
    }

    public int getBeginAmount3() {
        return beginAmount3;
    }

    public void setBeginAmount3(int beginAmount3) {
        this.beginAmount3 = beginAmount3;
    }

    public int getCycleLength() {
        return cycleLength;
    }

    public void setCycleLength(int cycleLength) {
        this.cycleLength = cycleLength;
    }

    public WS_Price() {
    }

    public WS_Price(int saleStrategyID, String strategyName, double price, double stagePrice1, double stagePrice2, double stagePrice3, int beginAmount1, int beginAmount2, int beginAmount3, int cycleLength) {
        this.saleStrategyID = saleStrategyID;
        this.strategyName = strategyName;
        this.price = price;
        this.stagePrice1 = stagePrice1;
        this.stagePrice2 = stagePrice2;
        this.stagePrice3 = stagePrice3;
        this.beginAmount1 = beginAmount1;
        this.beginAmount2 = beginAmount2;
        this.beginAmount3 = beginAmount3;
        this.cycleLength = cycleLength;
    }

    public WS_Price(int SID) {
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TSaleStrategy where FStrategyID=" + SID);
        try {
            if (rs.next()) {
                saleStrategyID = rs.getInt("FStrategyID");
                strategyName = rs.getString("FStrategyName");
                price = rs.getDouble("FPrice");
                stagePrice1 = rs.getDouble("FStagePrice1");
                stagePrice2 = rs.getDouble("FStagePrice2");
                stagePrice3 = rs.getDouble("FStagePrice3");
                beginAmount1 = rs.getInt("FBeginAmount1");
                beginAmount2 = rs.getInt("FBeginAmount2");
                beginAmount3 = rs.getInt("FBeginAmount3");
                cycleLength = rs.getInt("FCycleLength");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);
    }

    public static List<WS_Price> getAll() {
        List<WS_Price> wpList = new ArrayList<WS_Price>();
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TSaleStrategy where FEnabled=1");
        try {
            while (rs.next()) {
                wpList.add(new WS_Price(rs.getInt("FStrategyID"),
                        rs.getString("FStrategyName"),
                        rs.getDouble("FPrice"),
                        rs.getDouble("FStagePrice1"),
                        rs.getDouble("FStagePrice2"),
                        rs.getDouble("FStagePrice3"),
                        rs.getInt("FBeginAmount1"),
                        rs.getInt("FBeginAmount2"),
                        rs.getInt("FBeginAmount3"),
                        rs.getInt("FCycleLength")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wpList;
    }

}
