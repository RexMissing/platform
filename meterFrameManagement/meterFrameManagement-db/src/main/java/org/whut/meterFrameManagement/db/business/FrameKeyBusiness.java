package org.whut.meterFrameManagement.db.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.db.service.FrameKeyService;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 * Created by chenfu on 2017/3/30.
 */
@Component
public class FrameKeyBusiness {

    @Autowired
    private FrameKeyService frameKeyService;

    public String getOldKey(String meterID) {
        return frameKeyService.getOldKey(meterID);
    }

    public String getNewKey(String meterID) {
        return frameKeyService.getNewKey(meterID);
    }

    public void updateNewKey(String meterID, String newKey) {
        frameKeyService.updateNewKey(meterID, newKey);
    }
    public void changeKey(String meterID, String oldKey, String newKey) {
        frameKeyService.changeKey(meterID,oldKey,newKey);
    }
}
