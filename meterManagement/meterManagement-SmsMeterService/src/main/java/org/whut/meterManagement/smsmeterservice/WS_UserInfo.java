package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.sqldatalib.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/22.
 */
/// <summary>
/// 用户基本信息
/// </summary>
public class WS_UserInfo {

    private int userID;
    private String userName;
    private String userPhone;
    private String address;
    private double saleTotal;
    private String meterID;
    private String idNo;
    private String area;
    private int strategyID;
    private boolean isStagePrice;
    private Date inputDT;
    private Date openDT;
    private String cardID;
    private String openOp;
    private Date stageBeginDate;
    private int readMeterDay;
    private WS_MeterStatus meterStatus;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getSaleTotal() {
        return saleTotal;
    }

    public void setSaleTotal(double saleTotal) {
        this.saleTotal = saleTotal;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public int getStrategyID() {
        return strategyID;
    }

    public void setStrategyID(int strategyID) {
        this.strategyID = strategyID;
    }

    public boolean isStagePrice() {
        return isStagePrice;
    }

    public void setStagePrice(boolean isStagePrice) {
        this.isStagePrice = isStagePrice;
    }

    public Date getInputDT() {
        return inputDT;
    }

    public void setInputDT(Date inputDT) {
        this.inputDT = inputDT;
    }

    public Date getOpenDT() {
        return openDT;
    }

    public void setOpenDT(Date openDT) {
        this.openDT = openDT;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getOpenOp() {
        return openOp;
    }

    public void setOpenOp(String openOp) {
        this.openOp = openOp;
    }

    public Date getStageBeginDate() {
        return stageBeginDate;
    }

    public void setStageBeginDate(Date stageBeginDate) {
        this.stageBeginDate = stageBeginDate;
    }

    public int getReadMeterDay() {
        return readMeterDay;
    }

    public void setReadMeterDay(int readMeterDay) {
        this.readMeterDay = readMeterDay;
    }

    public WS_MeterStatus getMeterStatus() {
        return meterStatus;
    }

    public void setMeterStatus(WS_MeterStatus meterStatus) {
        this.meterStatus = meterStatus;
    }

    public WS_UserInfo() {
    }

    public WS_UserInfo(int uid) {
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TUser where FUserID=" + uid);
        try {
            if (!rs.next()) {
                return;
            }
            userID = uid;
            userName = rs.getString("FName");
            userPhone = rs.getString("FPhone");
            address = rs.getString("FAddress");
            saleTotal = rs.getDouble("FSaleTotal");
            meterID = rs.getString("FMeterID");
            idNo = rs.getString("FIdNo");
            area = rs.getString("FArea");
            strategyID = rs.getInt("FSaleStrategyID");
            isStagePrice = rs.getBoolean("FIsStagePrice");
            inputDT = rs.getDate("FInputDT");
            readMeterDay = rs.getInt("FReadMeterDay");
            meterStatus = new WS_MeterStatus(meterID);
            stageBeginDate = rs.getDate("FStageBeginDate");
            openDT = rs.getDate("FOpenDT");
            openOp = rs.getString("FOpenOp");
            cardID = rs.getString("FCardID");
        } catch (SQLException e) {
            e.printStackTrace();
            userID = 0;
        }

    }

    public static List<WS_UserInfo> getUsers(int type, String keyvalue) {
        List<WS_UserInfo> wuList = new ArrayList<WS_UserInfo>();
        String sql;
        switch (type) {
            case 0:
                sql = "select FUserID from TUser where FUserID=" + keyvalue;
                break;
            case 1:
                sql = "select FUserID from TUser where FName='" + keyvalue + "'";
                break;
            case 2:
                sql = "select FUserID from TUser where FAddress like '%" + keyvalue + "%'";
                break;
            case 3:
                sql = "select FUserID from TUser where FPhone='" + keyvalue + "'";
                break;
            case 4:
                sql = "select FUserID from TUser where FIDNo='" + keyvalue + "'";
                break;
            case 5:
                sql = "select FUserID from TUser where FMeterID='" + keyvalue + "'";
                break;
            case 6:
                sql = "select FUserID from TUser where FArea='" + keyvalue + "'";
                break;
            case 7:
                sql = "select FUserID from TUser where FCardID='" + keyvalue + "'";
                break;
            default:
                return null;
        }
        ResultSet rs = StdUtils.getSqlh().executeQuery(sql);
        try {
            while (rs.next()) {
                wuList.add(new WS_UserInfo(rs.getInt("FUserID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wuList;
    }

    public static List<WS_UserInfo> getUsersMultiConditions(List<QCondition> conds) {
        List<WS_UserInfo> wuList = new ArrayList<WS_UserInfo>();
        String sql = "select FUserID from TUser where";
        for (int i = 0; i < conds.size(); i++) {
            if (i == 0) {
                sql += " ";
            } else {
                sql += " and ";
            }
            switch (conds.get(i).getType()) {
                case 0:
                    sql += "FUserID=" + conds.get(i).getValue();
                    break;
                case 1:
                    sql += "FName='" + conds.get(i).getValue() + "'";
                    break;
                case 2:
                    sql += "FAddress like '%" + conds.get(i).getValue() + "%'";
                    break;
                case 3:
                    sql += "FPhone='" + conds.get(i).getValue() + "'";
                    break;
                case 4:
                    sql += "FIDNo='" + conds.get(i).getValue() + "'";
                    break;
                case 5:
                    sql += "FMeterID='" + conds.get(i).getValue() + "'";
                    break;
                case 6:
                    sql += "FArea='" + conds.get(i).getValue() + "'";
                    break;
                case 7:
                    sql += "FCardID='" + conds.get(i).getValue() + "'";
                    break;
                default:
                    break;
            }
        }
        sql += " order by FUserID";
        ResultSet rs = StdUtils.getSqlh().executeQuery(sql);
        try {
            while (rs.next()){
                wuList.add(new WS_UserInfo(rs.getInt("FUserID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wuList;
    }

    public static List<WS_UserInfo> getUsersMultiConditions(String sqlcond) {
        List<WS_UserInfo> wuList = new ArrayList<WS_UserInfo>();
        String sql = "select FUserID from TUser where ";
        sql += sqlcond;
        sql += " order by FUserID";
        ResultSet rs = StdUtils.getSqlh().executeQuery(sql);
        try {
            while (rs.next()){
                wuList.add(new WS_UserInfo(rs.getInt("FUserID")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wuList;
    }

}
