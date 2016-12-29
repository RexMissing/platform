package org.whut.meterManagement.smsmeterservice.service_entity;

import org.whut.meterManagement.sqldatalib.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by chenfu on 2016/12/29.
 */
/// <summary>
/// 短信状态报告
/// </summary>
public class WS_SmsReport {
    private String sismsid;
    private int reportCode;
    private String reportDesc;
    private Date reportTime;
    private String destAddr;

    public String getSismsid() {
        return sismsid;
    }

    public void setSismsid(String sismsid) {
        this.sismsid = sismsid;
    }

    public int getReportCode() {
        return reportCode;
    }

    public void setReportCode(int reportCode) {
        this.reportCode = reportCode;
    }

    public String getReportDesc() {
        return reportDesc;
    }

    public void setReportDesc(String reportDesc) {
        this.reportDesc = reportDesc;
    }

    public Date getReportTime() {
        return reportTime;
    }

    public void setReportTime(Date reportTime) {
        this.reportTime = reportTime;
    }

    public String getDestAddr() {
        return destAddr;
    }

    public void setDestAddr(String destAddr) {
        this.destAddr = destAddr;
    }

    public WS_SmsReport() {
    }

    public static WS_SmsReport getBySismsid(String sis) {
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TSMS_Report where FSismsid='" + sis + "'");
        WS_SmsReport wsr = new WS_SmsReport();
        try {
            if (!rs.next()) {
                return null;
            }
            /*wsr.Sismsid = sis;
            wsr.DestAddr = rs.GetStringValue("FPhone");
            wsr.ReportCode = rs.GetIntValue("FRPT_CODE");
            wsr.ReportDesc = rs.GetStringValue("FRPT_DESC");
            wsr.ReportTime = rs.GetDateTimeValue("FRPT_TIME");*/
            wsr.setSismsid(sis);
            wsr.setDestAddr(rs.getString("FPhone"));
            wsr.setReportCode(rs.getInt("FRPT_CODE"));
            wsr.setReportDesc(rs.getString("FRPT_DESC"));
            wsr.setReportTime(rs.getDate("FRPT_TIME"));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return wsr;
    }
}
