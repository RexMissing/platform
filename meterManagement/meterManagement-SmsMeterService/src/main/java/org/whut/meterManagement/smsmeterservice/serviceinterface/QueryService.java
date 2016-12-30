package org.whut.meterManagement.smsmeterservice.serviceinterface;

import org.whut.meterManagement.smsmeterservice.service_entity.*;

import java.util.Date;
import java.util.List;

/**
 * Created by chenfu on 2016/12/29.
 */
public interface QueryService {

    boolean verificationOp(WS_Operator op);

    WResult queryUserInfo(WS_Operator op,WS_UserInfo user);

    WResult queryUserInfo(WS_Operator op, int type, String keyvalue,List<WS_UserInfo> users);

    WResult queryUserInfo(WS_Operator op, List<QCondition> cond,List<WS_UserInfo> users);

    WResult queryUserInfo(WS_Operator op, String sqlcond,List<WS_UserInfo> users);

    List<String> queryAllOpID(String compName);

    WResult queryMeter(WS_Operator op, Date B, Date E,List<WS_Meter> meters);

    WResult queryMeter(WS_Operator op,WS_Meter meter);

    WResult queryMeter(WS_Operator op, String Sim,WS_Meter meter);

    WResult queryArea(WS_Operator op,List<String> areas);

    WResult queryOperatorList(WS_Operator op,List<WS_Operator> ops);

    WResult queryCharge(WS_Operator op, int UserID,List<WS_Recharge> recharges);

    WResult queryCharge(WS_Operator op, Date begin, Date end,List<WS_Recharge> recharges);

    WResult queryAllPrice(WS_Operator op,List<WS_Price> pricelst);

    WResult queryAllCommand(WS_Operator op, String MeterID, Date dtb, Date dte,List<WS_AllSendCommand> cmds);

    WResult queryAllCommand(WS_Operator op, Date dtb, Date dte, int UserID,List<WS_AllSendCommand> cmds);

    WResult queryAllCommand(WS_Operator op, String MeterID,List<WS_AllSendCommand> cmds);

    WResult queryMeterDynamic(WS_Operator op, String MeterID,List<WS_MeterDynamic> mds);

    WResult reportUserInfo_bySQL(WS_Operator op, String sqlcond);
}
