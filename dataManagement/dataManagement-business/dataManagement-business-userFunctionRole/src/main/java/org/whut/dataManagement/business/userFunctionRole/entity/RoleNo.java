package org.whut.dataManagement.business.userFunctionRole.entity;

/**
 * Created by Administrator on 2017/8/1.
 */
public class RoleNo {
    private long userid;
    private int funcRole;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public int getFuncRole() {
        return funcRole;
    }

    public void setFuncRole(int funcRole) {
        this.funcRole = funcRole;
    }
}
