package org.whut.dataManagement.business.returnMeter.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.whut.platform.fundamental.util.json.CustomDateDeserialize;

import java.util.Date;

/**
 * Created by Administrator on 2017/7/24 0024.
 */
public class ReturnMeter {
    private long id;
    private  String freturnbatch;
    private String fmetercode;
    private String fvalvecode;
    private String fcustomer;
    private String fmetername;
    private String fmeterdirection;
    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date fdatetime;
    private String foperator;
    private String freportmisfune;
    private String fmeterreading;

    public String getFmeterreading() {
        return fmeterreading;
    }

    public void setFmeterreading(String fmeterreading) {
        this.fmeterreading = fmeterreading;
    }

    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date fproducetime;
    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date fsaletime;
    private String fpremaintain;    //现场维修人员

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFreturnbatch() {
        return freturnbatch;
    }

    public void setFreturnbatch(String freturnbatch) {
        this.freturnbatch = freturnbatch;
    }

    public String getFmetercode() {
        return fmetercode;
    }

    public void setFmetercode(String fmetercode) {
        this.fmetercode = fmetercode;
    }

    public String getFvalvecode() {
        return fvalvecode;
    }

    public void setFvalvecode(String fvalvecode) {
        this.fvalvecode = fvalvecode;
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

    public String getFmeterdirection() {
        return fmeterdirection;
    }

    public void setFmeterdirection(String fmeterdirection) {
        this.fmeterdirection = fmeterdirection;
    }

    public Date getFdatetime() {
        return fdatetime;
    }

    public void setFdatetime(Date fdatetime) {
        this.fdatetime = fdatetime;
    }

    public String getFoperator() {
        return foperator;
    }

    public void setFoperator(String foperator) {
        this.foperator = foperator;
    }

    public String getFreportmisfune() {
        return freportmisfune;
    }

    public void setFreportmisfune(String freportmisfune) {
        this.freportmisfune = freportmisfune;
    }

    public Date getFproducetime() {
        return fproducetime;
    }

    public void setFproducetime(Date fproducetime) {
        this.fproducetime = fproducetime;
    }

    public Date getFsaletime() {
        return fsaletime;
    }

    public void setFsaletime(Date fsaletime) {
        this.fsaletime = fsaletime;
    }

    public String getFpremaintain() {
        return fpremaintain;
    }

    public void setFpremaintain(String fpremaintain) {
        this.fpremaintain = fpremaintain;
    }
}
