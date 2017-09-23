package org.whut.dataManagement.business.enumTable.entity;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public class EnumTable {
    private long id;
    private String fenumtype;
    private String fenumname;
    private String fenumvalue;
    private String fdescription;

    public String getFdescription() {
        return fdescription;
    }

    public void setFdescription(String fdescription) {
        this.fdescription = fdescription;
    }

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

    public String getFenumtype() {
        return fenumtype;
    }

    public void setFenumtype(String fenumtype) {
        this.fenumtype = fenumtype;
    }

    public String getFenumname() {
        return fenumname;
    }

    public void setFenumname(String fenumname) {
        this.fenumname = fenumname;
    }

    public String getFenumvalue() {
        return fenumvalue;
    }

    public void setFenumvalue(String fenumvalue) {
        this.fenumvalue = fenumvalue;
    }
}
