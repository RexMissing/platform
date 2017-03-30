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

    public void add2DB(String meterID, String keyStr) {
        frameKeyMapper.add2DB(meterID, keyStr);
    }

    public String getKey(String meterID) {
        return frameKeyMapper.getKey(meterID);
    }

    public void updatekey(String meterID, String newKey) {
        frameKeyMapper.updatekey(meterID, newKey);
    }

}
