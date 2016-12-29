package org.whut.meterManagement.smsmeterservice.service_entity;

import org.whut.meterManagement.sqldatalib.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by chenfu on 2016/12/28.
 */
/// <summary>
/// 表具状态
/// </summary>
public class WS_MeterStatus {

    private String meterID;
    private int meterRead;
    private double residual;
    private double price;
    private Date fDateTime;

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public int getMeterRead() {
        return meterRead;
    }

    public void setMeterRead(int meterRead) {
        this.meterRead = meterRead;
    }

    public double getResidual() {
        return residual;
    }

    public void setResidual(double residual) {
        this.residual = residual;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getfDateTime() {
        return fDateTime;
    }

    public void setfDateTime(Date fDateTime) {
        this.fDateTime = fDateTime;
    }

    public WS_MeterStatus(String mid)
    {
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TMeterStatus where FMeterID='" + mid + "'");
        try {
            if (rs.next())
            {
                meterID = mid;
                meterRead = rs.getInt("FMeterRead");
                residual = rs.getDouble("FResidual");
                price = rs.getDouble("FPrice");
                fDateTime = rs.getDate("FDateTime");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}
