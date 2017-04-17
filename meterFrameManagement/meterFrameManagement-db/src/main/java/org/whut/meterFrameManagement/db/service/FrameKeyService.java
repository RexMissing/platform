package org.whut.meterFrameManagement.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.db.mapper.FrameKeyMapper;

/**
 * Created by chenfu on 2017/3/30.
 */
@Component
public class FrameKeyService {
    @Autowired
    private FrameKeyMapper frameKeyMapper;

    public String getOldKey(String meterID) {
        return frameKeyMapper.getOldKey(meterID);
    }

    public void updateNewKey(String meterID, String newKey) {
        frameKeyMapper.updateNewKey(meterID, newKey);
    }

    public String getNewKey(String meterID) {
        return frameKeyMapper.getNewKey(meterID);
    }

    public void changeKey(String meterID, String oldKey, String newKey) {
        frameKeyMapper.changeKey(meterID,oldKey,newKey);
    }
}
