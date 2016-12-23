package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.sqldatalib.StdUtils;


/**
 * Created by chenfu on 2016/12/22.
 */
/// <summary>
/// 操作人员
/// </summary>
public class WS_Operator {

    public String opID;
    
    public String opMd5; 
    
    public String opName; 
    
    public String opMemo;

    public String getOpID() {
        return opID;
    }

    public void setOpID(String opID) {
        this.opID = opID;
    }

    public String getOpMd5() {
        return opMd5;
    }

    public void setOpMd5(String opMd5) {
        this.opMd5 = opMd5;
    }

    public String getOpName() {
        return opName;
    }

    public void setOpName(String opName) {
        this.opName = opName;
    }

    public String getOpMemo() {
        return opMemo;
    }

    public void setOpMemo(String opMemo) {
        this.opMemo = opMemo;
    }

    public boolean addOrModify(String parentID)
    {
        Object o = StdUtils.getSqlh().executeScalar("select count(*) from TOperator where FOperatorID='" + opID + "'");
        int cnt = Integer.parseInt(o.toString());
        if (cnt>0)
        {
            int n = StdUtils.getSqlh().executeNonQuery("update TOperator set FPassword='" + opMd5 + "',FMemo='" + opMemo + "' where FOperatorID='" + opID + "'");
            if (n == 0)
            {
                return false;
            }
        }
        else
        {
            int n = StdUtils.getSqlh().executeNonQuery("insert into TOperator(FOperatorID,FOperatorName,FPassword,FMemo,FParent) values('" + opID + "','" + opName + "','" + opMd5 + "','" + opMemo + "','" + parentID + "')");
            if (n == 0)
            {
                return false;
            }
        }
        return true;
    }

    public static boolean newOpertor(WS_Operator op,String parentID, StringBuffer er)
    {
        er = new StringBuffer();
        //判断opid是否存在
        Object o =StdUtils.getSqlh().executeScalar("select count(*) from TOperator where FOperatorID='" + op.opID + "'");
        int cnt = Integer.parseInt(o.toString());
        if (cnt>0)
        {
            er.append("操作员编号重复");
            return false;
        }
        String role = "";
        if (parentID.equals("admin"))
        {
            role = "admin";
        }
        cnt = StdUtils.getSqlh().executeNonQuery("insert into TOperator(FOperatorID,FOperatorName,FPassword,FRole,FMemo,FParent) values('" + op.opID + "','" + op.opName + "','" + op.opMd5 + "','" + role + "','" + op.opMemo + "','" + parentID + "')");
        if (cnt==0)
        {
            er.append("更新数据库失败");
            return false;
        }
        return true;
    }

    public boolean changePassword(String psw)
    {
        int n = StdUtils.getSqlh().executeNonQuery("update TOperator set FPassword='" + psw + "' where FOperatorID='" + opID + "'");
        if (n == 0)
        {
            return false;
        }

        opMd5 = psw;
        return true;
    }

    public boolean defaultPassword()
    {
        int n = StdUtils.getSqlh().executeNonQuery("update TOperator set FPassword='" + MYCONST.defaultkey + "' where FOperatorID='" + opID + "'");
        if (n==0)
        {
            return false;
        }
        opMd5 = MYCONST.defaultkey;
        return true;
    }
    
}
