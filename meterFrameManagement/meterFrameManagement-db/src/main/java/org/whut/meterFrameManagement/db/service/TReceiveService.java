package org.whut.meterFrameManagement.db.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.db.entity.TReceive;
import org.whut.meterFrameManagement.db.mapper.TReceiveMapper;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
@Component
public class TReceiveService {
    @Autowired
    private TReceiveMapper tReceiveMapper;
    public List<TReceive> getAllReceiveFrame() {
        return tReceiveMapper.getAllReceiveFrame();
    }
    public List<TReceive> getReceiveFrame(String meterID){
        return tReceiveMapper.getReceiveFrame(meterID);
    }
    public void addReceiveFrame(String meterID,String receiveString,Timestamp timestamp){
        tReceiveMapper.addReceiveFrame(meterID,receiveString,timestamp);
    }
    public void deleteReceiveFrame(int id){
        tReceiveMapper.deleteReceiveFrame(id);
    }


}
