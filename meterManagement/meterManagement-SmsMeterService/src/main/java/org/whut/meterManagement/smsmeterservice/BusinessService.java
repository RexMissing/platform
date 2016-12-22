package org.whut.meterManagement.smsmeterservice;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by chenfu on 2016/12/22.
 */
public interface BusinessService {

    
    WResult UserOpenMeter(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr);

    
    WResult UserOpenIC(WS_Operator op, int userID, String meterID, int strategyID, double money, Timestamp sdt, int cbr, String cardID, int overdraftStyle, WS_IcData icdata);

    
    List<WResult> RemoteReadMeter(WS_Operator op, List<Integer> userIDs);

    
    List<WResult> RemoteReadMeterAtTime(WS_Operator op, List<Integer> userIDs, Timestamp dt);

    
    List<WResult> RemoteValveControl(WS_Operator op, int mode, List<Integer> userIDs);

    
    List<WResult> RemoteCloseValveAtTime(WS_Operator op, List<Integer> userIDs, Timestamp AtTime);

    
    List<WResult> CheckMeterTime(WS_Operator op, List<Integer> userIDs);

    
    WResult RemoteMeterUserSet(WS_Operator op, int userID, int Sum, int cur, int pre, byte mode);

    
    WResult RemoteCharge(WS_Operator op, int userID, double money);

    
    WResult ICCharge(WS_Operator op, int userID, double money, String cardID, String IcData, WS_IcData icdata);

    
    WResult ReadIC(WS_Operator op, String cardID, String IcData, WS_IcData icdata);

    
    WResult RemoteChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schdt);

    
    WResult ICChangePrice(WS_Operator op, int userID, int saleID, boolean isAtTime, Timestamp schdt, String cardID, String IcData, WS_IcData icdata);

    
    WResult BatchChangePrice(WS_Operator op, int saleID, int NewSaleID, boolean isAtTime, Timestamp schdt);

    
    WResult ReSendCmd(WS_Operator op, String sismsid);

    
    WResult ReWriteIC(WS_Operator op, String sismsid, String cardid, WS_IcData icdata);

    
    WResult MeterJieSuan(WS_Operator op, int userID, int MeterRead);

    
    WResult ChangeMeter(WS_Operator op, Integer userID, String NewmeterID, double Updatemoney, double Chargemoney, String cardid, WS_IcData icdata);

    
    List<WResult> ChangeCbr(WS_Operator op, List<Integer> userIDs, int cbr);

    
    List<WResult> ChangeOverdraftStyle(WS_Operator op, List<Integer> userIDs, int overdraftStyle);
    


    // 资料业务处理
    WResult UpdateUserInfo(WS_Operator op, WS_UserInfo usi);

    
    WResult NewArea(WS_Operator op, String area);

    // C#中第二个参数前有ref关键字 
    WResult UpdatePrice(WS_Operator op, WS_Price Pr);

    
    WResult ImportMeter(WS_Operator op, List<WS_Meter> MTS);

    
    WResult ChangePassword(WS_Operator op, String NewPsw);

    
    WResult NewOperator(WS_Operator op, WS_Operator nop);
    

    WResult OperatorResetPassword(WS_Operator op, WS_Operator target);

    
    WResult ImportMeterFile(WS_Operator op, ExcelFile efile);

    
    WResult DownloadMeterTemplate(WS_Operator op,ExcelFile exfile);
}
