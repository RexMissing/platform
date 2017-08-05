package org.whut.dataManagement.business.returnMeter.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.whut.platform.fundamental.util.json.CustomDateDeserialize;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public class ReturnMeterInfo {
    private String fmetercode;
    private String fcustomer;
    private String fmetername;
    private int fquantity;
    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date fdatetime;
    private String foperator;

    public String getFoperator() {
        return foperator;
    }

    public void setFoperator(String foperator) {
        this.foperator = foperator;
    }

    public String getFmetercode() {
        return fmetercode;
    }

    public void setFmetercode(String fmetercode) {
        this.fmetercode = fmetercode;
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

    public int getFquantity() {
        return fquantity;
    }

    public void setFquantity(int fquantity) {
        this.fquantity = fquantity;
    }

    public Date getFdatetime() {
        return fdatetime;
    }

    public void setFdatetime(Date fdatetime) {
        this.fdatetime = fdatetime;
    }
}
