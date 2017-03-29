package org.whut.meterFrameManagement.db.mapper;

import org.apache.ibatis.annotations.Param;
import org.whut.meterFrameManagement.db.entity.TSend;

import java.sql.Timestamp;
import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
public interface TSendMapper {
    public List<TSend> getSendFrame(String meterID);
    public void addSendFrame(@Param("meterID")String meterID,@Param("frameString")String frameString,@Param("timestamp")Timestamp timestamp);
    public void deleteSendFrame(int id);
}
