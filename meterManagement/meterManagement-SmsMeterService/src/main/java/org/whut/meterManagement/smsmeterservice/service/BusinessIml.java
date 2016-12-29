package org.whut.meterManagement.smsmeterservice.service;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.whut.meterManagement.meterSettlement.CheckAndSettlement;
import org.whut.meterManagement.meterSettlement.SettleResult;
import org.whut.meterManagement.smsmeterservice.corebusiness.SMSBusiness;
import org.whut.meterManagement.smsmeterservice.service_entity.*;
import org.whut.meterManagement.smsmeterservice.serviceinterface.BusinessService;
import org.whut.meterManagement.sqldatalib.sqlhelper.StdUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/22.
 */
public class BusinessIml implements BusinessService {

    /// <summary>
    /// 用户开通表具(适用于新表具)
    /// </summary>
    /// <param name="op"></param>
    /// <param name="userID"></param>
    /// <param name="MeterID"></param>
    /// <param name="StrategyID"></param>
    /// <param name="Money"></param>
    /// <param name="SDT">阶梯周期起始日期</param>
    /// <param name="CBR">抄表日</param>
    /// <returns></returns>
    @Override
    public WResult userOpenMeter(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr) {
        WResult wr = new WResult(userID);

        if (!wr.VerOP(op)) {
            return wr;
        }
        StringBuffer sb = new StringBuffer();
        SMSBusiness busi = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        boolean brst = busi.openUser(userID, meterID, strategyID, money, sdt, cbr, sb);
        if (brst) {
            wr.setBResult(busi.sendSmsCommand(sb.toString()));
        } else {
            wr.setErDes(sb);
        }
        return wr;
    }

    /// <summary>
    /// 表具开通（IC卡开通） 新表具
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="userID">用户编号</param>
    /// <param name="MeterID">表具编号</param>
    /// <param name="StrategyID">单价类型</param>
    /// <param name="Money">充值金额</param>
    /// <param name="cardID">IC卡编号</param>
    /// <returns></returns>
    @Override
    public WResult userOpenIC(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr, String cardID, int overdraftStyle, WS_IcData icdata) {
        WResult wr = new WResult();
        //icdata = null;
        if (!wr.VerOP(op)) {
            return wr;
        } else {
            ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TUser where FCardID='" + cardID + "'");
            try {
                if (!rs.next()) {
                    //新卡号，可使用，更新TUser中的FCardID
                    StdUtils.getSqlh().executeNonQuery("update TUser set FCardID='" + cardID + "' where FUserID=" + userID);
                } else {
                    if (rs.getInt("FUserID") == userID) {
                        wr.setBResult(false);
                        StringBuffer sb = new StringBuffer();
                        sb.append("卡号已被其他用户使用");
                        wr.setErDes(sb);
                        return wr;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // TODO
            //String SNO = ConfigurationManager.AppSettings["ServerNo"];
            String sno = "";
            StringBuffer sb = new StringBuffer();
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
            String[] SMSs = new String[3]; //0:开通;1:更改服务号码;2:更改透支方式

            boolean brst = busiSvr.openUser(userID, meterID, strategyID, money, sdt, cbr, sb);
            if (!brst) {
//                wr.ErDes = Sismsid;
//                wr.bResult = false;
                wr.setBResult(false);
                wr.setErDes(sb);
                return wr;
            }
            SMSs[0] = busiSvr.getCommandStr(sb.toString());
            brst = busiSvr.setServerNo(meterID, sno, sb.delete(0, sb.length()));
            if (!brst) {
//                wr.bResult = false;
//                wr.ErDes = Sismsid;
                wr.setBResult(false);
                wr.setErDes(sb);
                return wr;
            }
            SMSs[1] = busiSvr.getCommandStr(sb.toString());
            //更改透支方式
            brst = busiSvr.overdraftStyle(userID, overdraftStyle, sb.delete(0, sb.length()));
            if (!brst) {
//                wr.bResult = false;
//                wr.ErDes = Sismsid;
                wr.setBResult(false);
                wr.setErDes(sb);
                return wr;
            }
            SMSs[2] = busiSvr.getCommandStr(sb.toString());

            //icdata = WS_IcData.buildWrite(cardID, SMSs);
            WS_IcData.buildWrite(cardID, SMSs, icdata);
            StdUtils.getSqlh().getDB().closeConn(rs);
        }
        return wr;
    }

    /// <summary>
    /// 发送远程读表命令
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserIDs">用户编号</param>
    /// <returns>成功发送指令的用户编号</returns>
    @Override
    public List<WResult> remoteReadMeter(WS_Operator op, List<Integer> userIDs) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userIDs.size(); i++) {
            WResult wr = new WResult();
//            wr.ID = UserIDs[i];
//            wr.BResult = busiSvr.ReadMeter(UserIDs[i], er);
//            wr.ErDes = er;
            wr.setID(userIDs.get(i));
            wr.setBResult(busiSvr.readMeter(userIDs.get(i), sb.delete(0, sb.length())));
            wr.setErDes(sb);

            wrList.add(wr);
        }
        return wrList;
    }

    /// <summary>
    /// 定时读表
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserIDs">用户编号 列表</param>
    /// <param name="DT">设定时间</param>
    /// <returns></returns>
    @Override
    public List<WResult> remoteReadMeterAtTime(WS_Operator op, List<Integer> userIDs, Date dt) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userIDs.size(); i++) {
            WResult wr = new WResult();
//            wr.ID = UserIDs[i];
//            wr.BResult = busiSvr.ReadMeter(UserIDs[i], DT, er);
//            wr.ErDes = er;
            wr.setID(userIDs.get(i));
            wr.setBResult(busiSvr.readMeter(userIDs.get(i), dt, sb.delete(0, sb.length())));
            wr.setErDes(sb);

            wrList.add(wr);
        }
        return wrList;
    }

    /// <summary>
    /// 远程阀门控制
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="mode">膜式 1:关闭； 0:开启; -1:强制关闭</param>
    /// <param name="UserIDs"></param>
    /// <returns></returns>
    @Override
    public List<WResult> remoteValveControl(WS_Operator op, int mode, List<Integer> userIDs) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userIDs.size(); i++) {
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            wr.setBResult(busiSvr.valveControl(userIDs.get(i), mode, sb.delete(0, sb.length())));
            wr.setErDes(sb);

            wrList.add(wr);
        }
        return wrList;
    }

    /// <summary>
    /// 定时关闭阀门
    /// </summary>
    /// <param name="op"></param>
    /// <param name="UserIDs"></param>
    /// <param name="AtTime"></param>
    /// <returns></returns>
    @Override
    public List<WResult> remoteCloseValveAtTime(WS_Operator op, List<Integer> userIDs, Date atTime) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userIDs.size(); i++) {
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            wr.setBResult(busiSvr.closeValveAtTime(userIDs.get(i), atTime, sb.delete(0, sb.length())));
            wr.setErDes(sb);

            wrList.add(wr);
        }
        return wrList;
    }

    /// <summary>
    /// 请求表具校对时钟（适用于v1表具）
    /// </summary>
    /// <param name="op"></param>
    /// <param name="UserIDs"></param>
    /// <returns></returns>
    @Override
    public List<WResult> checkMeterTime(WS_Operator op, List<Integer> userIDs) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userIDs.size(); i++) {
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            wr.setBResult(busiSvr.checkMeterTime(userIDs.get(i), sb.delete(0, sb.length())));
            wr.setErDes(sb);

            wrList.add(wr);
        }
        return wrList;
    }

    /// <summary>
    /// 设置表具总用量及阶梯周期量
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserID">用户编号</param>
    /// <param name="Sum">总用量</param>
    /// <param name="cur">当前周期量</param>
    /// <param name="pre">上个周期量</param>
    /// <param name="mode">设置模式</param>
    /// <returns></returns>
    @Override
    public WResult remoteMeterUserSet(WS_Operator op, int userID, int sum, int cur, int pre, byte mode) {
        WResult wr = new WResult(userID);
        if (!wr.VerOP(op)) {
            return wr;
        } else {
            StringBuffer sb = new StringBuffer();
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
            wr.setBResult(busiSvr.meterUseSet(userID, sum, cur, pre, mode, sb));
            wr.setErDes(sb);
        }
        return wr;
    }

    /// <summary>
    /// 短信充值
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserID">用户编号</param>
    /// <param name="Money">充值金额</param>
    /// <returns></returns>
    @Override
    public WResult remoteCharge(WS_Operator op, int userID, double money) {
        WResult wr = new WResult(userID, false);
        if (!wr.VerOP(op)) {
            return wr;
        } else {
            StringBuffer sb = new StringBuffer();
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());

            boolean brst = busiSvr.charge(userID, money, sb);
            if (brst) {
                wr.setBResult(busiSvr.sendSmsCommand(sb.toString()));
            } else {
                wr.setBResult(false);
                wr.setErDes(sb);
            }
        }
        return wr;
    }

    /// <summary>
    /// IC卡充值
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserID">用户编号</param>
    /// <param name="Money">充值金额</param>
    /// <param name="Ver">表具版本</param>
    /// <returns></returns>
    @Override
    public WResult icCharge(WS_Operator op, int userID, double money, String cardID, String data, WS_IcData icdata) {
        WResult wr = new WResult(userID, false);
        //icdata = null;
        if (!wr.VerOP(op)) {
            return wr;
        }
        WS_IcData ic = new WS_IcData();
        StringBuffer sb = new StringBuffer();
        if (!WS_IcData.readFromDataStr(data, cardID, ic, sb)) {
            wr.setBResult(false);
            wr.setErDes(sb);
            return wr;
        }
        //查询IC卡号对应的用户
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TUser where FCardID='" + cardID + "'");
        try {
            if (!rs.next()) {
                //新卡号，可使用，更新TUser中的FCardID
                StdUtils.getSqlh().executeNonQuery("update TUser set FCardID='" + cardID + "' where FUserID=" + userID);
            } else {
                if (rs.getInt("FUserID") != userID) {
                    wr.setBResult(false);
                    sb.delete(0, sb.length()).append("卡号与用户编号不符，不能充值");
                    wr.setErDes(sb);
                    return wr;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        boolean brst = busiSvr.charge(userID, money, sb.delete(0, sb.length()));
        if (brst) {
            //icdata = WS_IcData.buildWrite(cardID, SMS);
            String SMS = busiSvr.getCommandStr(sb.toString());
            WS_IcData.buildWrite(cardID, SMS, icdata);
            wr.setBResult(true);
        } else {
            wr.setErDes(sb);
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wr;
    }

    /// <summary>
    /// 读取IC卡回传数据
    /// </summary>
    /// <param name="op"></param>
    /// <param name="CardID"></param>
    /// <param name="IcData"></param>
    /// <returns></returns>
    @Override
    public WResult readIC(WS_Operator op, String cardID, String data, WS_IcData icdata) {
        WResult wr = new WResult(false);
        //icdata = null;
        if (!wr.VerOP(op)) {
            return wr;
        }
        StringBuffer sb = new StringBuffer();
        if (!WS_IcData.readFromDataStr(data, cardID, icdata, sb)) {
            wr.setBResult(false);
            wr.setErDes(sb);
        } else {
            wr.setBResult(true);
        }

        return wr;
    }

    /// <summary>
    /// 更改单价(新表具)
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserID">用户编号</param>
    /// <param name="SaleID">单价ID</param>
    /// <param name="IsAtTime">是否定时</param>
    /// <param name="SchDT">定时时间</param>
    /// <returns></returns>
    @Override
    public WResult remoteChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Date schDT) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
        StringBuffer sb = new StringBuffer();
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        boolean brst = busiSvr.setPrice(userID, saleID, schDT, isAtTime, sb);
        if (brst) {
            wr.setBResult(busiSvr.sendSmsCommand(sb.toString()));
        } else {
            wr.setBResult(false);
            wr.setErDes(sb);
        }

        return wr;
    }

    /// <summary>
    /// IC卡更改单价(新表具)
    /// </summary>
    /// <param name="op"></param>
    /// <param name="UserID"></param>
    /// <param name="SaleID"></param>
    /// <param name="IsAtTime"></param>
    /// <param name="SchDT"></param>
    /// <param name="CardID"></param>
    /// <param name="IcData"></param>
    /// <returns></returns>
    @Override
    public WResult icChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schDT, String cardID, String data, WS_IcData icdata) {
        WResult wr = new WResult();
        //icdata = null;
        if (!wr.VerOP(op)) {
            return wr;
        }
        WS_IcData ic = new WS_IcData();
        StringBuffer sb = new StringBuffer();
        if (!WS_IcData.readFromDataStr(data, cardID, ic, sb)) {
            wr.setBResult(false);
            wr.setErDes(sb);
            return wr;
        }
        //查询IC卡号对应的用户
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TUser where FCardID='" + cardID + "'");
        try {
            if (!rs.next()) {
                //新卡号，可使用，更新TUser中的FCardID
                StdUtils.getSqlh().executeNonQuery("update TUser set FCardID='" + cardID + "' where FUserID=" + userID);
            } else {
                if (rs.getInt("FUserID") != userID) {
                    wr.setBResult(false);
                    sb.delete(0, sb.length()).append("卡号与用户编号不符，不能充值");
                    wr.setErDes(sb);
                    return wr;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuffer stringBuffer = new StringBuffer();
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        boolean brst = busiSvr.setPrice(userID, saleID, schDT, isAtTime, stringBuffer);
        if (brst) {
            String SMS = busiSvr.getCommandStr(stringBuffer.toString());
            WS_IcData.buildWrite(cardID, SMS, icdata);
            wr.setBResult(true);
        } else {
            wr.setErDes(stringBuffer);
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wr;
    }

    /// <summary>
    /// 批量价格调整
    /// </summary>
    /// <param name="op"></param>
    /// <param name="SaleID"></param>
    /// <param name="NewSaleID"></param>
    /// <param name="IsAtTime"></param>
    /// <param name="SchDT"></param>
    /// <returns></returns>
    @Override
    public WResult batchChangePrice(WS_Operator op, int saleID, int newSaleID, boolean isAtTime, Date schDT) {
        WResult wr = new WResult(false);
        if (!wr.VerOP(op)) {
            return wr;
        }
        //查询符合记录的用户
        ResultSet rs = StdUtils.getSqlh().executeQuery("select FUserID from TUser where FSaleStrategyID=" + saleID);
        int cnt_Fail = 0;
        int cnt_Succ = 0;
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        try {
            StringBuffer sb = new StringBuffer();
            while (rs.next()) {
                if (busiSvr.setPrice(rs.getInt(1), newSaleID, schDT, isAtTime, sb.delete(0, sb.length()))) {
                    cnt_Succ++;
                    busiSvr.sendSmsCommand(sb.toString());
                } else {
                    cnt_Fail++;
                }
            }
            wr.setBResult(true);
            String str = "总计需要变更" + rs.getRow() + "户，成功变更" + cnt_Succ + "户，未变更" + cnt_Fail + "户";
            wr.setErDes(sb.delete(0, sb.length()).append(str));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        StdUtils.getSqlh().getDB().closeConn(rs);

        return wr;
    }

    /// <summary>
    /// 重发指令
    /// </summary>
    /// <param name="op"></param>
    /// <param name="sismsid"></param>
    /// <returns></returns>
    @Override
    public WResult reSendCmd(WS_Operator op, String sismsid) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        } else {
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
            wr.setBResult(busiSvr.sendSmsCommand(sismsid));
        }
        return wr;
    }

    /// <summary>
    /// 重写IC卡指令
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="sismsid">指令唯一号</param>
    /// <param name="cardid">IC卡编号</param>
    /// <returns></returns>
    @Override
    public WResult reWriteIC(WS_Operator op, String sismsid, String cardID, WS_IcData icdata) {
        WResult wr = new WResult();
        //icdata = null;
        if (!wr.VerOP(op)) {
            return wr;
        }

        StringBuffer sb = new StringBuffer();
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        wr.setBResult(busiSvr.reWriteIC(sismsid, cardID, sb));
        wr.setErDes(sb);
        if (wr.isBResult()) {
            WS_IcData.buildWrite(cardID, sb.toString(), icdata);
        }
        return wr;
    }

    /// <summary>
    /// 故障表具金额结算
    /// </summary>
    /// <param name="op"></param>
    /// <param name="UserID"></param>
    /// <returns></returns>
    @Override
    public WResult meterJieSuan(WS_Operator op, int userID, int meterRead) {
        WResult wr = new WResult(userID, false);
        if (!wr.VerOP(op)) {
            return wr;
        }

        StringBuffer sb = new StringBuffer();

        //查询用户表具编号
        Object obj = StdUtils.getSqlh().executeScalar("select FMeterID from TUser where FUserID=" + userID);
        if (obj == null) {
            sb.append("用户资料不存在");
            wr.setErDes(sb);
            return wr;
        }
        String meterID = obj.toString();
        CheckAndSettlement cas = new CheckAndSettlement(StdUtils.getSqlh());
        SettleResult sr = cas.executeByFail(meterID, meterRead);
        if (!sr.isResult()) {
            sb.delete(0, sb.length()).append(sr.getDescription());
            wr.setErDes(sb);
            return wr;
        }

        //-------------
        sb.delete(0, sb.length()).append(sr.getDescription());
        wr.setErDes(sb);
        wr.setBResult(true);
        return wr;
    }

    /// <summary>
    /// 维修换表处理
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserID">用户编号</param>
    /// <param name="OldRead">旧表止码</param>
    /// <param name="NewMeterID">新表表号</param>
    /// <param name="cardid">IC卡号</param>
    /// <returns></returns>
    @Override
    public WResult changeMeter(WS_Operator op, Integer userID, String newMeterID, double updateMoney,
                               double chargeMoney, String cardID, WS_IcData icdata) {
        WResult wr = new WResult();
        //icdata = null;
        if (!wr.VerOP(op)) {
            return wr;
        }
        int bzql = 0;
        int szql = 0;
        //获取本周期量
        Object obj = StdUtils.getSqlh().executeScalar("select FMeterID from TUser where FUserID=" + userID);
        String oldMeterID = obj.toString();
        obj = StdUtils.getSqlh().executeScalar("select FStartRead from TMeterStage where FMeterID='"
                + oldMeterID + "' order by FStartDate desc");
        int startread = 0;
        if (obj != null) {
            startread = Integer.parseInt(obj.toString());
        }
        obj = StdUtils.getSqlh().executeScalar("select FMeterRead from TMeterStatus where FMeterID='" + oldMeterID + "'");
        int lastread = Integer.parseInt(obj.toString());
        bzql = lastread - startread;
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        if (busiSvr.openMeter(userID, newMeterID, updateMoney, chargeMoney, bzql, szql, sb)) {
            String SMS = busiSvr.getCommandStr(sb.toString());
            WS_IcData.buildWrite(cardID, SMS, icdata);
            wr.setBResult(true);
        } else {
            wr.setBResult(false);
            wr.setErDes(sb);
        }
        return wr;
    }

    /// <summary>
    /// 更改表具每月抄表日
    /// </summary>
    /// <param name="op"></param>
    /// <param name="UserIDs"></param>
    /// <param name="CBR"></param>
    /// <returns></returns>
    @Override
    public List<WResult> changeCbr(WS_Operator op, List<Integer> userIDs, int cbr) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userIDs.size(); i++) {
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            wr.setBResult(busiSvr.meterSetCBR(userIDs.get(i), cbr, sb.delete(0, sb.length())));
            wr.setErDes(sb);
            wrList.add(wr);
        }
        return wrList;
    }

    /// <summary>
    /// 更改表具透支方式
    /// </summary>
    /// <param name="op"></param>
    /// <param name="UserIDs"></param>
    /// <param name="overdraftStyle"></param>
    /// <returns></returns>
    @Override
    public List<WResult> changeOverdraftStyle(WS_Operator op, List<Integer> userIDs, int overdraftStyle) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < userIDs.size(); i++) {
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            wr.setBResult(busiSvr.overdraftStyle(userIDs.get(i), overdraftStyle, sb.delete(0, sb.length())));
            wr.setErDes(sb);
            wrList.add(wr);
        }

        return wrList;
    }

    /// <summary>
    /// 更新用户资料（包括新增，修改。ID=0为新增，大于0为修改）
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="usi">用户资料</param>
    /// <returns></returns>
    @Override
    public WResult updateUserInfo(WS_Operator op, WS_UserInfo usi) {
        WResult wr = new WResult(usi.getUserID());
        if (!wr.VerOP(op)) {
            return wr;
        }
        StringBuffer sb = new StringBuffer();
        if (usi.getUserID() > 0) {
            //判断地址是否重复
            if (StdUtils.getSqlh().executeExsit("select * from TUser where FAddress='" + usi.getAddress()
                    + "' and FUserID<>" + usi.getUserID())) {
//                wr.BResult = false;
//                wr.ErDes = "修改后地址与现有地址重复";
                wr.setBResult(false);
                wr.setErDes(sb.append("修改后地址与现有地址重复"));
                return wr;
            }
            //修改数据
            String sql = "update TUser set FName='" + usi.getUserName() + "',FAddress='" + usi.getAddress()
                    + "',FPhone='" + usi.getUserPhone() + "',FIDNo='" + usi.getIdNo() + "',FArea='" + usi.getArea()
                    + "' where FUserID=" + usi.getUserID();
            StdUtils.getSqlh().executeNonQuery(sql);
            wr.setBResult(true);
        } else {
            //判断地址是否重复
            if (StdUtils.getSqlh().executeExsit("select * from TUser where FAddress='" + usi.getAddress() + "'")) {
//                wr.BResult = false;
//                wr.ErDes = "地址与现有地址重复";
                wr.setBResult(false);
                wr.setErDes(sb.delete(0, sb.length()).append("地址与现有地址重复"));
                return wr;
            }
            //新增数据
            String sql = "insert TUser(FName,FAddress,FPhone,FIDNo,FArea) Values('" + usi.getUserName()
                    + "','" + usi.getAddress() + "','" + usi.getUserPhone() + "','" + usi.getIdNo() + "','" + usi.getArea() + "')";
            wr.setID(StdUtils.getSqlh().insertGetID(sql));
            wr.setBResult(true);
        }
        return wr;
    }

    /// <summary>
    /// 新增小区分类
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="area">小区信息</param>
    /// <returns></returns>
    @Override
    public WResult newArea(WS_Operator op, String area) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
        StdUtils.getSqlh().executeNonQuery("insert TConfig(FParamName,FParamValue) Values('FArea','" + area + "')");
        wr.setErDes(new StringBuffer().append(area));
        return wr;
    }

    /// <summary>
    /// 更新单价记录（包括新增、修改。ID=0为新增，ID>0为修改）
    /// </summary>
    /// <param name="op"></param>
    /// <param name="Pr"></param>
    /// <returns></returns>
    @Override
    public WResult updatePrice(WS_Operator op, WS_Price pr) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
        String sql;
        StringBuffer sb = new StringBuffer();
        if (pr.getSaleStrategyID() > 0) {
            sql = "select * from TUser where FSaleStrategyID=" + pr.getSaleStrategyID();
            if (StdUtils.getSqlh().executeExsit(sql)) {
//                wr.BResult = false;
//                wr.ErDes = "此单价仍在被用户使用，不能停用";
                wr.setBResult(false);
                wr.setErDes(sb.append("此单价仍在被用户使用，不能停用"));
            } else {
                sql = "update TSaleStrategy set FEnabled=0 where FStrategyID=" + pr.getSaleStrategyID();
                if (StdUtils.getSqlh().executeNonQuery(sql) == 1) {
//                    wr.BResult = true;
                    wr.setBResult(true);
                } else {
//                    wr.BResult = false;
//                    wr.ErDes = "更新数据失败";
                    wr.setBResult(false);
                    wr.setErDes(sb.delete(0, sb.length()).append("更新数据失败"));
                }
            }
        } else {
            //INSERT
            sql = "insert TSaleStrategy(FStrategyName,FPrice,FStagePrice1,FStagePrice2,FStagePrice3,FBeginAmount1,"
                    + "FBeginAmount2,FBeginAmount3,FCycleLength) values('" + pr.getStrategyName() + "'," + pr.getPrice() + ","
                    + pr.getStagePrice1() + "," + pr.getStagePrice2() + "," + pr.getStagePrice3() + "," + pr.getBeginAmount1() + ","
                    + pr.getBeginAmount2() + "," + pr.getBeginAmount3() + "," + pr.getCycleLength() + ")";

//            pr.SaleStrategyID = StdUtils.SqlH.InsertGetID(sql);
//            wr.BResult = true;
            pr.setSaleStrategyID(StdUtils.getSqlh().insertGetID(sql));
            wr.setBResult(true);
        }
        return wr;
    }

    /// <summary>
    /// 表具资料导入
    /// </summary>
    /// <param name="op"></param>
    /// <param name="MTS"></param>
    /// <returns></returns>
    @Override
    public WResult importMeter(WS_Operator op, List<WS_Meter> MTS) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
//        wr.ErDes = WS_Meter.Import(MTS);
//        wr.BResult = true;
        wr.setErDes(new StringBuffer().append(WS_Meter.Import(MTS)));
        wr.setBResult(true);
        return wr;
    }

    /// <summary>
    /// 修改登录密码
    /// </summary>
    /// <param name="op"></param>
    /// <param name="target">目标操作员</param>
    /// <returns></returns>
    @Override
    public WResult changePassword(WS_Operator op, String newPsw) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
        wr.setBResult(op.changePassword(newPsw));
        wr.setErDes(new StringBuffer().append(newPsw));
        return wr;
    }

    /// <summary>
    /// 新建操作员
    /// </summary>
    /// <param name="op"></param>
    /// <param name="nop"></param>
    /// <returns></returns>
    @Override
    public WResult newOperator(WS_Operator op, WS_Operator newOP) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
        StringBuffer sb = new StringBuffer();
        wr.setBResult(WS_Operator.newOpertor(newOP, op.getOpID(), sb));
        wr.setErDes(sb);
        return wr;
    }

    /// <summary>
    /// 重置操作员密码
    /// </summary>
    /// <param name="op"></param>
    /// <param name="target"></param>
    /// <returns></returns>
    @Override
    public WResult operatorResetPassword(WS_Operator op, WS_Operator target) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
        wr.setBResult(target.defaultPassword());
        return wr;
    }

    /// <summary>
    /// 导入表具资料信息
    /// </summary>
    /// <param name="op"></param>
    /// <param name="efile">excel文件对象</param>
    /// <returns></returns>
    @Override
    public WResult importMeterFile(WS_Operator op, ExcelFile exfile) {
        WResult wr = new WResult();
        if (!wr.VerOP(op))
        {
            return wr;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("文件格式不正确，无法导入。");
        wr.setErDes(sb);
        try
        {
            ByteArrayInputStream stream = new ByteArrayInputStream(exfile.getF_stream());
            HSSFWorkbook workbook = new HSSFWorkbook(stream);
            //获取第一个sheet
            HSSFSheet sheet = workbook.getSheetAt(0);
            //获取sheet的首行
            HSSFRow headRow = sheet.getRow(0);
            //获取列数
            int cellCount = headRow.getLastCellNum();
            if (cellCount<4)
            {
                wr.setBResult(false);
                return wr;
            }
            //获取行数
            int rowCount = sheet.getPhysicalNumberOfRows();
            List<WS_Meter> wmList = new ArrayList<WS_Meter>();
            for (int i = 2; i <= rowCount; i++)
            {
                WS_Meter wm = new WS_Meter();
                wm.setMeterID(sheet.getRow(i - 1).getCell(0).getStringCellValue());
                wm.setSim(sheet.getRow(i - 1).getCell(1).getStringCellValue());
                wm.setIcCID(sheet.getRow(i - 1).getCell(2).getStringCellValue());
                wm.setKey(sheet.getRow(i - 1).getCell(3).getStringCellValue());
                wmList.add(wm);
            }

            wr.setErDes(sb.delete(0, sb.length()).append(WS_Meter.Import(wmList)));
            return wr;
        }
        catch (Exception ex)
        {
            wr.setBResult(false);
            wr.setErDes(sb.delete(0, sb.length()).append(ex.getMessage()));
            return wr;
        }
    }

    /// <summary>
    /// 取得表具资料excel模板
    /// </summary>
    /// <param name="op"></param>
    /// <param name="exfile"></param>
    /// <returns></returns>
    @Override
    public WResult downloadMeterTemplate(WS_Operator op, ExcelFile exfile) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            //exfile = null;
            return wr;
        }

        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("表具资料");
        HSSFRow headRow = sheet.createRow(0);

        HSSFCellStyle style = workbook.createCellStyle();
        style.setFillBackgroundColor(HSSFColor.BLUE.index);

        headRow.createCell(0).setCellValue("表具编号");
        headRow.getCell(0).setCellStyle(style);
        headRow.createCell(1).setCellValue("通讯号码");
        headRow.createCell(2).setCellValue("ICCID");
        headRow.createCell(3).setCellValue("出厂密钥");

        exfile = new ExcelFile();
        exfile.setF_name("表具资料模板.xls");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*exfile.f_len = (int) stream.Length;
        exfile.f_stream = new byte[exfile.f_len];
        stream.Seek(0, SeekOrigin.Begin);
        stream.Read(exfile.f_stream, 0, exfile.f_len);*/

        exfile.setF_len(stream.size());
        /*exfile.setF_stream(new byte[exfile.getF_len()]);
        stream.write(exfile.getF_stream(), 0, exfile.getF_len());*/
        exfile.setF_stream(stream.toByteArray());

        try {
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wr;
    }

}
