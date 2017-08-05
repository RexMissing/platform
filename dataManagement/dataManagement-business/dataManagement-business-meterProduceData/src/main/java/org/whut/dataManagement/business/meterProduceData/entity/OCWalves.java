package org.whut.dataManagement.business.meterProduceData.entity;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
public class OCWalves {
    private String fvalvecode;
    private String fpressure;
    private String frecord;
    private String fstate;

    public String getFvalvecode() {
        return fvalvecode;
    }

    public void setFvalvecode(String fvalvecode) {
        this.fvalvecode = fvalvecode;
    }

    public String getFpressure() {
        return fpressure;
    }

    public void setFpressure(String fpressure) {
        this.fpressure = fpressure;
    }

    public String getFrecord() {
        return frecord;
    }

    public void setFrecord(String frecord) {
        this.frecord = frecord;
    }

    public String getFstate() {
        return fstate;
    }

    public void setFstate(String fstate) {
        this.fstate = fstate;
    }

    @Override
    public String toString() {
        return "OCWalves{" +
                "fvalvecode='" + fvalvecode + '\'' +
                ", fpressure='" + fpressure + '\'' +
                ", frecord='" + frecord + '\'' +
                ", fstate='" + fstate + '\'' +
                '}';
    }
}
