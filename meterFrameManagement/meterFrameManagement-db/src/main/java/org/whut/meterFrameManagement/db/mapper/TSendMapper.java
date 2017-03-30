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
    public Integer getLastFrameID(@Param("meterID")String meterID, @Param("funCode")int funCode);
    public void addSendFrame(@Param("tSend")TSend tSend);
    public void deleteSendFrame(int id);
    void updateSent(@Param("id")int id,@Param("sent")boolean sent,@Param("timestamp")Timestamp timestamp);
}
