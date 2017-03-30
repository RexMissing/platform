package org.whut.meterFrameManagement.db.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.db.service.FrameKeyService;

/**
 * Created by chenfu on 2017/3/30.
 */
@Component
public class FrameKeyBusiness {
    @Autowired
    private FrameKeyService frameKeyService;

    public void add2DB(String meterID, String keyStr) {
        frameKeyService.add2DB(meterID, keyStr);
    }

    public String getKey(String meterID) {
        return frameKeyService.getKey(meterID);
    }

    public void updatekey(String meterID, String newKey) {
        frameKeyService.updatekey(meterID, newKey);
    }


}
