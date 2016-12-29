package org.whut.meterManagement.smsmeterservice.service_entity;


import org.whut.meterManagement.sqldatalib.sqlhelper.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/29.
 */
/// <summary>
/// 表具动态信息
/// </summary>
public class WS_MeterDynamic {
    private String meterID;
    private int beginRead;
    private int endRead;
    private double price;
    private String sismsid;
    private Date fDateTime;
    private int totalUsed;
    private double totalMoney;

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public int getBeginRead() {
        return beginRead;
    }

    public void setBeginRead(int beginRead) {
        this.beginRead = beginRead;
    }

    public int getEndRead() {
        return endRead;
    }

    public void setEndRead(int endRead) {
        this.endRead = endRead;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getSismsid() {
        return sismsid;
    }

    public void setSismsid(String sismsid) {
        this.sismsid = sismsid;
    }

    public Date getfDateTime() {
        return fDateTime;
    }

    public void setfDateTime(Date fDateTime) {
        this.fDateTime = fDateTime;
    }

    public int getTotalUsed() {
        return totalUsed;
    }

    public void setTotalUsed(int totalUsed) {
        this.totalUsed = totalUsed;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public WS_MeterDynamic() {
    }

    public static List<WS_MeterDynamic> getByMeterID(String MID) {
        List<WS_MeterDynamic> wmdList = new ArrayList<WS_MeterDynamic>();
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TMeterDynamic where FMeterID='" + MID + "' order by FBeginRead");
        WS_MeterDynamic curMD = null;
        WS_MeterDynamic preMD = null;
        try {
            while (rs.next()) {
                curMD = new WS_MeterDynamic();
                curMD.setBeginRead(rs.getInt("FBeginRead"));
                curMD.setEndRead(rs.getInt("FEndRead"));
                curMD.setPrice(rs.getDouble("FGasPrice"));
                curMD.setSismsid(rs.getString("FSismsid"));
                curMD.setfDateTime(rs.getDate("FDateTime"));
                curMD.setTotalUsed(curMD.getEndRead() - curMD.getBeginRead() + 1);
                curMD.setTotalMoney(curMD.getPrice() * curMD.getTotalUsed());

                if (preMD != null) {
                    if (curMD.getPrice() == preMD.getPrice()) {
                        preMD.setEndRead(curMD.getEndRead());
                        preMD.setTotalUsed(preMD.getEndRead() - preMD.getBeginRead() + 1);
                        preMD.setTotalMoney(preMD.getPrice() * preMD.getTotalUsed());
                    } else {
                        wmdList.add(curMD);
                        preMD = curMD;
                    }
                } else {
                    wmdList.add(curMD);
                    preMD = curMD;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wmdList;
    }
}
