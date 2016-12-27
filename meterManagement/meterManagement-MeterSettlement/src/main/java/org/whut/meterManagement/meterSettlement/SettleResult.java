package org.whut.meterManagement.meterSettlement;

/**
 * Created by chenfu on 2016/12/27.
 */
public class SettleResult {
    private boolean result;
    private String description;

    public SettleResult(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
