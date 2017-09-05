package org.whut.dataManagement.business.meterAnalysis.entity;

import java.util.Date;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.whut.platform.fundamental.util.json.CustomDateDeserialize;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public class MeterAnalysis {
    private long id;
    private String fdepartment;
    private String fanalysor;
    private String fcustomer;
    private String fmetername;
    private String fmetercode;
    private String fvalvename;
    private int freportmisfune;
    private int fconfirmmisfune;
    private String fmeterreading;
    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date fdatetime;
    private int isdelete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }

    public String getFdepartment() {
        return fdepartment;
    }

    public void setFdepartment(String fdepartment) {
        this.fdepartment = fdepartment;
    }

    public String getFanalysor() {
        return fanalysor;
    }

    public void setFanalysor(String fanalysor) {
        this.fanalysor = fanalysor;
    }

    public String getFcustomer() {
        return fcustomer;
    }

    public void setFcustomer(String fcustomer) {
        this.fcustomer = fcustomer;
    }

    public String getFmetername() {
        return fmetername;
    }

    public void setFmetername(String fmetername) {
        this.fmetername = fmetername;
    }

    public String getFmetercode() {
        return fmetercode;
    }

    public void setFmetercode(String fmetercode) {
        this.fmetercode = fmetercode;
    }

    public String getFvalvename() {
        return fvalvename;
    }

    public void setFvalvename(String fvalvename) {
        this.fvalvename = fvalvename;
    }

    public int getFreportmisfune() {
        return freportmisfune;
    }

    public void setFreportmisfune(int freportmisfune) {
        this.freportmisfune = freportmisfune;
    }

    public int getFconfirmmisfune() {
        return fconfirmmisfune;
    }

    public void setFconfirmmisfune(int fconfirmmisfune) {
        this.fconfirmmisfune = fconfirmmisfune;
    }

    public String getFmeterreading() {
        return fmeterreading;
    }

    public void setFmeterreading(String fmeterreading) {
        this.fmeterreading = fmeterreading;
    }

    public Date getFdatetime() {
        return fdatetime;
    }

    public void setFdatetime(Date fdatetime) {
        this.fdatetime = fdatetime;
    }
}
