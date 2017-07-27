package org.whut.meterFrameManagement.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.whut.meterFrameManagement.db.entity.TReceive;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
public interface TReceiveMapper {
    public List<TReceive> getAllReceiveFrame();
    public List<TReceive> getReceiveFrame(String meterID);
    public void addReceiveFrame(@Param("meterID")String meterID,@Param("receiveString")String receiveString,@Param("timestamp")Timestamp timestamp);
    public void deleteReceiveFrame(int id);
}
