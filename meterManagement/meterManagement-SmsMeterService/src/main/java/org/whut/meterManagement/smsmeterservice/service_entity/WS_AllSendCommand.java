package org.whut.meterManagement.smsmeterservice.service_entity;


import org.whut.meterManagement.sqldatalib.sqlhelper.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/29.
 */
public class WS_AllSendCommand {
    private int ID;
    private int userID;
    private String meterID;
    private byte funcCode;
    private Date sendDate;
    private boolean success;
    private String sismsid;
    private Date scheduleTime;
    private WS_SmsReport report;
    private WS_CmdRcv receive;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public byte getFuncCode() {
        return funcCode;
    }

    public void setFuncCode(byte funcCode) {
        this.funcCode = funcCode;
    }

    public Date getSendDate() {
        return sendDate;
    }

    public void setSendDate(Date sendDate) {
        this.sendDate = sendDate;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getSismsid() {
        return sismsid;
    }

    public void setSismsid(String sismsid) {
        this.sismsid = sismsid;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public WS_SmsReport getReport() {
        return report;
    }

    public void setReport(WS_SmsReport report) {
        this.report = report;
    }

    public WS_CmdRcv getReceive() {
        return receive;
    }

    public void setReceive(WS_CmdRcv receive) {
        this.receive = receive;
    }

    private static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    //构造方法
    public WS_AllSendCommand() {
    }

    public static List<WS_AllSendCommand> getByDateRange(Date b, Date e) {
        String strQ = "select A.*,B.*,C.*,D.FUserID from TAllSend A left join TReceiveCommand B on A.FSismsid=B.FSismsid"
                + " left join TSMS_Report C on A.FSismsid=C.FSismsid left join TUser D on A.FMeterID=D.FMeterID where A.FSendTime>='"
                + dateFormat.format(b) + "' and A.FSendTime<'" + dateFormat.format(e) + "'";
        return getBySQL(strQ);
    }

    public static List<WS_AllSendCommand> getByDateRange(Date b, Date e, int userID) {
        String strQ = "select A.*,B.*,C.*,D.FUserID from TAllSend A left join TReceiveCommand B on A.FSismsid=B.FSismsid"
                + " left join TSMS_Report C on A.FSismsid=C.FSismsid left join TUser D on A.FMeterID=D.FMeterID where A.FSendTime>='"
                + dateFormat.format(b) + "' and A.FSendTime<'" + dateFormat.format(e) + "' and D.FUserID=" + userID;
        return getBySQL(strQ);
    }

    public static List<WS_AllSendCommand> getByMeterID(String MID) {
        String strQ = "select A.*,B.*,C.*,D.FUserID from TAllSend A left join TReceiveCommand B on A.FSismsid=B.FSismsid"
                + " left join TSMS_Report C on A.FSismsid=C.FSismsid left join TUser D on A.FMeterID=D.FMeterID where A.FMeterID='"
                + MID + "' and A.FFuncCode<>10 order by A.FSendTime desc";
        return getBySQL(strQ);
    }

    public static List<WS_AllSendCommand> getBySQL(String sql) {
        List<WS_AllSendCommand> waList = new ArrayList<WS_AllSendCommand>();
        ResultSet rs = StdUtils.getSqlh().executeQuery(sql);
        try {
            while (rs.next()) {
                WS_AllSendCommand wa = new WS_AllSendCommand();
                /*wa.ID = rs.GetIntValue(0, i);
                wa.MeterID = rs.GetStringValue(1, i);
                wa.FuncCode = rs.GetByteValue(2, i);
                wa.SendDate = rs.GetDateTimeValue(3, i);
                wa.Sismsid = rs.GetStringValue(4, i);*/
                wa.setID(rs.getInt(0));
                wa.setMeterID(rs.getString(1));
                wa.setFuncCode(rs.getByte(2));
                wa.setSendDate(rs.getDate(3));
                wa.setSismsid(rs.getString(4));

                if (rs.getInt(6) > 0) {
                    /*wa.Success = true;
                    wa.Receive = new WS_CmdRcv();
                    wa.Receive.FID = rs.GetIntValue(6, i);
                    wa.Receive.MeterID = rs.GetStringValue(7, i);
                    wa.Receive.FunctionCode = rs.GetByteValue(9, i);
                    wa.Receive.MeterMoney = rs.GetDecimalValue(11, i);
                    wa.Receive.MeterRead = rs.GetIntValue(12, i);
                    wa.Receive.ValveState = rs.GetBoolValue(14, i);
                    wa.Receive.ReceDate = rs.GetDateTimeValue(15, i);
                    wa.Receive.Sismsid = rs.GetStringValue(16, i);
                    wa.Receive.Price = rs.GetDecimalValue(17, i);
                    wa.Receive.Amount1 = rs.GetIntValue(18, i);
                    wa.Receive.Amount2 = rs.GetIntValue(19, i);
                    wa.Receive.Amount3 = rs.GetIntValue(20, i);
                    wa.Receive.SumAmount = rs.GetIntValue(21, i);
                    wa.Receive.PreAmount = rs.GetIntValue(22, i);
                    wa.Receive.MeterTime = rs.GetDateTimeValue(23, i);*/
                    wa.setSuccess(true);
                    WS_CmdRcv wcr = new WS_CmdRcv();
                    wcr.setfID(rs.getInt(6));
                    wcr.setMeterID(rs.getString(7));
                    wcr.setFunctionCode(rs.getByte(9));
                    wcr.setMeterMoney(rs.getDouble(11));
                    wcr.setMeterRead(rs.getInt(12));
                    wcr.setValveState(rs.getBoolean(14));
                    wcr.setReceDate(rs.getDate(15));
                    wcr.setSismsid(rs.getString(16));
                    wcr.setPrice(rs.getDouble(17));
                    wcr.setAmount1(rs.getInt(18));
                    wcr.setAmount2(rs.getInt(19));
                    wcr.setAmount3(rs.getInt(20));
                    wcr.setSumAmount(rs.getInt(21));
                    wcr.setPreAmount(rs.getInt(22));
                    wcr.setMeterTime(rs.getDate(23));
                    wa.setReceive(wcr);
                } else {
                    wa.setSuccess(false);
                }

                if (rs.getInt(24) > 0) {
                    /*wa.Report = new WS_SmsReport();
                    wa.Report.Sismsid = rs.GetStringValue(25, i);
                    wa.Report.DestAddr = rs.GetStringValue(26, i);
                    wa.Report.ReportCode = rs.GetIntValue(27, i);
                    wa.Report.ReportDesc = rs.GetStringValue(28, i);
                    wa.Report.ReportTime = rs.GetDateTimeValue(29, i);*/
                    WS_SmsReport w_rep = new WS_SmsReport();
                    w_rep.setSismsid(rs.getString(25));
                    w_rep.setDestAddr(rs.getString(26));
                    w_rep.setReportCode(rs.getInt(27));
                    w_rep.setReportDesc(rs.getString(28));
                    w_rep.setReportTime(rs.getDate(29));
                    wa.setReport(w_rep);
                }
                wa.setUserID(rs.getInt(30));
                waList.add(wa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return waList;
    }

    public static List<WS_AllSendCommand> getOtherCommand(String mid, Date dtb, Date dte) {
        List<WS_AllSendCommand> waList = new ArrayList<WS_AllSendCommand>();
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TOtherCommand where FMeterID='" + mid + "' and FDateTime>="
                + "'" + dateFormat.format(dtb) + "' and FDateTime<'" + dateFormat.format(dte) + "' order by FDateTime desc");
        try {
            while (rs.next()) {
                WS_AllSendCommand wa = new WS_AllSendCommand();
                /*wa.MeterID = mid;
                wa.FuncCode = rs.GetByteValue("FFuncCode", i);
                wa.SendDate = rs.GetDateTimeValue("FDateTime", i);
                wa.Sismsid = rs.GetStringValue("FSismsid", i);
                wa.ScheduleTime = rs.GetDateTimeValue("FScheduleTime", i);
                wa.Report = WS_SmsReport.GetBySismsid(wa.Sismsid);
                wa.Receive = WS_CmdRcv.GetBySismsid(wa.Sismsid);
                if (wa.Receive != null) {
                    wa.Success = true;
                }*/
                wa.setMeterID(mid);
                wa.setFuncCode(rs.getByte("FFuncCode"));
                wa.setSendDate(rs.getDate("FDateTime"));
                wa.setSismsid(rs.getString("FSismsid"));
                wa.setScheduleTime(rs.getDate("FScheduleTime"));
                wa.setReport(WS_SmsReport.getBySismsid(wa.getSismsid()));
                wa.setReceive(WS_CmdRcv.getBySismsid(wa.getSismsid()));
                if (wa.getReceive() != null) {
                    wa.setSuccess(true);
                }
                waList.add(wa);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return waList;
    }
}
