package org.whut.dataManagement.business.inspection.entity;

import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.whut.platform.fundamental.util.json.CustomDateDeserialize;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/10.
 */
public class Inspection {
    private int id;
    private String finspectionbatch;
    private String fmetercode;
    private String fmetername;
    private String fdepartment;
    private String foperator;
    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date fdate;
    private String fexterior;
    private double fvoltage;
    private double fgas;
    private String fmisoperation;
    private String fgascontrol;
    private String fdatakeep;
    private double fgasaccumulated;
    private double fexteriorprotect;
    private String fdefend;
    private String fmdisturbance;
    private double fmaxelec;
    private String fgastightness;
    private String fairtightness;
    private double fswitcherror;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFinspectionbatch() {
        return finspectionbatch;
    }

    public void setFinspectionbatch(String finspectionbatch) {
        this.finspectionbatch = finspectionbatch;
    }

    public String getFmetercode() {
        return fmetercode;
    }

    public void setFmetercode(String fmetercode) {
        this.fmetercode = fmetercode;
    }

    public String getFmetername() {
        return fmetername;
    }

    public void setFmetername(String fmetername) {
        this.fmetername = fmetername;
    }

    public String getFdepartment() {
        return fdepartment;
    }

    public void setFdepartment(String fdepartment) {
        this.fdepartment = fdepartment;
    }

    public String getFoperator() {
        return foperator;
    }

    public void setFoperator(String foperator) {
        this.foperator = foperator;
    }

    public Date getFdate() {
        return fdate;
    }

    public void setFdate(Date fdate) {
        this.fdate = fdate;
    }

    public String getFexterior() {
        return fexterior;
    }

    public void setFexterior(String fexterior) {
        this.fexterior = fexterior;
    }

    public double getFvoltage() {
        return fvoltage;
    }

    public void setFvoltage(double fvoltage) {
        this.fvoltage = fvoltage;
    }

    public double getFgas() {
        return fgas;
    }

    public void setFgas(double fgas) {
        this.fgas = fgas;
    }

    public String getFmisoperation() {
        return fmisoperation;
    }

    public void setFmisoperation(String fmisoperation) {
        this.fmisoperation = fmisoperation;
    }

    public String getFgascontrol() {
        return fgascontrol;
    }

    public void setFgascontrol(String fgascontrol) {
        this.fgascontrol = fgascontrol;
    }

    public String getFdatakeep() {
        return fdatakeep;
    }

    public void setFdatakeep(String fdatakeep) {
        this.fdatakeep = fdatakeep;
    }

    public double getFgasaccumulated() {
        return fgasaccumulated;
    }

    public void setFgasaccumulated(double fgasaccumulated) {
        this.fgasaccumulated = fgasaccumulated;
    }

    public double getFexteriorprotect() {
        return fexteriorprotect;
    }

    public void setFexteriorprotect(double fexteriorprotect) {
        this.fexteriorprotect = fexteriorprotect;
    }

    public String getFdefend() {
        return fdefend;
    }

    public void setFdefend(String fdefend) {
        this.fdefend = fdefend;
    }

    public String getFmdisturbance() {
        return fmdisturbance;
    }

    public void setFmdisturbance(String fmdisturbance) {
        this.fmdisturbance = fmdisturbance;
    }

    public double getFmaxelec() {
        return fmaxelec;
    }

    public void setFmaxelec(double fmaxelec) {
        this.fmaxelec = fmaxelec;
    }

    public String getFgastightness() {
        return fgastightness;
    }

    public void setFgastightness(String fgastightness) {
        this.fgastightness = fgastightness;
    }

    public String getFairtightness() {
        return fairtightness;
    }

    public void setFairtightness(String fairtightness) {
        this.fairtightness = fairtightness;
    }

    public double getFswitcherror() {
        return fswitcherror;
    }

    public void setFswitcherror(double fswitcherror) {
        this.fswitcherror = fswitcherror;
    }
}
