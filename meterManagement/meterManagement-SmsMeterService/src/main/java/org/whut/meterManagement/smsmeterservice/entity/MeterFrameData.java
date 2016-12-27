package org.whut.meterManagement.smsmeterservice.entity;

import org.whut.meterManagement.database.DB;
import org.whut.meterManagement.sqldatalib.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhang_minzhong on 2016/12/27.
 */
// 表具相关数据类，包括表具通讯号码，通讯密钥，以及组帧ID等
public class MeterFrameData {
    private String meterID;
    private String meterKey;
    private String meterSIM;
    private byte frameID;
    private int frameIDEx;
    private String sismsID;
    private int iTimeSpan;

    public byte getFrameID() {
        return frameID;
    }

    public void setFrameID(byte frameID) {
        this.frameID = frameID;
    }

    public int getFrameIDEx() {
        return frameIDEx;
    }

    public void setFrameIDEx(int frameIDEx) {
        this.frameIDEx = frameIDEx;
    }

    public String getSismsID() {
        return sismsID;
    }

    public void setSismsID(String sismsID) {
        this.sismsID = sismsID;
    }

    public int getiTimeSpan() {
        return iTimeSpan;
    }

    public void setiTimeSpan(int iTimeSpan) {
        this.iTimeSpan = iTimeSpan;
    }

    public String getMeterID() {
        return meterID;
    }

    public void setMeterID(String meterID) {
        this.meterID = meterID;
    }

    public String getMeterKey() {
        return meterKey;
    }

    public void setMeterKey(String meterKey) {
        this.meterKey = meterKey;
    }

    public String getMeterSIM() {
        return meterSIM;
    }

    public void setMeterSIM(String meterSIM) {
        this.meterSIM = meterSIM;
    }

    private static String GetNewSMID(SqlHelper sh)
    {
        String sismsid = "";
        Object o = sh.executeScalar("select FParamValue from TConfig where FParamName='SM_ID'");
        if (o == null)
        {
            sismsid = "1";
            sh.executeNonQuery("insert TConfig(FParamName,FParamValue) values('SM_ID','" + sismsid + "')");
        }
        else
        {
            sismsid = String.valueOf(Integer.parseInt(o.toString()) + 1);//int.Parse(o.toString()) + 1).ToString();
            sh.executeNonQuery("update TConfig set FParamValue='" + sismsid + "' where FParamName='SM_ID'");
        }
        return sismsid;
    }

    public static MeterFrameData getMeterFrameData(int userID, SqlHelper sqlh) {
        //sh = sqlh;
        MeterFrameData meterFrameData = new MeterFrameData();
        //1.查询表具编号
        String meterID = sqlh.executeScalar("select FMeterID from TUser where FUserID=" + userID).toString();
        meterFrameData.setMeterID(meterID);
        //2.查询FrameID
        int frameIDEx = Integer.parseInt(sqlh.executeScalar("select count(*) from TAllSend where FMeterID='" + meterID + "'").toString());
        meterFrameData.setFrameIDEx(frameIDEx);
        byte frameID = (byte)(frameIDEx % 256);
        meterFrameData.setFrameID(frameID);
        //3.取得新的Sismsid
        String sismsID = GetNewSMID(sqlh);
        meterFrameData.setSismsID(sismsID);
        //4.查询表具密钥及短信号码
        ResultSet rs = sqlh.executeQuery("select * from TMeter where FMeterID='" + meterID + "'");
        try{
            String meterKey = rs.getString("FKey");
            meterFrameData.setMeterKey(meterKey);
            String meterSIM = rs.getString("FUIM");
            meterFrameData.setMeterSIM(meterSIM);
            int iTimeSpan = rs.getInt("FTimeUpdate");
            meterFrameData.setiTimeSpan(iTimeSpan);
        } catch (SQLException e) {
            DB.closeConn(rs);
            e.printStackTrace();
        } finally {
            DB.closeConn(rs);
        }
        //5.如果时间差小于半小时（1800秒）则返回0，不进行时间调整
        if (Math.abs(meterFrameData.getiTimeSpan()) <= 1800)
        {
            meterFrameData.setiTimeSpan(0);
        }
        else
        {
            sqlh.executeNonQuery("update TMeter set FTimeUpdate=0 where FMeterID='" + meterID + "'");
        }
        return meterFrameData;
    }

    /*public void nextFrame(SqlHelper sqlh)
    {
        sismsID = String.valueOf(Integer.parseInt(sismsID)+1);//int.Parse(Sismsid) + 1).ToString();
        sqlh.executeNonQuery("update TConfig set FParamValue='" + sismsID + "' where FParamName='SM_ID'");
        frameIDEx++;
        frameID = (byte)(frameIDEx % 256);
    }*/
}
