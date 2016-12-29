package org.whut.meterManagement.smsmeterservice.service_entity;

import org.whut.meterManagement.sqldatalib.StdUtils;

/**
 * Created by chenfu on 2016/12/22.
 */
/// <summary>
/// IC卡数据对象
/// </summary>
public class WS_IcData {

    private String cardID;
    private String dataStr;
    private String cmdStr;

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
        this.cardID = cardID;
    }

    public String getDataStr() {
        return dataStr;
    }

    public void setDataStr(String dataStr) {
        this.dataStr = dataStr;
    }

    public String getCmdStr() {
        return cmdStr;
    }

    public void setCmdStr(String cmdStr) {
        this.cmdStr = cmdStr;
    }

    public WS_IcData() {}

    /// <summary>
    /// 根据命令字符串，生成卡对象
    /// </summary>
    /// <param name="cardID"></param>
    /// <param name="sms"></param>
    /// <returns></returns>
    public static WS_IcData buildWrite(String cardID, String sms, WS_IcData ic)
    {
        //WS_IcData ic = new WS_IcData();
//        IC.cardID = cardID;
//        IC.cmdStr = sms;
//        IC.dataStr = "05010101000000000000000000000000";
//        IC.dataStr += "68" + sms.substring(1);
//        IC.dataStr = IC.dataStr.PadRight(448, '0');
        
        ic.setCardID(cardID);
        ic.setCmdStr(sms);
        String dataStr = "05010101000000000000000000000000";
        dataStr += "68" + sms.substring(1);
        while (dataStr.length() < 448) {
            dataStr += "0";
        }
        ic.setDataStr(dataStr);

        return ic;
    }

    public static WS_IcData buildWrite(String cardID, String[] sms, WS_IcData ic)
    {
        //WS_IcData ic = new WS_IcData();
//        IC.cardID = cardID;
//        IC.cmdStr = sms.toString();
//        IC.dataStr = "05010" + sms.length + "01000000000000000000000000";
//        for (int i = 0; i < sms.Length; i++)
//        {
//            IC.dataStr += "68" + sms[i].Substring(1);
//        }
//        IC.dataStr = IC.dataStr.PadRight(448, '0');
        
        ic.setCardID(cardID);
        ic.setCmdStr(sms.toString());
        String dataStr = "05010" + sms.length + "01000000000000000000000000";
        for (int i = 0; i < sms.length; i++)
        {
            dataStr += "68" + sms[i].substring(1);
        }
        while (dataStr.length() < 448) {
            dataStr += "0";
        }
        ic.setDataStr(dataStr);
        
        return ic;
    }

    /// <summary>
    /// 根据卡数据区字符串，执行读卡逻辑
    /// </summary>
    /// <param name="icdata">卡数据区</param>
    /// <param name="IC">ICData对象</param>
    /// <param name="er"></param>
    /// <returns></returns>
    public static boolean readFromDataStr(String icData, String cardID, WS_IcData IC, StringBuffer sb)
    {
        //IC = null;

        if ((!icData.substring(0, 2).equals("05")) && (!icData.substring(0, 2).equals("DD")))
        {
            sb.append("非用户卡");
            return false;
        }
        IC.setCardID(cardID);
        IC.setDataStr(icData);
        if (icData.substring(2, 4).equals("00"))
        {
            //表具回传，将数据还原为通讯帧，加入到TSms_RECEIVE表中
            //IC.cmdStr = "h40" + icData.Substring(32, 128) + "16";
            IC.setCmdStr("h40" + icData.substring(32, 160) + "16");
            if (!StdUtils.getSqlh().executeExsit("select * from TIC_RECEIVE where FCardID='"
                    + IC.getCardID() + "' and FCmdStr='" + IC.getCmdStr() + "'"))
            {
                StdUtils.getSqlh().executeNonQuery("insert TIC_RECEIVE(FCardID,FCmdStr) values('" + cardID + "','" + IC.getCmdStr() + "')");
            }
        }
        if (icData.substring(2, 4).equals("01"))
        {
            //发送帧
        }
        return true;
    }

}
