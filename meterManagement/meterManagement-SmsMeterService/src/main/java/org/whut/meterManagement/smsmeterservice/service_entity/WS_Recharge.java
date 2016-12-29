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
/// <summary>
/// 充值明细
/// </summary>
public class WS_Recharge {
    private int chargeID;
    private int userID;
    private double chargeMoney;
    private Date chargeDate;
    private String operatorID;
    private boolean success;
    private WS_CmdRcv receive;

    public int getChargeID() {
        return chargeID;
    }

    public void setChargeID(int chargeID) {
        this.chargeID = chargeID;

        Object o = StdUtils.getSqlh().executeScalar("select FSismsid from TSetMoney where FChargeID=" + chargeID);
        if (o == null) {
            success = false;
            return;
        }
        String sis = o.toString();
        receive = WS_CmdRcv.getBySismsid(sis);
        if (receive == null) {
            success = false;
        } else {
            success = true;
        }
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public double getChargeMoney() {
        return chargeMoney;
    }

    public void setChargeMoney(double chargeMoney) {
        this.chargeMoney = chargeMoney;
    }

    public Date getChargeDate() {
        return chargeDate;
    }

    public void setChargeDate(Date chargeDate) {
        this.chargeDate = chargeDate;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public WS_CmdRcv getReceive() {
        return receive;
    }

    public void setReceive(WS_CmdRcv receive) {
        this.receive = receive;
    }

    public WS_Recharge() {
    }

    public WS_Recharge(int chargeID, int userID, double chargeMoney, Date chargeDate, String operatorID) {
        this.chargeID = chargeID;
        this.userID = userID;
        this.chargeMoney = chargeMoney;
        this.chargeDate = chargeDate;
        this.operatorID = operatorID;
    }

    public static List<WS_Recharge> getRechargeByUserID(int UID) {
        List<WS_Recharge> wrList = new ArrayList<WS_Recharge>();
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TRecharge where FUserID=" + UID + " order by FDateTime desc");
        try {
            while (rs.next()) {
                wrList.add(new WS_Recharge(
                        rs.getInt("FChargeID"),
                        UID,
                        rs.getDouble("FChargeMoney"),
                        rs.getDate("FDateTime"),
                        rs.getString("FOperatorID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wrList;
    }

    public static List<WS_Recharge> getRechargeByDateRange(Date b, Date e, String opid) {
        List<WS_Recharge> wrList = new ArrayList<WS_Recharge>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TRecharge where FDateTime>='" + dateFormat.format(b) + "' and FDateTime<'"
                + dateFormat.format(e) + "' and FOperatorID='" + opid + "'");
        try {
            while (rs.next()){
                WS_Recharge wr = new WS_Recharge();
                /*wr.chargeID = rs.GetIntValue("FChargeID", i);
                wr.UserID = rs.GetIntValue("FUserID", i);
                wr.ChargeMoney = rs.GetDecimalValue("FChargeMoney", i);
                wr.ChargeDate = rs.GetDateTimeValue("FDateTime", i);
                wr.OperatorID = rs.GetStringValue("FOperatorID", i);*/
                wr.setChargeID(rs.getInt("FChargeID"));
                wr.setUserID(rs.getInt("FUserID"));
                wr.setChargeMoney(rs.getDouble("FChargeMoney"));
                wr.setChargeDate(rs.getDate("FDateTime"));
                wr.setOperatorID(rs.getString("FOperatorID"));
                wrList.add(wr);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wrList;
    }
}
