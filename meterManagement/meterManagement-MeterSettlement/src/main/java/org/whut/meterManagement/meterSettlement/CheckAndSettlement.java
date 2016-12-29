package org.whut.meterManagement.meterSettlement;

import org.whut.meterManagement.gasMeterModal.DynamicRecord;
import org.whut.meterManagement.gasMeterModal.GasMeterRun;
import org.whut.meterManagement.gasMeterModal.MeterStatModal;
import org.whut.meterManagement.gasMeterModal.PriceModal;
import org.whut.meterManagement.sqldatalib.sqlhelper.SqlHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/27.
 */
public class CheckAndSettlement {
    private SqlHelper sqlhelper;

    public CheckAndSettlement(SqlHelper s) {
        sqlhelper = s;
    }

    public SettleResult execute(String meterID, int meterRead, double remainMoney, int bzql, int szql, Date meterTime, StringBuffer sb, Date receTime) {
        SettleResult sr = new SettleResult(false);
        MeterStatModal oldMSM = new MeterStatModal();

        ResultSet rs = sqlhelper.executeQuery("select * from TMeterStatus where FMeterID='" + meterID + "'");
        try {
            if (!rs.next()) {
                sr.setDescription("获取表具上次状态数据失败！");
                return sr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        oldMSM.MeterRead = (int) rs.Rows[0]["FMeterRead"];
//        oldMSM.Money = (double) rs.Rows[0]["FResidual"];
//        oldMSM.MeterTime = (Timestamp) rs.Rows[0]["FDateTime"];
        try {
            oldMSM.setMeterRead(rs.getInt("FMeterRead"));
            oldMSM.setMoney(rs.getDouble("FResidual"));
            oldMSM.setMeterTime(rs.getDate("FDateTime"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rs = sqlhelper.executeQuery("select A.FStageBeginDate,B.* from TUser A left join TSaleStrategy B on A.FSaleStrategyID=B.FStrategyID where A.FMeterID='" + meterID + "'");
        try {
            if (!rs.next()) {
                sr.setDescription("获取用户单价数据失败！");
                return sr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PriceModal priceModal = new PriceModal();
        try {
            priceModal.setID(rs.getInt("FStrategyID"));
            priceModal.setP0(rs.getDouble("FPrice"));
            priceModal.setP1(rs.getDouble("FStagePrice1"));
            priceModal.setP2(rs.getDouble("FStagePrice2"));
            priceModal.setP3(rs.getDouble("FStagePrice3"));
            priceModal.setA1(rs.getInt("FBeginAmount1"));
            priceModal.setA2(rs.getInt("FBeginAmount2"));
            priceModal.setA3(rs.getInt("FBeginAmount3"));
            priceModal.setStartDate(rs.getTimestamp("FStageBeginDate"));
            priceModal.setCycleLen(rs.getInt("FCycleLength"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        priceModal.sort();
        //查询是否发生金额变更
        double umoney = 0.00d;
        if (!sb.toString().equals("")) {
            rs = sqlhelper.executeQuery("select * from TSetMoney where FSismsid='" + sb.toString() + "'");
            try {
                if (rs.getRow() > 0) {
//                    int mode = Integer.parseInt(rs.Rows[0]["FMode"].ToString());
//                    umoney = (double) rs.Rows[0]["FMoney"];
                    rs.next();
                    int mode = Integer.parseInt(rs.getObject("FMode").toString());
                    umoney = rs.getDouble("FMoney");
                    if (mode == 1) {
                        umoney = umoney * -1;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        DynamicRecord DR = new DynamicRecord();
        //进行表具模拟运算
        MeterStatModal newMSM = GasMeterRun.standardRun(oldMSM, priceModal, meterRead, bzql, szql, umoney, receTime, DR);
        if (newMSM == null) {
            //运算失败，数据不足
            sr.setDescription("数据不足，运算失败，需要获取表具历史阶梯周期量");
            return sr;
        }
        //根据运算结果更新数据库
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sql = "update TMeterStatus set FMeterRead=" + newMSM.getMeterRead() + ",FResidual="
                + newMSM.getMoney() + ",FDateTime='" + dateFormat.format(new Date()) + "',FMeterTime='" + meterTime
                + "',FCycleBeginRead=" + newMSM.getCycleBeginRead() + " where FMeterID='" + meterID + "'";
        sqlhelper.executeNonQuery(sql);
        List<String> sqls = new ArrayList<String>();
        for (int i = 0; i < DR.getUseStep().size(); i++) {
            sqls.add("insert TMeterDynamic(FMeterID,FBeginRead,FEndRead,FGasPrice,FSismsid) Values('"
                    + meterID + "'," + DR.getUseStep().get(i).getbRead() + "," + DR.getUseStep().get(i).geteRead()
                    + "," + DR.getUseStep().get(i).getPrice() + ",'" + sb.toString() + "')");
        }
        for (int i = 0; i < DR.getStageStep().size(); i++) {
            sqls.add("insert TMeterStage(FMeterID,FStartRead,FStartDate,FStartMoney) values('"
                    + meterID + "'," + DR.getStageStep().get(i).getbRead() + ",'" + DR.getStageStep().get(i).getbDate()
                    + "'," + DR.getStageStep().get(i).getbMoney() + ")");
        }
        //判断是否存在金额差异
        double dev = newMSM.getMoney() - remainMoney;
        if (dev != 0.00d) {
            sqls.add("insert TMeterError(FMeterID,FError) values('" + meterID + "'," + dev + ")");
        }
        sqlhelper.executeWithTransaction(sqls);
        sr.setResult(true);
        return sr;
    }

    public SettleResult executeByFail(String meterID, int meterRead) {
        SettleResult sr = new SettleResult(false);
        MeterStatModal oldMSM = new MeterStatModal();
        PriceModal priceModal = new PriceModal();
        ResultSet rs = sqlhelper.executeQuery("select * from TMeterStatus where FMeterID='" + meterID + "'");
        try {
            if (!rs.next()) {
                sr.setDescription("获取表具上次状态数据失败！");
                return sr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        oldMSM.MeterRead = (int) rs.Rows[0]["FMeterRead"];
//        oldMSM.Money = (double) rs.Rows[0]["FResidual"];
//        oldMSM.MeterTime = (Timestamp) rs.Rows[0]["FDateTime"];
        try {
            oldMSM.setMeterRead(rs.getInt("FMeterRead"));
            oldMSM.setMoney(rs.getDouble("FResidual"));
            oldMSM.setMeterTime(rs.getTimestamp("FDateTime"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        rs = sqlhelper.executeQuery("select A.FStageBeginDate,B.* from TUser A left join TSaleStrategy B on A.FSaleStrategyID=B.FStrategyID where A.FMeterID='" + meterID + "'");
        try {
            if (!rs.next()) {
                sr.setDescription("获取用户单价数据失败！");
                return sr;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

//        priceModal.ID = (int) rs.Rows[0]["FStrategyID"];
//        priceModal.p0 = (double) rs.Rows[0]["FPrice"];
//        priceModal.p1 = (double) rs.Rows[0]["FStagePrice1"];
//        priceModal.p2 = (double) rs.Rows[0]["FStagePrice2"];
//        priceModal.p3 = (double) rs.Rows[0]["FStagePrice3"];
//        priceModal.a1 = (int) rs.Rows[0]["FBeginAmount1"];
//        priceModal.a2 = (int) rs.Rows[0]["FBeginAmount2"];
//        priceModal.a3 = (int) rs.Rows[0]["FBeginAmount3"];
//        priceModal.StartDate = (Timestamp) rs.Rows[0]["FStageBeginDate"];
//        priceModal.CycleLen = (int) rs.Rows[0]["FCycleLength"];
        try {
            priceModal.setID(rs.getInt("FStrategyID"));
            priceModal.setP0(rs.getDouble("FPrice"));
            priceModal.setP1(rs.getDouble("FStagePrice1"));
            priceModal.setP2(rs.getDouble("FStagePrice2"));
            priceModal.setP3(rs.getDouble("FStagePrice3"));
            priceModal.setA1(rs.getInt("FBeginAmount1"));
            priceModal.setA2(rs.getInt("FBeginAmount2"));
            priceModal.setA3(rs.getInt("FBeginAmount3"));
            priceModal.setStartDate(rs.getTimestamp("FStageBeginDate"));
            priceModal.setCycleLen(rs.getInt("FCycleLength"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //
        DynamicRecord DR = new DynamicRecord();
        MeterStatModal newMSM = GasMeterRun.maintainRun(oldMSM, priceModal, meterRead, DR);
        //根据运算结果记录数据
        //String Sismsid = string.Format("WX{0:yyyyMMddHHmmss}", Timestamp.Now);
        String Sismsid = "WX" + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String sql = "update TMeterStatus set FMeterRead=" + newMSM.getMeterRead() + ",FResidual="
                + newMSM.getMoney() + ",FDateTime='" + dateFormat.format(new Date()) + "',FMeterTime='" + dateFormat.format(new Date()) + "'"
                + ",FCycleBeginRead=" + newMSM.getCycleBeginRead() + " where FMeterID='" + meterID + "'";
        sqlhelper.executeNonQuery(sql);
        List<String> sqls = new ArrayList<String>();
        for (int i = 0; i < DR.getUseStep().size(); i++) {
            sqls.add("insert TMeterDynamic(FMeterID,FBeginRead,FEndRead,FGasPrice,FSismsid) Values('"
                    + meterID + "'," + DR.getUseStep().get(i).getbRead() + "," + DR.getUseStep().get(i).geteRead()
                    + "," + DR.getUseStep().get(i).getPrice() + ",'" + Sismsid + "')");
        }
        for (int i = 0; i < DR.getStageStep().size(); i++) {
            sqls.add("insert TMeterStage(FMeterID,FStartRead,FStartDate,FStartMoney) values('"
                    + meterID + "'," + DR.getStageStep().get(i).getbRead() + ",'" + DR.getStageStep().get(i).getbDate()
                    + "'," + DR.getStageStep().get(i).getbMoney() + ")");
        }
        sqlhelper.executeWithTransaction(sqls);
        if (DR.getStageStep().size() > 0) {
            //跨越阶梯周期，使用量采用平均法计算
            sr.setDescription("N," + Sismsid);
        } else {
            //未跨阶梯周期
            sr.setDescription("Y," + Sismsid);
        }
        sr.setResult(true);
        return sr;
    }

}
