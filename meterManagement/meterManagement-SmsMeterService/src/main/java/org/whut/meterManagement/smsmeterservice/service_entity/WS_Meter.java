package org.whut.meterManagement.smsmeterservice.service_entity;

import org.whut.meterManagement.sqldatalib.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/22.
 */
/// <summary>
/// 表具信息
/// </summary>
public class WS_Meter {

    private String meterID;
    private String sim;
    private String icCID;
    private Date inputDate;
    private String status;
    private String key;

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public String getIcCID() {
        return icCID;
    }

    public void setIcCID(String icCID) {
        this.icCID = icCID;
    }

    public Date getInputDate() {
        return inputDate;
    }

    public void setInputDate(Date inputDate) {
        this.inputDate = inputDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    public WS_Meter() {
    }

    public WS_Meter(String mid, boolean M) {
        ResultSet rs;
        if (M) {
            rs = StdUtils.getSqlh().executeQuery("select * from TMeter where FMeterID='" + mid + "'");
        } else {
            rs = StdUtils.getSqlh().executeQuery("select * from TMeter where FUIM='" + mid + "'");
        }
        try {
            if (rs.next()) {
                meterID = rs.getString("FMeterID");
                sim = rs.getString("FUIM");
                icCID = rs.getString("FICCID");
                inputDate = rs.getDate("FInputDateTime");
                status = rs.getString("FStatus");
                key = rs.getString("FKey");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<WS_Meter> getMeters(Date bDt, Date eDt) {
        List<WS_Meter> wmList = new ArrayList<WS_Meter>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sql = "select FMeterID from TMeter where FInputDateTime>='"
                + dateFormat.format(bDt) + "' and FInputDateTime<='" + dateFormat.format(eDt) + "'";
        ResultSet rs = StdUtils.getSqlh().executeQuery(sql);
        try {
            while (rs.next()) {
                wmList.add(new WS_Meter(rs.getString("FMeterID"), true));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wmList;
    }

    /// <summary>
    /// 表具资料导入
    /// </summary>
    /// <param name="mts"></param>
    /// <returns></returns>
    public static String Import(List<WS_Meter> mts) {
        StringBuilder sb = new StringBuilder();
        int cntSucc = 0;
        int cntFail = 0;
        for (int i = 0; i < mts.size(); i++) {
            try {
                StdUtils.getSqlh().executeNonQuery("insert TMeter(FMeterID,FUIM,FICCID,FKey) Values('"
                        + mts.get(i).getMeterID() + "','" + mts.get(i).getSim() + "','" + mts.get(i).getIcCID() + "','" + mts.get(i).getKey() + "')");
                cntSucc++;
            } catch (Exception e) {
                sb.append(mts.get(i).getMeterID() + ":表号已存在或数据不合法\n");
                cntFail++;
            }
        }
        sb.append("成功导入表具资料：" + cntSucc + "行\n");
        if (cntFail > 0) {
            sb.append("失败：" + cntFail + "行\n");
        }
        return sb.toString();
    }

}
