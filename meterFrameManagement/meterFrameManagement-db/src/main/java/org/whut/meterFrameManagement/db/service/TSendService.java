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

    public TSendMapper gettSendMapper() {
        return tSendMapper;
    }

    public void settSendMapper(TSendMapper tSendMapper) {
        this.tSendMapper = tSendMapper;
    }

    public List<TSend> getSendFrame(String meterID){
        return tSendMapper.getSendFrame(meterID);
    }
    public void addSendFrame(String meterID,String frameString,Timestamp timestamp){
        tSendMapper.addSendFrame(meterID,frameString,timestamp);
    }
    public void deleteSendFrame(int id){
        tSendMapper.deleteSendFrame(id);
    }
}
