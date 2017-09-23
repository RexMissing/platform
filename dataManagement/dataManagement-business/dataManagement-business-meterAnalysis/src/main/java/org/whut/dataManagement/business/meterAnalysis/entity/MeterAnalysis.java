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
    private String fmetername;
    private String fmetercode;
    private String fvalvename;
    private String fanalysor;
    private String fcustomer;
    private String freportmisfune;
    private String fconfirmmisfune;
    private String fmisfunedescrib;
    private String fmeterreading;
    private String felecdisplay;
//    @JsonDeserialize(using = CustomDateDeserialize.class)
    private Date fdatetime;
    private int isdelete;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFdepartment() {
        return fdepartment;
    }

    public void setFdepartment(String fdepartment) {
        this.fdepartment = fdepartment;
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

    public String getFreportmisfune() {
        return freportmisfune;
    }

    public void setFreportmisfune(String freportmisfune) {
        this.freportmisfune = freportmisfune;
    }

    public String getFconfirmmisfune() {
        return fconfirmmisfune;
    }

    public void setFconfirmmisfune(String fconfirmmisfune) {
        this.fconfirmmisfune = fconfirmmisfune;
    }

    public String getFmisfunedescrib() {
        return fmisfunedescrib;
    }

    public void setFmisfunedescrib(String fmisfunedescrib) {
        this.fmisfunedescrib = fmisfunedescrib;
    }

    public String getFmeterreading() {
        return fmeterreading;
    }

    public void setFmeterreading(String fmeterreading) {
        this.fmeterreading = fmeterreading;
    }

    public String getFelecdisplay() {
        return felecdisplay;
    }

    public void setFelecdisplay(String felecdisplay) {
        this.felecdisplay = felecdisplay;
    }

    public Date getFdatetime() {
        return fdatetime;
    }

    public void setFdatetime(Date fdatetime) {
        this.fdatetime = fdatetime;
    }

    public int getIsdelete() {
        return isdelete;
    }

    public void setIsdelete(int isdelete) {
        this.isdelete = isdelete;
    }
}
