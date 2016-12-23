package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.database.DB;
import org.whut.meterManagement.smsmeterlib.frames.SMC;
import org.whut.meterManagement.smsmeterservice.entity.MeterPrice;
import org.whut.meterManagement.sqldatalib.SqlHelper;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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

    private MeterPrice getMeterPrice(int saleStrategyID,SqlHelper sqlh){
        ResultSet rs=null;
        MeterPrice mtp = new MeterPrice();
        try {
            rs = sqlh.executeQuery("select * from TSaleStrategy where FStrategyID=" + saleStrategyID);
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
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DB.closeConn(rs);
        }
        return mtp;
    }
    /*
    前台业务处理功能
     */
    // 表具开通，用户首次开通使用（20140701修改,生成帧，不发送短信，需要调用SendSmsCommand发送短信指令）
    // <param name="UserID">用户编号</param>
    // <param name="MeterID">表具编号</param>
    // <param name="SaleStrategyID">单价策略</param>
    // <param name="Money">首次充值金额</param>
    // <param name="SDT">阶梯起始日期</param>
    // <param name="CBR">抄表日</param>
    // <param name="Sismsid">命令序号</param>
    public boolean openUser(int userID,String meterID,int saleStrategyID,double money,Timestamp sdt,int cbr,StringBuffer sismsID){
        //1.查询开户单价
        //ResultSet rs=null;
        ResultSet rs1 = null;
        try {
            MeterPrice mtp = getMeterPrice(saleStrategyID,sqlHelper);
            //2.查询FrameID
            int frameID = Integer.parseInt(sqlHelper.executeScalar("select count(*) from TAllSend where FMeterID='"
                    + meterID + "'").toString());
            frameID %= 256;
            //3.获得新的Sismsid
            sismsID.append(getNewSMID());
            //4.查询表具密钥及短信号码
            rs1 = sqlHelper.executeQuery("select * from TMeter where FMeterID='" + meterID + "'");
            String meterKey = "";
            String destAddr = "";
            rs1.next();
            meterKey = rs1.getString("Fkey");
            destAddr = rs1.getString("FUIM");
            //5.组成短信指令帧
            String SMS = SMC.getMeterOpenFrame(meterID,meterKey,(byte)0x23,
                    money,mtp.getPrice(),mtp.getPrice1(),mtp.getPrice2(),mtp.getPrice3()
                    ,mtp.getAmount1(),mtp.getAmount2(),mtp.getAmount3(),
                    meterKey,sdt,(byte)mtp.getCycleLength(),(byte)cbr,0,0);
            //6.更新数据库，保存业务数据
            String sqlStr = "insert TRecharge(FUserID,FChargeMoney,FSaleStrategyID,FDate,FOperatorID) values("
                    + userID + "," + money + "," + saleStrategyID
                    + ",'" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                    + "','" + operatorID + "')";
            int chargeID = sqlHelper.insertGetID(sqlStr);
            List<String> sqlList = new ArrayList<String>();
            sqlList.add("insert into TSetMoney(FMeterID,FMoney,FMode,FSismsid,FChargeID) values('" + meterID
                    + "'," + money + ",0,'" + sismsID + "'," + chargeID + ")");
            sqlList.add("update TMeter set FStatus='U' where FMeterID='" + meterID + "'");
            sqlList.add("update TUser set FMeterID='" + meterID + "',FSaleStrategyID="
                    + saleStrategyID + ",FOpenDT='" + new SimpleDateFormat("yyyy/MM/dd").format(new Date())
                    + "',FOpenOp='" + operatorID + "',FSaleTotal=" + money + ",FStageBeginDate='"
                    + new SimpleDateFormat("yyyy-MM-dd").format(sdt) + "',FReadMeterDay=" + cbr
                    + " where FUserID=" + userID);
            sqlList.add("insert into TMeterStatus(FMeterID,FStrategyID) values('" + meterID
                    + "'," + saleStrategyID + ")");
            sqlList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) values('" + meterID + "',35,'"
                    + sismsID + "'," + frameID + ")");
            sqlList.add("insert into TSetPrice(FMeterID,FStrategyID,FSismsid) Values('" + meterID + "',"
                    + mtp.getStrategyID() + ",'" + sismsID + "')");
            sqlList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('"
                    + sismsID + "','" + destAddr + "','" + SMS + "'," + userID + ",'开通用户')");
            sqlHelper.executeWithTransaction(sqlList);
        } catch (SQLException e) {
            e.printStackTrace();
            sismsID.delete(0, sismsID.length() - 1).append(e.getMessage());
            return false;
        } finally {
            //DB.closeConn(rs);
            DB.closeResultSet(rs1);
        }
        return true;
    }

    // 表具开通（用户维修更换表）
    // <param name="UserID"></param>
    // <param name="MeterID"></param>
    // <param name="UpdateMoney">结算金额</param>
    // <param name="ChargeMoney">充值金额</param>
    // <param name="BZQL"></param>
    // <param name="SZQL"></param>
    // <param name="Sismsid"></param>
    public boolean OpenMeter(int userID, String meterID, double updateMoney, double chargeMoney, int bzql, int szql, StringBuffer sismsID)
    {
       // Sismsid = "";
        ResultSet rs = null;
        ResultSet rs1 = null;
        try
        {
            //1.查询原来表号
            String oldMeterID = sqlHelper.executeScalar("select FMeterID from TUser where FUserID=" + userID).toString();
            //2.查询FrameID
            byte frameID = 0;
            //3.取得新的Sismsid
            sismsID.append(getNewSMID());
            //4.查询表具密钥及短信号码
            rs = sqlHelper.executeQuery("select * from TMeter where FMeterID='" + meterID + "'");
            rs.next();
            String meterKey = rs.getString("FKey");
            String meterSIM = rs.getString("FUIM");

            //查询用户信息，获取用户相关资料
            rs1 = sqlHelper.executeQuery("select * from TUser where FUserID=" + userID);
            if (!rs1.next())
            {

                sismsID.delete(0,sismsID.length()-1).append("用户编号错误，用户资料不存在");
                return false;
            }
            int strategyID = rs1.getInt("FSaleStrategyID");
            int cbr = rs1.getInt("FReadMeterDay");
            Date sdt = rs1.getTimestamp("FStageBeginDate");

            MeterPrice mtp = getMeterPrice(strategyID,sqlHelper);
            //生成最新的阶梯周期起始日期
            Date now = new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(sdt);
            c.add(Calendar.MONTH,mtp.getCycleLength());
            sdt = c.getTime();
            while(now.after(sdt)){
                c.add(Calendar.MONTH,mtp.getCycleLength());
                sdt = c.getTime();
            }

            //组成短信命令帧
            String SMS = SMC.getMeterOpenFrame(meterID, meterKey, frameID, updateMoney + chargeMoney,
                    mtp.getPrice(), mtp.getPrice1(), mtp.getPrice2(), mtp.getPrice3(), mtp.getAmount1(), mtp.getAmount2(), mtp.getAmount3()
                    , meterKey, sdt,(byte)mtp.getCycleLength(),(byte)cbr, bzql,szql);


            //更新数据库
            int chargeID = 0;
            if (chargeMoney > 0)
            {
                String sqlstr = "insert TRecharge(FUserID,FChargeMoney,FSaleStrategyID,FDate,FOperatorID) values("
                        + userID + "," + chargeMoney + "," + strategyID + ",'"
                        + new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + "','" + operatorID + "')";
                chargeID = sqlHelper.insertGetID(sqlstr);
            }
            List<String> sqlList = new ArrayList<String>();
            sqlList.add("insert into TSetMoney(FMeterID,FMoney,FMode,FSismsid,FChargeID) values('" + meterID
                    + "'," + (chargeMoney + updateMoney) + ",0,'" + sismsID + "'," + chargeID + ")");
            sqlList.add("update TMeter set FStatus='U' where FMeterID='" + meterID + "'");
            sqlList.add("update TMeter set FStatus='F' where FMeterID='" + oldMeterID + "'");
            sqlList.add("update TUser set FMeterID='" + meterID + "',FSaleTotal=" + chargeMoney
                    + ",FStageBeginDate='" + new SimpleDateFormat("yyyy-MM-dd").format(sdt) + "' where FUserID=" + userID);
            sqlList.add("insert into TMeterStatus(FMeterID,FStrategyID) values('" + meterID
                    + "'," + strategyID + ")");
            sqlList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) values('" + meterID
                    + "',35,'" + sismsID + "'," + frameID + ")");
            sqlList.add("insert into TSetPrice(FMeterID,FStrategyID,FSismsid) Values('" + meterID
                    + "'," + mtp.getStrategyID() + ",'" + sismsID + "')");
            sqlList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('"
                    + sismsID + "','" + meterSIM + "','" + SMS + "'," + userID + ",'维修表具开通')");

            sqlHelper.executeWithTransaction(sqlList);
            return true;
        }
        catch (Exception e)
        {
            sismsID.append(e.getMessage());

            return false;
        }finally {
            DB.closeConn(rs);
            DB.closeConn(rs1);
        }
    }


}
