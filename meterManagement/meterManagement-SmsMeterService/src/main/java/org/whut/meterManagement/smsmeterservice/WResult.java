package org.whut.meterManagement.smsmeterservice;

import org.whut.meterManagement.sqldatalib.StdUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by chenfu on 2016/12/22.
 */
public class WResult {

    public boolean bResult;
    
    public StringBuffer erDes;
    
    public int ID;

    public boolean isBResult() {
        return bResult;
    }

    public void setBResult(boolean bResult) {
        this.bResult = bResult;
    }

    public StringBuffer getErDes() {
        return erDes;
    }

    public void setErDes(StringBuffer erDes) {
        this.erDes = erDes;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public WResult() {
    }

    public WResult(int id) {
        setID(id);
    }

    public WResult(boolean bResult) {
        setBResult(bResult);
    }

    public WResult(int ID, boolean bResult) {
        setID(ID);
        setBResult(bResult);
    }

    //验证普通操作员
    public boolean VerOP(WS_Operator op) {
        //00640072003000770073007300400050
        if (op.getOpID().equals("admin") && op.getOpMd5() == MYCONST.adminkey) {
            op.setOpName("超级管理员");
            bResult = true;
            return true;
        }
        ResultSet rs = StdUtils.getSqlh().executeQuery("select * from TOperator where FOperatorID='" + op.getOpID() + "'");
        try {
            if (rs.getRow() > 0) {
                //验证密码
                if (op.getOpMd5().toUpperCase().equals(rs.getString("FPassword").toUpperCase())) {
                    op.setOpName(rs.getString("FOperatorName"));
                    op.setOpMemo(rs.getString("FMemo"));
                    bResult = true;
                } else {
                    erDes.append("操作员验证失败");
                    bResult = false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bResult;
    }
        
}


