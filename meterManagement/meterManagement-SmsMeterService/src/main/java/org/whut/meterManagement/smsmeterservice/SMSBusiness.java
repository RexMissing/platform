package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.smsmeterlib.frames.SMC;
import org.whut.meterManagement.smsmeterservice.entity.MeterPrice;
import org.whut.meterManagement.sqldatalib.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhang_minzhong on 2016/12/21.
 */

/// <summary>
/// 业务类，原BusiServer库转移到此处
/// </summary>
public class SMSBusiness {
    private SqlHelper sqlHelper;
    private String operatorID;

    public SqlHelper getSqlHelper() {
        return sqlHelper;
    }

    public void setSqlHelper(SqlHelper sqlHelper) {
        this.sqlHelper = sqlHelper;
    }

    public String getOperatorID() {
        return operatorID;
    }

    public void setOperatorID(String operatorID) {
        this.operatorID = operatorID;
    }

    public SMSBusiness(String operatorID, SqlHelper sqlHelper) {
        this.operatorID = operatorID;
        this.sqlHelper = sqlHelper;
    }

    private String getNewSMID()
    {
        String sismsid = "";
        Object o = sqlHelper.executeScalar("select FParamValue from TConfig where FParamName='SM_ID'").toString();
        if (o == null)
        {
            sismsid = "1";
            sqlHelper.executeNonQuery("insert TConfig(FParamName,FParamValue) values('SM_ID','" + sismsid + "')");
        }
        else
        {
            sismsid = String.valueOf(Integer.parseInt(o.toString()) + 1);
            sqlHelper.executeNonQuery("update TConfig set FParamValue='" + sismsid + "' where FParamName='SM_ID'");
        }
        return sismsid;
    }

    /*
    前台业务处理功能
     */
    /// <summary>
    /// 表具开通，用户首次开通使用（20140701修改,生成帧，不发送短信，需要调用SendSmsCommand发送短信指令）
    /// </summary>
    /// <param name="UserID">用户编号</param>
    /// <param name="MeterID">表具编号</param>
    /// <param name="SaleStrategyID">单价策略</param>
    /// <param name="Money">首次充值金额</param>
    /// <param name="SDT">阶梯起始日期</param>
    /// <param name="CBR">抄表日</param>
    /// <param name="Sismsid">命令序号</param>
    /// <returns></returns>
    public boolean openUser(int userID,String meterID,int saleStrategyID,double money,Timestamp sdt,int cbr,StringBuffer sismsID){
        //1.查询开户单价
        MeterPrice mtp = new MeterPrice();
        ResultSet rs = sqlHelper.executeQuery("select * from TSaleStrategy where FStrategyID="+String.valueOf(saleStrategyID));
        try {
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

            //2.查询FrameID
            int frameID = Integer.parseInt(sqlHelper.executeScalar("select count(*) from TAllSend where FMeterID='"
                    + meterID + "'").toString());
            frameID %= 256;
            //3.获得新的Sismsid
            sismsID.append(getNewSMID());
            //4.查询表具密钥及短信号码
            ResultSet rs1 = sqlHelper.executeQuery("select * from TMeter where FMeterID='" + meterID + "'");
            String meterKey = "";
            String destAddr = "";
            rs.next();
            meterKey = rs.getString("Fkey");
            destAddr = rs.getString("FUIM");
            //5.组成短信指令帧
            String SMS = SMC.getMeterOpenFrame(meterID,meterKey,(byte)0x23,
                    money,mtp.getPrice(),mtp.getPrice1(),mtp.getPrice2(),mtp.getPrice3()
                    ,mtp.getAmount1(),mtp.getAmount2(),mtp.getAmount3(),
                    meterKey,sdt,(byte)mtp.getCycleLength(),(byte)cbr,0,0);
            //6.更新数据库，保存业务数据
            String sqlStr = "insert TRecharge(FUserID,FChargeMoney,FSaleStrategyID,FDate,FOperatorID) values("
                    + String.valueOf(userID) + "," + String.valueOf(money) + "," + String.valueOf(saleStrategyID)
                    + ",'" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                    + "','" + operatorID + "')";
            int chargeID = sqlHelper.insertGetID(sqlStr);
            List<String> sqlList = new ArrayList<String>();
            sqlList.add("insert into TSetMoney(FMeterID,FMoney,FMode,FSismsid,FChargeID) values('" + meterID
                    + "'," + String.valueOf(money) + ",0,'" + sismsID + "'," + String.valueOf(chargeID) + ")");
            sqlList.add("update TMeter set FStatus='U' where FMeterID='" + meterID + "'");
            sqlList.add("update TUser set FMeterID='" + meterID + "',FSaleStrategyID="
                    + String.valueOf(saleStrategyID) + ",FOpenDT='" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                    + "',FOpenOp='" + operatorID + "',FSaleTotal=" + String.valueOf(money) + ",FStageBeginDate='"
                    + new SimpleDateFormat("yyyy-MM-dd").format(sdt) + "',FReadMeterDay=" + String.valueOf(cbr)
                    + " where FUserID=" + String.valueOf(userID));
            sqlList.add("insert into TMeterStatus(FMeterID,FStrategyID) values('" + meterID
                    + "'," + String.valueOf(saleStrategyID) + ")");
            sqlList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) values('" + meterID + "',35,'"
                    + sismsID + "'," + String.valueOf(frameID) + ")");
            sqlList.add("insert into TSetPrice(FMeterID,FStrategyID,FSismsid) Values('" + meterID + "',"
                    + String.valueOf(mtp.getStrategyID()) + ",'" + sismsID + "')");
            sqlList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('"
                    + sismsID + "','" + destAddr + "','" + SMS + "'," + String.valueOf(userID) + ",'开通用户')");
            sqlHelper.executeWithTransaction(sqlList);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
