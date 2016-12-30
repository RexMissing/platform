package org.whut.meterManagement.smsmeterservice.service;

import org.whut.meterManagement.smsmeterservice.service_entity.*;
import org.whut.meterManagement.smsmeterservice.serviceinterface.QueryService;

import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/29.
 */
public class QueryIml implements QueryService {

    @Override
    public boolean verificationOp(WS_Operator op) {
        return false;
    }

    @Override
    public WResult queryUserInfo(WS_Operator op, WS_UserInfo user) {
        return null;
    }

    @Override
    public WResult queryUserInfo(WS_Operator op, int type, String keyvalue, List<WS_UserInfo> users) {
        return null;
    }

    @Override
    public WResult queryUserInfo(WS_Operator op, List<QCondition> cond, List<WS_UserInfo> users) {
        return null;
    }

    @Override
    public WResult queryUserInfo(WS_Operator op, String sqlcond, List<WS_UserInfo> users) {
        return null;
    }

    @Override
    public List<String> queryAllOpID(String compName) {
        return null;
    }

    @Override
    public WResult queryMeter(WS_Operator op, Date B, Date E, List<WS_Meter> meters) {
        return null;
    }

    @Override
    public WResult queryMeter(WS_Operator op, WS_Meter meter) {
        return null;
    }

    @Override
    public WResult queryMeter(WS_Operator op, String Sim, WS_Meter meter) {
        return null;
    }

    @Override
    public WResult queryArea(WS_Operator op, List<String> areas) {
        return null;
    }

    @Override
    public WResult queryOperatorList(WS_Operator op, List<WS_Operator> ops) {
        return null;
    }

    @Override
    public WResult queryCharge(WS_Operator op, int UserID, List<WS_Recharge> recharges) {
        return null;
    }

    @Override
    public WResult queryCharge(WS_Operator op, Date begin, Date end, List<WS_Recharge> recharges) {
        return null;
    }

    @Override
    public WResult queryAllPrice(WS_Operator op, List<WS_Price> pricelst) {
        return null;
    }

    @Override
    public WResult queryAllCommand(WS_Operator op, String MeterID, Date dtb, Date dte, List<WS_AllSendCommand> cmds) {
        return null;
    }

    @Override
    public WResult queryAllCommand(WS_Operator op, Date dtb, Date dte, int UserID, List<WS_AllSendCommand> cmds) {
        return null;
    }

    @Override
    public WResult queryAllCommand(WS_Operator op, String MeterID, List<WS_AllSendCommand> cmds) {
        return null;
    }

    @Override
    public WResult queryMeterDynamic(WS_Operator op, String MeterID, List<WS_MeterDynamic> mds) {
        return null;
    }

    @Override
    public WResult reportUserInfo_bySQL(WS_Operator op, String sqlcond) {
        return null;
    }
}
