package org.whut.dataManagement.business.userFunctionRole.entity;

/**
 * Created by Administrator on 2017/7/25.
 */
public class UserFuncRole {
    private int userID;
    private String userName;
    private String departNo;
    private String departName;
    private int funcRole;
    private String funcRoleName;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartNo() {
        return departNo;
    }

    public void setDepartNo(String departNo) {
        this.departNo = departNo;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public int getFuncRole() {
        return funcRole;
    }

    public void setFuncRole(int funcRole) {
        this.funcRole = funcRole;
    }

    public String getFuncRoleName() {
        return funcRoleName;
    }

    public void setFuncRoleName(String funcRoleName) {
        this.funcRoleName = funcRoleName;
    }
}
