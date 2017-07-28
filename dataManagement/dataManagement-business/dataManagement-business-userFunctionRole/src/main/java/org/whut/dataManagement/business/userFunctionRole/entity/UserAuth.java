package org.whut.dataManagement.business.userFunctionRole.entity;

/**
 * Created by Administrator on 2017/7/28.
 */
public class UserAuth{
    private long id;
    private long userid;
    private long authid;
    private String username;
    private String aythorityName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getAuthid() {
        return authid;
    }

    public void setAuthid(long authid) {
        this.authid = authid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAythorityName() {
        return aythorityName;
    }

    public void setAythorityName(String aythorityName) {
        this.aythorityName = aythorityName;
    }
}
