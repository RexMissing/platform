package org.whut.meterFrameManagement.communicationframe.receive;

/**
 * Created by zhang_minzhong on 2017/2/28.
 */
public class FunCodeFrameId {
    private String funCode;
    private int frameId;
    private String successOrFail;

    public int getFrameId() {
        return frameId;
    }

    public void setFrameId(int frameId) {
        this.frameId = frameId;
    }

    public String getFunCode() {
        return funCode;
    }

    public void setFunCode(String funCode) {
        this.funCode = funCode;
    }

    public String getSuccessOrFail() {
        return successOrFail;
    }

    public void setSuccessOrFail(String successOrFail) {
        this.successOrFail = successOrFail;
    }
}
