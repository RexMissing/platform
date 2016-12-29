package org.whut.meterManagement.smsmeterservice.service_entity;

/**
 * Created by chenfu on 2016/12/28.
 */
public class QCondition {

    private int type;
    private String value;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean checkSelf() {
        boolean rst = true;
        switch (type) {
            case 0:
                try {
                    int uid = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    rst = false;
                }
                break;
            case 5:
                if (value.length() != 13) {
                    rst = false;
                }
                break;
            default:
                break;
        }
        return rst;
    }

}
