package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.meterSettlement.CheckAndSettlement;
import org.whut.meterManagement.meterSettlement.SettleResult;
import org.whut.meterManagement.sqldatalib.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
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
        StringBuffer Sismsid = new StringBuffer();
        SMSBusiness busi = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        boolean brst = busi.openUser(userID, meterID, strategyID, money, sdt, cbr, Sismsid);
        if (brst) {
            // TODO  SendSmsCommand
            //wr.setBResult(busi.SendSmsCommand(Sismsid));
        } else {
            wr.setErDes(Sismsid);
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
                if (rs.getRow() == 0) {
                    //新卡号，可使用，更新TUser中的FCardID
                    StdUtils.getSqlh().executeNonQuery("update TUser set FCardID='" + cardID + "' where FUserID=" + userID);
                } else {
                    rs.next();
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
            StringBuffer Sismsid = new StringBuffer();
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
            String[] SMSs = new String[3]; //0:开通;1:更改服务号码;2:更改透支方式

            boolean brst = busiSvr.openUser(userID, meterID, strategyID, money, sdt, cbr, Sismsid);
            if (!brst) {
//                wr.ErDes = Sismsid;
//                wr.bResult = false;
                wr.setBResult(false);
                wr.setErDes(Sismsid);
                return wr;
            }
            // TODO
            //SMSs[0] = busiSvr.GetCommandStr(Sismsid);

            // TODO
            //brst = busiSvr.SetServerNo(MeterID, SNO, out Sismsid);
            if (!brst) {
//                wr.bResult = false;
//                wr.ErDes = Sismsid;
                wr.setBResult(false);
                wr.setErDes(Sismsid);
                return wr;
            }
            // TODO
            //SMSs[1] = busiSvr.GetCommandStr(Sismsid);


            //更改透支方式
            // TODO
            //brst = busiSvr.OverdraftStyle(userID, OverdraftStyle, Sismsid);
            if (!brst) {
//                wr.bResult = false;
//                wr.ErDes = Sismsid;
                wr.setBResult(false);
                wr.setErDes(Sismsid);
                return wr;
            }
            // TODO
            //SMSs[2] = busiSvr.GetCommandStr(Sismsid);

            //icdata = WS_IcData.buildWrite(cardID, SMSs);
            WS_IcData.buildWrite(cardID, SMSs, icdata);
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
        for (int i = 0; i < userIDs.size(); i++) {
            StringBuffer er = new StringBuffer();
            WResult wr = new WResult();
//            wr.ID = UserIDs[i];
//            wr.BResult = busiSvr.ReadMeter(UserIDs[i], er);
//            wr.ErDes = er;
            wr.setID(userIDs.get(i));
            // TODO
            //wr.setBResult(busiSvr.ReadMeter(userIDs.get(i), er));
            wr.setErDes(er);

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
    public List<WResult> remoteReadMeterAtTime(WS_Operator op, List<Integer> userIDs, Timestamp rs) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        for (int i = 0; i < userIDs.size(); i++) {
            StringBuffer er = new StringBuffer();
            WResult wr = new WResult();
//            wr.ID = UserIDs[i];
//            wr.BResult = busiSvr.ReadMeter(UserIDs[i], DT, er);
//            wr.ErDes = er;
            wr.setID(userIDs.get(i));
            // TODO
            //wr.setBResult(busiSvr.ReadMeter(userIDs.get(i), rs, er));
            wr.setErDes(er);

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
        for (int i = 0; i < userIDs.size(); i++) {
            StringBuffer er = new StringBuffer();
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            // TODO
            //wr.setBResult(busiSvr.ValveControl(userIDs.get(i), mode, er));
            wr.setErDes(er);

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
    public List<WResult> remoteCloseValveAtTime(WS_Operator op, List<Integer> userIDs, Timestamp atTime) {
        List<WResult> wrList = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op)) {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        for (int i = 0; i < userIDs.size(); i++) {
            StringBuffer er = new StringBuffer();
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            // TODO
            //wr.setBResult(busiSvr.CloseValveAtTime(userIDs.get(i), atTime, er));
            wr.setErDes(er);

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
        for (int i = 0; i < userIDs.size(); i++) {
            StringBuffer er = new StringBuffer();
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            // TODO
            //wr.setBResult(busiSvr.CheckMeterTime(userIDs.get(i), er));
            wr.setErDes(er);

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
            StringBuffer er = new StringBuffer();
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
            // TODO
            //wr.setBResult(busiSvr.MeterUseSet(userID, sum, cur, pre, mode, er));
            wr.setErDes(er);
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
            StringBuffer Sismsid = new StringBuffer();
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());

            // TODO
//            boolean brst = busiSvr.Charge(userID, money, Sismsid);
//            if (brst)
//            {
//                wr.setBResult(busiSvr.SendSmsCommand(Sismsid));
//            }
//            else
//            {
//                wr.setBResult(false);
//                wr.setErDes(Sismsid);
//            }
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
        StringBuffer Sismsid = new StringBuffer();
        if (!WS_IcData.readFromDataStr(data, cardID, ic, Sismsid)) {
            wr.setBResult(false);
            wr.setErDes(Sismsid);
            return wr;
        }
        //查询IC卡号对应的用户
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TUser where FCardID='" + cardID + "'");
        try {
            if (rs.getRow() == 0) {
                //新卡号，可使用，更新TUser中的FCardID
                StdUtils.getSqlh().executeNonQuery("update TUser set FCardID='" + cardID + "' where FUserID=" + userID);
            } else {
                rs.next();
                if (rs.getInt("FUserID") != userID) {
                    wr.setBResult(false);
                    StringBuffer ErDes = new StringBuffer();
                    ErDes.append("卡号与用户编号不符，不能充值");
                    wr.setErDes(ErDes);
                    return wr;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        // TODO
//        boolean brst = busiSvr.Charge(userID, money, Sismsid);
//        if (brst)
//        {
//            String SMS = busiSvr.GetCommandStr(Sismsid);
//            //icdata = WS_IcData.buildWrite(cardID, SMS);
//            WS_IcData.buildWrite(cardID, SMS, icdata);
//            wr.setBResult(true);
//        }
//        else
//        {
//            wr.setErDes(Sismsid);
//        }
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
        StringBuffer Sismsid = new StringBuffer();
        if (!WS_IcData.readFromDataStr(data, cardID, icdata, Sismsid)) {
            wr.setBResult(false);
            wr.setErDes(Sismsid);
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
    public WResult remoteChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schDT) {
        WResult wr = new WResult();
        if (!wr.VerOP(op)) {
            return wr;
        }
        StringBuffer Sismsid = new StringBuffer();
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        // TODO
//        boolean brst = busiSvr.SetPrice(userID, saleID, schDT, isAtTime, Sismsid);
//        if (brst)
//        {
//            wr.setBResult(busiSvr.SendSmsCommand(Sismsid));
//        }
//        else
//        {
//            wr.setBResult(false);
//            wr.setErDes(Sismsid);
//        }

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
        StringBuffer Er = new StringBuffer();
        if (!WS_IcData.readFromDataStr(data, cardID, ic, Er)) {
            wr.setBResult(false);
            wr.setErDes(Er);
            return wr;
        }
        //查询IC卡号对应的用户
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TUser where FCardID='" + cardID + "'");
        try {
            if (rs.getRow() == 0) {
                //新卡号，可使用，更新TUser中的FCardID
                StdUtils.getSqlh().executeNonQuery("update TUser set FCardID='" + cardID + "' where FUserID=" + userID);
            } else {
                rs.next();
                if (rs.getInt("FUserID") != userID) {
                    wr.setBResult(false);
                    StringBuffer sb = new StringBuffer();
                    sb.append("卡号与用户编号不符，不能充值");
                    wr.setErDes(sb);
                    return wr;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StringBuffer Sismsid = new StringBuffer();
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        // TODO
//        boolean brst = busiSvr.SetPrice(userID, saleID, schDT, isAtTime, Sismsid);
//        if (brst) {
//            String SMS = busiSvr.GetCommandStr(Sismsid);
//            WS_IcData.buildWrite(cardID, SMS, icdata);
//            wr.setBResult(true);
//        } else {
//            wr.setErDes(Sismsid);
//        }

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
    public WResult batchChangePrice(WS_Operator op, int saleID, int newSaleID, boolean isAtTime, Timestamp schDT) {
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
            while (rs.next()) {
                StringBuffer Sismsid = new StringBuffer();
                // TODO
//                if (busiSvr.SetPrice(rs.getInt(1), newSaleID, schDT, isAtTime, Sismsid)) {
//                    cnt_Succ++;
//                    busiSvr.SendSmsCommand(Sismsid);
//                } else {
//                    cnt_Fail++;
//                }
            }
            wr.setBResult(true);
            String str = "总计需要变更" + rs.getRow() + "户，成功变更" + cnt_Succ + "户，未变更" + cnt_Fail + "户";
            wr.setErDes(new StringBuffer().append(str));
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
            //wr.setBResult(busiSvr.SendSmsCommand(sismsid));
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
        //WS_IcData ic = WS_IcData.ParseRead(IcRead);
        StringBuffer Er = new StringBuffer();
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        // TODO
        //wr.setBResult(busiSvr.ReWriteIC(sismsid, cardID, Er));
        wr.setErDes(Er);
        if (wr.isBResult()) {
            WS_IcData.buildWrite(cardID, Er.toString(), icdata);
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

        StringBuffer ErDes = new StringBuffer();

        //查询用户表具编号
        Object obj = StdUtils.getSqlh().executeScalar("select FMeterID from TUser where FUserID=" + userID);
        if (obj == null) {
            ErDes.append("用户资料不存在");
            wr.setErDes(ErDes);
            return wr;
        }
        String meterID = obj.toString();
        CheckAndSettlement cas = new CheckAndSettlement(StdUtils.getSqlh());
        SettleResult sr = cas.executeByFail(meterID, meterRead);
        if (!sr.isResult()) {
            ErDes.append(sr.getDescription());
            wr.setErDes(ErDes);
            return wr;
        }

        //-------------
        ErDes.append(sr.getDescription());
        wr.setErDes(ErDes);
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
    public WResult changeMeter(WS_Operator op, Integer userID, String newMeterID, double updateMoney, double chargeMoney, String cardID, WS_IcData icdata) {
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
        obj = StdUtils.getSqlh().executeScalar("select FStartRead from TMeterStage where FMeterID='" + oldMeterID + "' order by FStartDate desc");
        int startread = 0;
        if (obj != null) {
            startread = Integer.parseInt(obj.toString());
        }
        obj = StdUtils.getSqlh().executeScalar("select FMeterRead from TMeterStatus where FMeterID='" + oldMeterID + "'");
        int lastread = Integer.parseInt(obj.toString());
        bzql = lastread - startread;
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        StringBuffer Sismsid = new StringBuffer();
        if (busiSvr.openMeter(userID, newMeterID, updateMoney, chargeMoney, bzql, szql, Sismsid)) {
            // TODO
//            String SMS = busiSvr.GetCommandStr(Sismsid);
//            WS_IcData.buildWrite(cardID, SMS, icdata);
            wr.setBResult(true);
        } else {
            wr.setBResult(false);
            wr.setErDes(Sismsid);
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
        if (!(new WResult()).VerOP(op))
        {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        for (int i = 0; i < userIDs.size(); i++)
        {
            StringBuffer Er = new StringBuffer();
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            // TODO
            //wr.setBResult(busiSvr.MeterSetCBR(userIDs.get(i), cbr, Er));
            wr.setErDes(Er);
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
        if (!(new WResult()).VerOP(op))
        {
            return wrList;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        for (int i = 0; i < userIDs.size(); i++)
        {
            StringBuffer Er = new StringBuffer();
            WResult wr = new WResult();
            wr.setID(userIDs.get(i));
            // TODO
            //wr.setBResult(busiSvr.OverdraftStyle(userIDs.get(i), overdraftStyle, Er));
            wr.setErDes(Er);
            wrList.add(wr);
        }

        return wrList;
    }


    @Override
    public WResult updateUserInfo(WS_Operator op, WS_UserInfo usi) {
        return null;
    }

    @Override
    public WResult newArea(WS_Operator op, String area) {
        return null;
    }

    @Override
    public WResult updatePrice(WS_Operator op, WS_Price Pr) {
        return null;
    }

    @Override
    public WResult importMeter(WS_Operator op, List<WS_Meter> MTS) {
        return null;
    }

    @Override
    public WResult changePassword(WS_Operator op, String NewPsw) {
        return null;
    }

    @Override
    public WResult newOperator(WS_Operator op, WS_Operator nop) {
        return null;
    }

    @Override
    public WResult operatorResetPassword(WS_Operator op, WS_Operator target) {
        return null;
    }

    @Override
    public WResult importMeterFile(WS_Operator op, ExcelFile efile) {
        return null;
    }

    @Override
    public WResult downloadMeterTemplate(WS_Operator op, ExcelFile exfile) {
        return null;
    }
}
