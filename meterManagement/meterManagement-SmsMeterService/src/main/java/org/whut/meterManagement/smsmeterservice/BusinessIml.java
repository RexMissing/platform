package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.std.StdUtils;

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
    public WResult UserOpenMeter(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr) {
        WResult rst = new WResult(userID);

        if (!rst.VerOP(op))
        {
            return rst;
        }
        StringBuffer Sismsid = new StringBuffer();
        //SMSBusiness busi = StdUtils.GetBusiness(op.OpID);
        SMSBusiness busi = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        boolean brst = busi.openUser(userID, meterID, strategyID, money, sdt, cbr, Sismsid);
        if (brst)
        {
            //rst.bResult = busi.SendSmsCommand(Sismsid);
            // TODO  SendSmsCommand
            //rst.setBResult(busi.SendSmsCommand(Sismsid));
        }
        else
        {
            rst.setErDes(Sismsid);
        }
        return rst;
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
    public WResult UserOpenIC(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr, String cardID, int overdraftStyle, WS_IcData icdata) {
        WResult rst = new WResult();
        icdata = null;
        if (!rst.VerOP(op))
        {
            return rst;
        }
        else
        {
            ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TUser where FCardID='" + cardID + "'");
            try {
                if (rs.getRow() == 0)
                {
                    //新卡号，可使用，更新TUser中的FCardID
                    StdUtils.getSqlh().executeNonQuery("update TUser set FCardID='" + cardID + "' where FUserID=" + userID);
                }
                else
                {
//                    if ((int)rs.Rows[0]["FUserID"] != userID)
//                    {
//                        rst.bResult = false;
//                        rst.erDes = "卡号以被其他用户使用";
//                        return rst;
//                    }
                    if (Integer.parseInt(rs.getString("FUserID")) == userID) {
                        rst.setBResult(false);
                        rst.getErDes().append("卡号以被其他用户使用");
                        return rst;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            // TODO
            //String SNO = ConfigurationManager.AppSettings["ServerNo"];
            StringBuffer Sismsid = new StringBuffer();
            //SMSBusiness busiSvr = StdUtils.GetBusiness(op.OpID);
            SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
            String[] SMSs = new String[3]; //0:开通;1:更改服务号码;2:更改透支方式

            boolean brst = busiSvr.openUser(userID, meterID, strategyID, money, sdt, cbr, Sismsid);
            if (!brst)
            {
//                rst.ErDes = Sismsid;
//                rst.bResult = false;
                rst.setBResult(false);
                rst.getErDes().append(Sismsid);
                return rst;
            }
            // TODO
            //SMSs[0] = busiSvr.GetCommandStr(Sismsid);

            // TODO
            //brst = busiSvr.SetServerNo(MeterID, SNO, out Sismsid);
            if (!brst)
            {
//                rst.bResult = false;
//                rst.ErDes = Sismsid;
                rst.setBResult(false);
                rst.getErDes().append(Sismsid);
                return rst;
            }
            // TODO
            //SMSs[1] = busiSvr.GetCommandStr(Sismsid);


            //更改透支方式
            // TODO
            //brst = busiSvr.OverdraftStyle(userID, OverdraftStyle, Sismsid);
            if (!brst)
            {
//                rst.bResult = false;
//                rst.ErDes = Sismsid;
                rst.setBResult(false);
                rst.getErDes().append(Sismsid);
                return rst;
            }
            // TODO
            //SMSs[2] = busiSvr.GetCommandStr(Sismsid);

            icdata = WS_IcData.buildWrite(cardID, SMSs);
        }
        return rst;
    }

    /// <summary>
    /// 发送远程读表命令
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserIDs">用户编号</param>
    /// <returns>成功发送指令的用户编号</returns>
    @Override
    public List<WResult> RemoteReadMeter(WS_Operator op, List<Integer> userIDs) {
        List<WResult> rst = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op))
        {
            return rst;
        }
        //SMSBusiness busiSvr = StdUtils.GetBusiness(op.OpID);
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        for (int i = 0; i < userIDs.size(); i++)
        {
            StringBuffer er = new StringBuffer();
            WResult wsr = new WResult();
//            wsr.ID = UserIDs[i];
//            wsr.BResult = busiSvr.ReadMeter(UserIDs[i], er);
//            wsr.ErDes = er;
            wsr.setID(userIDs.get(i));
            // TODO
            //wsr.setBResult(busiSvr.ReadMeter(userIDs.get(i), er));
            wsr.setErDes(er);

            rst.add(wsr);
        }
        return rst;
    }

    /// <summary>
    /// 定时读表
    /// </summary>
    /// <param name="op">操作员</param>
    /// <param name="UserIDs">用户编号 列表</param>
    /// <param name="DT">设定时间</param>
    /// <returns></returns>
    @Override
    public List<WResult> RemoteReadMeterAtTime(WS_Operator op, List<Integer> userIDs, Timestamp dt) {
        List<WResult> rst = new ArrayList<WResult>();
        if (!(new WResult()).VerOP(op))
        {
            return rst;
        }
        SMSBusiness busiSvr = new SMSBusiness(op.getOpID(), StdUtils.getSqlh());
        for (int i = 0; i < userIDs.size(); i++)
        {
            StringBuffer er = new StringBuffer();
            WResult wsr = new WResult();
//            wsr.ID = UserIDs[i];
//            wsr.BResult = busiSvr.ReadMeter(UserIDs[i], DT, er);
//            wsr.ErDes = er;
            wsr.setID(userIDs.get(i));
            // TODO
            //wsr.setBResult(busiSvr.ReadMeter(userIDs.get(i), dt, er));
            wsr.setErDes(er);
            
            rst.add(wsr);
        }
        return rst;
    }

    @Override
    public List<WResult> RemoteValveControl(WS_Operator op, int mode, List<Integer> userIDs) {
        return null;
    }

    @Override
    public List<WResult> RemoteCloseValveAtTime(WS_Operator op, List<Integer> userIDs, Timestamp AtTime) {
        return null;
    }

    @Override
    public List<WResult> CheckMeterTime(WS_Operator op, List<Integer> userIDs) {
        return null;
    }

    @Override
    public WResult RemoteMeterUserSet(WS_Operator op, int userID, int Sum, int cur, int pre, byte mode) {
        return null;
    }

    @Override
    public WResult RemoteCharge(WS_Operator op, int userID, double money) {
        return null;
    }

    @Override
    public WResult ICCharge(WS_Operator op, int userID, double money, String cardID, String IcData, WS_IcData icdata) {
        return null;
    }

    @Override
    public WResult ReadIC(WS_Operator op, String cardID, String IcData, WS_IcData icdata) {
        return null;
    }

    @Override
    public WResult RemoteChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schdt) {
        return null;
    }

    @Override
    public WResult ICChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schdt, String cardID, String IcData, WS_IcData icdata) {
        return null;
    }

    @Override
    public WResult BatchChangePrice(WS_Operator op, int saleID, int NewSaleID, boolean isAtTime, Timestamp schdt) {
        return null;
    }

    @Override
    public WResult ReSendCmd(WS_Operator op, String sismsid) {
        return null;
    }

    @Override
    public WResult ReWriteIC(WS_Operator op, String sismsid, String cardID, WS_IcData icdata) {
        return null;
    }

    @Override
    public WResult MeterJieSuan(WS_Operator op, int userID, int MeterRead) {
        return null;
    }

    @Override
    public WResult ChangeMeter(WS_Operator op, Integer userID, String NewmeterID, double Updatemoney, double Chargemoney, String cardID, WS_IcData icdata) {
        return null;
    }

    @Override
    public List<WResult> ChangeCbr(WS_Operator op, List<Integer> userIDs, int cbr) {
        return null;
    }

    @Override
    public List<WResult> ChangeOverdraftStyle(WS_Operator op, List<Integer> userIDs, int overdraftStyle) {
        return null;
    }

    @Override
    public WResult UpdateUserInfo(WS_Operator op, WS_UserInfo usi) {
        return null;
    }

    @Override
    public WResult NewArea(WS_Operator op, String area) {
        return null;
    }

    @Override
    public WResult UpdatePrice(WS_Operator op, WS_Price Pr) {
        return null;
    }

    @Override
    public WResult ImportMeter(WS_Operator op, List<WS_Meter> MTS) {
        return null;
    }

    @Override
    public WResult ChangePassword(WS_Operator op, String NewPsw) {
        return null;
    }

    @Override
    public WResult NewOperator(WS_Operator op, WS_Operator nop) {
        return null;
    }

    @Override
    public WResult OperatorResetPassword(WS_Operator op, WS_Operator target) {
        return null;
    }

    @Override
    public WResult ImportMeterFile(WS_Operator op, ExcelFile efile) {
        return null;
    }

    @Override
    public WResult DownloadMeterTemplate(WS_Operator op, ExcelFile exfile) {
        return null;
    }
}
