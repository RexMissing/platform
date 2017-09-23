package org.whut.dataManagement.business.updateRecord.entity;



import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.whut.platform.fundamental.util.json.CustomDateDeserialize;

import java.util.Date;

/**
 * Created by Administrator on 2017/8/14 0014.
 */
public class UpdateRecord {
    private int id;
    private String dataname;
    @JsonDeserialize(using=CustomDateDeserialize.class)
    private Date updatedate;
    private String updateoperator;
    private String updatename;
    private String updatekey;
    private String updatebefore;
    private String updateafter;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUpdatekey() {
        return updatekey;
    }

    public void setUpdatekey(String updatekey) {
        this.updatekey = updatekey;
    }

    public String getDataname() {
        return dataname;
    }

    public void setDataname(String dataname) {
        this.dataname = dataname;
    }

    public Date getUpdatedate() {
        return updatedate;
    }

    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    public String getUpdateoperator() {
        return updateoperator;
    }

    public void setUpdateoperator(String updateoperator) {
        this.updateoperator = updateoperator;
    }

    public String getUpdatename() {
        return updatename;
    }

    public void setUpdatename(String updatename) {
        this.updatename = updatename;
    }

    public String getUpdatebefore() {
        return updatebefore;
    }

    public void setUpdatebefore(String updatebefore) {
        this.updatebefore = updatebefore;
    }

    public String getUpdateafter() {
        return updateafter;
    }

    public void setUpdateafter(String updateafter) {
        this.updateafter = updateafter;
    }
}
