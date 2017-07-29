package org.whut.dataManagement.business.returnMeter.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.whut.platform.fundamental.util.json.CustomDateDeserialize;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/24 0024.
 */
public class ReturnMeter {
    private String fmetercode;
    private String fcustomer;
    private String fmetername;
    private int fquantity;
    private String frinvono;
    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date fdatetime;



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
    public String getFrinvono() {
        return frinvono;
    }

    public void setFrinvono(String frinvono) {
        this.frinvono = frinvono;
    }

    public Date getFdatetime() {
        return fdatetime;
    }

    public void setFdatetime(Date fdatetime) {
        this.fdatetime = fdatetime;
    }

}
