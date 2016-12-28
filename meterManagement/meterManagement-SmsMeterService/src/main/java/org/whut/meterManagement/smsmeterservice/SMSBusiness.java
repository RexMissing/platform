package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.database.DB;
import org.whut.meterManagement.date.DateUtil;
import org.whut.meterManagement.smsmeterlib.enums.ValveCtrStyle;
import org.whut.meterManagement.smsmeterlib.frames.Recharge;
import org.whut.meterManagement.smsmeterlib.frames.SMC;
import org.whut.meterManagement.smsmeterlib.send.SendFrame;
import org.whut.meterManagement.smsmeterservice.entity.MeterFrameData;
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

    private String getNewSMID() {
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
            MeterPrice mtp = MeterPrice.getMeterPrice(saleStrategyID, sqlHelper);
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
            DB.closeResultSet(rs1);
            e.printStackTrace();
            sismsID.delete(0, sismsID.length()).append(e.getMessage());
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
    public boolean openMeter(int userID, String meterID, double updateMoney, double chargeMoney, int bzql, int szql, StringBuffer sismsID)
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

                sismsID.delete(0,sismsID.length()).append("用户编号错误，用户资料不存在");
                return false;
            }
            int strategyID = rs1.getInt("FSaleStrategyID");
            int cbr = rs1.getInt("FReadMeterDay");
            Date sdt = rs1.getTimestamp("FStageBeginDate");

            MeterPrice mtp = MeterPrice.getMeterPrice(strategyID, sqlHelper);
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
            DB.closeConn(rs);
            DB.closeConn(rs1);
            sismsID.append(e.getMessage());
            return false;
        }finally {
            DB.closeConn(rs);
            DB.closeConn(rs1);
        }
    }

    // 充值或冲正（20140701修改,生成帧，不发送短信，需要调用SendSmsCommand发送短信指令）
    // Money大于0时为充值，小于0时为冲正
    // <param name="UserID">用户编号</param>
    // <param name="Money">充值金额</param>
    // <param name="Sismsid">错误信息,指令唯一编号</param>
    public boolean charge(int userID, double money, StringBuffer sismsID)
    {
        //sismsID = "";
        boolean isUncharge = false;
        double t_money = money;
        if (money < 0)
        {
            isUncharge = true;
            money *= -1;
        }
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.查询用户购买单价
            int strategyID = Integer.valueOf(sqlHelper.executeScalar("select FSaleStrategyID from TUser where FUserID=" + userID).toString());
            //6.增加充值记录，并获取充值ID
            String sqlstr = "insert TRecharge(FUserID,FChargeMoney,FSaleStrategyID,FDate,FOperatorID) values("
                    + userID + "," + money + "," + strategyID + ",'" + new SimpleDateFormat("yyyy-MM-dd").format(new Date())
                    + "','" + operatorID + "')";
            int chargeID = sqlHelper.insertGetID(sqlstr);

            //7.生成短信指令
            String SMS = SMC.getChangeMoneyFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameIDEx(),t_money,mfd.getiTimeSpan());
            //8.更新数据库
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',10,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            if (isUncharge)
            {
                SQLList.add("update TUser set FSaleTotal=FSaleTotal-" + money + " where FUserID=" + userID);
                SQLList.add("insert into TSetMoney(FMeterID,FMoney,FMode,FSismsid,FChargeID) values('" + mfd.getMeterID()
                        + "'," + money + ",1,'" + mfd.getSismsID() + "'," + chargeID + ")");
                SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                        + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'金额冲正')");
            }
            else
            {
                SQLList.add("update TUser set FSaleTotal=FSaleTotal+" + money + " where FUserID=" + userID);
                SQLList.add("insert into TSetMoney(FMeterID,FMoney,FMode,FSismsid,FChargeID) values('" + mfd.getMeterID()
                        + "'," + money + ",0,'" + mfd.getSismsID() + "'," + chargeID + ")");
                SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                        + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'充值')");
            }
            sismsID.append(mfd.getSismsID());
            sqlHelper.executeWithTransaction(SQLList);
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 及时读表(20140701修改，生成帧并发送短信指令)
    // <param name="UserID">用户编号</param>
    // <param name="Sismsid"></param>
    public boolean readMeter(int userID, StringBuffer sismsID)
    {
        //Sismsid = "";
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            String SMS = SMC.getMeterDataFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),null,mfd.getiTimeSpan());
            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',5,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid) Values('" + mfd.getMeterID()
                    + "',5,'" + mfd.getSismsID() + "')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'读表')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'读表')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 定时读表(20140701修改,生成帧并发送短信指令)
    // <param name="UserID">用户编号</param>
    // <param name="SchDT">定时时间</param>
    // <param name="Sismsid"></param>
    public boolean readMeter(int userID, Date SchDT, StringBuffer sismsID)
    {
        //Sismsid = "";
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            String SMS = SMC.getMeterDataFrame(mfd.getMeterID(), mfd.getMeterKey(), mfd.getFrameID(), SchDT, mfd.getiTimeSpan());
            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',30,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid,FScheduleTime) Values('" + mfd.getMeterID()
                    + "',5,'" + mfd.getSismsID() + "','" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(SchDT) + "')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'定时读表')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'定时读表')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 更新表具单价（20140701修改,生成帧，不发送短信，需要调用SendSmsCommand发送短信指令）
    // <param name="UserID">用户编号</param>
    // <param name="SaleStrategyID">单价策略ID</param>
    // <param name="SchDT">定时时间</param>
    // <param name="IsAtTime">是否定时</param>
    // <param name="Er">错误信息</param>
    public boolean setPrice(int userID, int saleStrategyID, Date SchDT, boolean isAtTime, StringBuffer sismsID)
    {
        //Sismsid = "";
        try
        {
            //1.查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //2.查询现有单价信息
            ResultSet rs = sqlHelper.executeQuery("select * from TUser where FUserID=" + userID);
            int oldStrategyID = rs.getInt("FSaleStrategyID");
            boolean oldStage = rs.getBoolean("FIsStagePrice");
            if (saleStrategyID == oldStrategyID)
            {
                //单价相同，而且未变更阶梯价格是否开启状态
                sismsID.append("变更单价与现有单价相同");
                return false;
            }

            //3.查询单价Price
            MeterPrice mp = MeterPrice.getMeterPrice(saleStrategyID,sqlHelper);

            //4.更改处理
            if (isAtTime)
            {
                //long end = SchDT.getTime();
                //long begin = DateUtil.createDate("2000-1-1 00:00:00").getTime();
                //long timeSpan = end - begin;
                //定时更改，生成短信指令
                String SMS = SMC.getChangePriceFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),
                        mp.getPrice(),mp.getPrice1(),mp.getPrice2(),mp.getPrice3(), mp.getAmount1(),mp.getAmount2(),mp.getAmount3(),
                        SchDT, (byte)mp.getCycleLength(),SchDT,mfd.getiTimeSpan());
                List<String> SQLList = new ArrayList<String>();
                SQLList.add("update TUser set FNextStrategyID=" + saleStrategyID + " where FUserID="
                        + userID);
                SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                        + "',31,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
                SQLList.add("insert into TSetPrice(FMeterID,FStrategyID,FSismsid,FIsAtTime,FAtTime) Values('" + mfd.getMeterID()
                        + "'," + mp.getStrategyID() + ",'" + mfd.getSismsID() + "',1,'"
                        + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(SchDT) + "')");
                SQLList.add("update TUser set FStageBeginDate='" + new SimpleDateFormat("yyyy/MM/dd").format(SchDT)+" 0:00:00"
                        + "' where FUserID=" + userID);
                SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                        + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'定时更新单价')");
                //SQLList.Add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.Sismsid
                //        + "','" + mfd.MeterSIM + "','" + SMS + "'," + UserID.ToString() + ",'定时更新单价')");
                sqlHelper.executeWithTransaction(SQLList);
            }
            else
            {
                //直接更改，生成短信指令
                String SMS = SMC.getChangePriceFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),
                        mp.getPrice(),mp.getPrice1(),mp.getPrice2(),mp.getPrice3(), mp.getAmount1(),mp.getAmount2(),mp.getAmount3(),
                        SchDT, (byte)mp.getCycleLength(),null,mfd.getiTimeSpan());
                List<String> SQLList = new ArrayList<String>();
                SQLList.add("update TUser set FNextStrategyID=" + saleStrategyID + " where FUserID="
                        + userID);
                SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                        + "',8,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
                SQLList.add("insert into TSetPrice(FMeterID,FStrategyID,FSismsid) Values('" + mfd.getMeterID()
                        + "'," + mp.getStrategyID() + ",'" + mfd.getSismsID() + "')");
                SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                        + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'更新单价')");
                //SQLList.Add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.Sismsid
                //        + "','" + mfd.MeterSIM + "','" + SMS + "'," + UserID.ToString() + ",'更新单价')");
                sqlHelper.executeWithTransaction(SQLList);
            }
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 阀门控制(20140701修改,生成帧并发送短信指令)
    // <param name="UserID">用户编号</param>
    // <param name="Mode">1：关闭阀门; 0: 开启阀门; -1：强制关闭阀门</param>
    // <param name="Sismsid"></param>
    public boolean valveControl(int userID, int mode, StringBuffer sismsID)
    {
        //Sismsid = "";
        if ((mode > 1) || (mode < -1))
        {
            return false;
        }
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            ValveCtrStyle vcs = null;
            byte funCode = 0;
            String desc = "";
            switch (mode)
            {
                case -1:
                    vcs = ValveCtrStyle.强制关闭;
                    funCode = 12;
                    desc = "强制关闭阀门";
                    break;
                case 0:
                    vcs = ValveCtrStyle.允许开启;
                    funCode = 1;
                    desc = "允许开启阀门";
                    break;
                case 1:
                    vcs = ValveCtrStyle.临时关闭;
                    funCode = 3;
                    desc = "临时关闭阀门";
                    break;
                default:
                    return false;
            }
            String SMS = SMC.getValveControlFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),vcs,null,mfd.getiTimeSpan());
            //6.保存数据库
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "'," + funCode + ",'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid) Values('" + mfd.getMeterID()
                    + "'," + funCode + ",'" + mfd.getSismsID() + "')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'" + desc + "')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'" + desc + "')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 定时关闭阀门(20140701修改,生成帧并发送短信指令)
    // <param name="UserID"></param>
    // <param name="SchDT"></param>
    // <param name="Sismsid"></param>
    public boolean closeValveAtTime(int userID, Date SchDT, StringBuffer sismsID)
    {
        //Sismsid = "";
        try
        {
            //1.查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            ValveCtrStyle vcs = ValveCtrStyle.定时关闭;
            //2.生成短信指令
            String SMS = SMC.getValveControlFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),vcs,SchDT,mfd.getiTimeSpan());;
            //3.保存数据库
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',32,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid,FScheduleTime) Values('" + mfd.getMeterID()
                    + "',32,'" + mfd.getSismsID() + "','" + new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(SchDT) + "')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'定时关闭阀门')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'定时关闭阀门')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 要求校对表具时间
    // <param name="UserID">用户编号</param>
    // <param name="Er"></param>
    // <param name="Sismsid"></param>
    public boolean checkMeterTime(int userID, StringBuffer er)
    {
        //Er = "";
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            String SMS = SMC.getCheckMeterTimeFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID());

            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',41,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid) Values('" + mfd.getMeterID()
                    + "',41,'" + mfd.getSismsID() + "')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'请求表具对时')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'请求表具对时')");
            sqlHelper.executeWithTransaction(SQLList);
            return true;
        }
        catch (Exception ex)
        {
            er.append(ex.getMessage());
            return false;
        }
    }

    // 更改表具透支方式（20140701修改,生成帧，不发送短信，需要调用SendSmsCommand发送短信指令）
    // <param name="UserID">用户编号</param>
    // <param name="Style">透支方式参数。0:透支10元 1:透支3天 2:无限透支</param>
    // <param name="Sismsid"></param>
    public boolean overdraftStyle(int userID, int style, StringBuffer sismsiID)
    {
        //Sismsid = "";
        if ((style < 0) || (style > 2))
        {
            return false;
        }
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            String SMS = SMC.getChangeOverdraftFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),style,mfd.getiTimeSpan());
            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',33,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid) Values('" + mfd.getMeterID()
                    + "',33,'" + mfd.getSismsID() + "')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'更改透支方式')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'更改透支方式')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsiID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsiID.delete(0,sismsiID.length()).append(ex.getMessage());
            return false;
        }
    }

    /// 设置表具周期量，包括本周期量和上周期量
    /// <param name="UserID"></param>
    /// <param name="Sum"></param>
    /// <param name="cur"></param>
    /// <param name="pre"></param>
    /// <param name="mode"></param>
    /// <param name="Er"></param>
    public boolean meterUseSet(int userID, int sum, int cur, int pre, byte mode, StringBuffer er)
    {
        //Er = "";
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            String SMS = SMC.getMeterUseSetFrame(mfd.getMeterID(), mfd.getMeterKey(), mfd.getFrameID(), sum, cur, pre, mode);

            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',6,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid) Values('" + mfd.getMeterID()
                    + "',6,'" + mfd.getSismsID() + "')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'修改表具用量')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'修改表具用量')");
            sqlHelper.executeWithTransaction(SQLList);
            return true;
        }
        catch (Exception ex)
        {
            er.append(ex.getMessage());
            return false;
        }
    }

    // 更改表具每月抄表日
    // <param name="UserID"></param>
    // <param name="CBR"></param>
    // <param name="Sismsid"></param>
    public boolean meterSetCBR(int userID, int CBR, StringBuffer sismsID)
    {
        if (CBR < 1 || CBR > 28)
        {
            sismsID.append( "抄表日只能取1~28范围的数字");
            return false;
        }
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);

            //5.生成短信指令
            String SMS = SMC.getMeterSetCBRFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),mfd.getiTimeSpan(),CBR);

            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("update TUser set FReadMeterDay=" + CBR + " where FUserID=" + userID);
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',37,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid) Values('" + mfd.getMeterID()
                    + "',37,'" + mfd.getSismsID() + "')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'更改抄表日')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'更改抄表日')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 读取历史阶梯周期使用量（20140701增加,生成帧并发送短信指令)
    public boolean readCycleInfo(int userID, StringBuffer sismsID)
    {
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            String SMS = SMC.getReadCycleInfoFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),mfd.getiTimeSpan());

            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',41,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid) Values('" + mfd.getMeterID()
                    + "',41,'" + mfd.getSismsID() + "')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'读取历史阶梯周期量')");
            SQLList.add("insert into TSchedule(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'读取历史阶梯周期量')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    /// 设置每月抄表日（20140701增加，生成帧，需要调用SendSmsCommand发送短信指令)
    /// <param name="UserID"></param>
    /// <param name="CBR"></param>
    /// <param name="Sismsid"></param>
    public boolean setReadMeterDay(int userID, int CBR, StringBuffer sismsID)
    {
        //Sismsid = "";
        try
        {
            //查询表具相关信息
            MeterFrameData mfd = MeterFrameData.getMeterFrameData(userID,sqlHelper);
            //5.生成短信指令
            String SMS = SMC.getMeterSetCBRFrame(mfd.getMeterID(),mfd.getMeterKey(),mfd.getFrameID(),mfd.getiTimeSpan(),CBR);
            //6.更新数据
            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) Values('" + mfd.getMeterID()
                    + "',37,'" + mfd.getSismsID() + "'," + mfd.getFrameID() + ")");
            SQLList.add("Insert into TOtherCommand(FMeterID,FFuncCode,FSismsid,FMemo) Values('" + mfd.getMeterID()
                    + "',37,'" + mfd.getSismsID() + "','" + CBR + "')");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('" + mfd.getSismsID()
                    + "','" + mfd.getMeterSIM() + "','" + SMS + "'," + userID + ",'设置抄表日')");
            sqlHelper.executeWithTransaction(SQLList);
            sismsID.append(mfd.getSismsID());
            return true;
        }
        catch (Exception ex)
        {
            sismsID.delete(0,sismsID.length()).append(ex.getMessage());
            return false;
        }
    }

    // 将已发送的指令写入IC卡
    // <param name="sis"></param>
    // <param name="cid"></param>
    // <param name="CmsStr"></param>
    public boolean reWriteIC(String sis, String cid, StringBuffer CmsStr)
    {
        ResultSet rs = null;
        try {
            rs = sqlHelper.executeQuery("select * from TAllCommand where FSismsid='" + sis + "'");
            if (!rs.next()) {
                CmsStr.append("指令不存在");
                return false;
            }
            int UID = rs.getInt("FUserID");
            CmsStr.append(rs.getString("FCmdStr"));
            Object o = sqlHelper.executeScalar("select count(*) from TUser where FUserID=" + UID
                    + " and FCardID='" + cid + "'");
            int iExist = Integer.parseInt(String.valueOf(o));
            if (iExist == 0) {
                CmsStr.delete(0,CmsStr.length()).append("卡号不符，不能重新写入指令");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            DB.closeConn(rs);
        }
        return true;
    }

    // 服务号码变更
    // <param name="MeterID"></param>
    // <param name="ServerNo"></param>
    // <param name="Sismsid"></param>
    public boolean setServerNo(String MeterID, String ServerNo, StringBuffer sb)
    {
        String Sismsid = "";
        ResultSet rs = null;
        try {
            rs = sqlHelper.executeQuery("select * from TMeter where FMeterID='" + MeterID + "'");
            if (!rs.next())
            {
                sb.append("表具资料不存在");
                return false;
            }
            String key = rs.getString("FKey");
            String phone = rs.getString("FUIM");
            //取得新的Sismsid
            Sismsid = sqlHelper.executeScalar("select FParamValue from TConfig where FParamName='SM_ID'").toString();
            Sismsid = String.valueOf(Integer.parseInt(Sismsid) + 1);
            sb.append(Sismsid);
            sqlHelper.executeNonQuery("update TConfig set FParamValue='" + Sismsid + "' where FParamName='SM_ID'");
            //生成短信命令
            String SMS = SMC.getSetServerNoFrame(MeterID,key,0,0,1,ServerNo);

            List<String> SQLList = new ArrayList<String>();
            SQLList.add("insert into TAllSend(FMeterID,FFuncCode,FSismsid,FFrameID) values('" + MeterID
                    + "',13,'" + Sismsid + "',0)");
            SQLList.add("insert into TAllCommand(FSismsid,FDestAddr,FCmdStr,FUserID,FDes) Values('"
                    + Sismsid + "','" + phone + "','" + SMS + "',0,'设置服务号码')");
            sqlHelper.executeWithTransaction(SQLList);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //指令后续处理
    // <summary>
    // 发送短信指令
    // <param name="Sismsid"></param>
    public boolean sendSmsCommand(String Sismsid)
    {
        String strSQL = "insert TSchedule(FDestAddr,FCmdStr,FDateTime,FUserID,FDes,FSismsid) "
                + "select FDestAddr,FCmdStr,FDateTime,FUserID,FDes,FSismsid from TAllCommand where "
                + "FSismsid='" + Sismsid + "'";
        int n = sqlHelper.executeNonQuery(strSQL);
        if (n > 0)
            return true;
        else
            return false;
    }

    // 获取指令字符串（用于写IC卡）
    // <param name="Sismsid"></param>
    public String getCommandStr(String Sismsid)
    {
        Object obj = sqlHelper.executeScalar("select FCmdStr from TAllCommand where FSismsid='" + Sismsid + "'");
        if (obj != null)
        {
            return obj.toString();
        }
        else
        {
            return "";
        }
    }
}
