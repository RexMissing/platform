package org.whut.meterManagement.smsmeterservice.service_entity;

/**
 * Created by chenfu on 2016/12/22.
 */
public class ExcelFile {

    private String f_name;
    private int f_len;
    private byte[] f_stream;

    public String getF_name() {
        return f_name;
    }

    public void setF_name(String f_name) {
        this.f_name = f_name;
    }

    public int getF_len() {
        return f_len;
    }

    public void setF_len(int f_len) {
        this.f_len = f_len;
    }

    public byte[] getF_stream() {
        return f_stream;
    }

    public void setF_stream(byte[] f_stream) {
        this.f_stream = f_stream;
    }

}
