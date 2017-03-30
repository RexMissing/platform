package org.whut.meterFrameManagement.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.db.entity.TSend;
import org.whut.meterFrameManagement.db.mapper.TSendMapper;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
@Component
public class TSendService {
    @Autowired
    private TSendMapper tSendMapper;

    public List<TSend> getSendFrame(String meterID){
        return tSendMapper.getSendFrame(meterID);
    }
    public int getLastFrameID(String meterID, int funCode) {
        return tSendMapper.getLastFrameID(meterID,funCode);
    }
    public void addSendFrame(TSend tSend){
        tSendMapper.addSendFrame(tSend);
    }
    public void deleteSendFrame(int id){
        tSendMapper.deleteSendFrame(id);
    }
    public void updateSent(int id,boolean sent,Timestamp timestamp) {
        tSendMapper.updateSent(id,sent,timestamp);
    }
}
