package org.whut.meterFrameManagement.db.business;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.whut.meterFrameManagement.db.entity.TReceive;
import org.whut.meterFrameManagement.db.service.TReceiveService;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/29.
 */
@Component
public class ReceiveFrameBusiness {
    @Autowired
    private TReceiveService tReceiveService;
    public List<TReceive> getAllReceiveFrame(){
        return tReceiveService.getAllReceiveFrame();
    }
    public List<TReceive> getReceiveFrame(String meterID){
        return tReceiveService.getReceiveFrame(meterID);
    }
    public void addReceiveFrame(String meterID,String receiveString,Timestamp timestamp){
        tReceiveService.addReceiveFrame(meterID,receiveString,timestamp);
    }
    public void deleteReceiveFrame(int id){
        tReceiveService.deleteReceiveFrame(id);
    }
}
