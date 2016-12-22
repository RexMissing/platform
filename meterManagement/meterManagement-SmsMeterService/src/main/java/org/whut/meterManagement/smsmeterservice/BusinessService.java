package org.whut.meterManagement.smsmeterservice;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by chenfu on 2016/12/22.
 */
public interface BusinessService {

    
    WResult userOpenMeter(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr);

    
    WResult userOpenIC(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr, String cardID, int overdraftStyle, WS_IcData icdata);

    
    List<WResult> remoteReadMeter(WS_Operator op, List<Integer> userIDs);

    
    List<WResult> remoteReadMeterAtTime(WS_Operator op, List<Integer> userIDs, Timestamp dt);

    
    List<WResult> remoteValveControl(WS_Operator op, int mode, List<Integer> userIDs);

    
    List<WResult> remoteCloseValveAtTime(WS_Operator op, List<Integer> userIDs, Timestamp AtTime);

    
    List<WResult> checkMeterTime(WS_Operator op, List<Integer> userIDs);

    
    WResult remoteMeterUserSet(WS_Operator op, int userID, int Sum, int cur, int pre, byte mode);

    
    WResult remoteCharge(WS_Operator op, int userID, double money);

    
    WResult icCharge(WS_Operator op, int userID, double money, String cardID, String IcData, WS_IcData icdata);

    
    WResult readIC(WS_Operator op, String cardID, String IcData, WS_IcData icdata);

    
    WResult remoteChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schdt);

    
    WResult icChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schdt, String cardID, String IcData, WS_IcData icdata);

    
    WResult batchChangePrice(WS_Operator op, int saleID, int NewSaleID, boolean isAtTime, Timestamp schdt);

    
    WResult reSendCmd(WS_Operator op, String sismsid);

    
    WResult reWriteIC(WS_Operator op, String sismsid, String cardid, WS_IcData icdata);

    
    WResult meterJieSuan(WS_Operator op, int userID, int MeterRead);

    
    WResult changeMeter(WS_Operator op, Integer userID, String NewmeterID, double Updatemoney, double Chargemoney, String cardid, WS_IcData icdata);

    
    List<WResult> changeCbr(WS_Operator op, List<Integer> userIDs, int cbr);

    
    List<WResult> changeOverdraftStyle(WS_Operator op, List<Integer> userIDs, int overdraftStyle);
    


    // 资料业务处理
    WResult updateUserInfo(WS_Operator op, WS_UserInfo usi);

    
    WResult newArea(WS_Operator op, String area);

    // C#中第二个参数前有ref关键字 
    WResult updatePrice(WS_Operator op, WS_Price Pr);

    
    WResult importMeter(WS_Operator op, List<WS_Meter> MTS);

    
    WResult changePassword(WS_Operator op, String NewPsw);

    
    WResult newOperator(WS_Operator op, WS_Operator nop);
    

    WResult operatorResetPassword(WS_Operator op, WS_Operator target);

    
    WResult importMeterFile(WS_Operator op, ExcelFile efile);

    
    WResult downloadMeterTemplate(WS_Operator op,ExcelFile exfile);
}
