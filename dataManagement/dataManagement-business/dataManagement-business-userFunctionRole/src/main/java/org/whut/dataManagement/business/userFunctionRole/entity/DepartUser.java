package org.whut.dataManagement.business.userFunctionRole.entity;

/**
 * Created by Administrator on 2017/7/28.
 */
public class DepartUser {
    private long userid;
    private String username;
    private String fname;
    private String sex;
    private String funcroleName;

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getFuncroleName() {
        return funcroleName;
    }

    public void setFuncroleName(String funcroleName) {
        this.funcroleName = funcroleName;
    }
}
