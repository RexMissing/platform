package org.whut.meterFrameManagement.db.mapper;

import org.whut.meterFrameManagement.db.entity.TSend;

import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
public interface TSendMapper {
    public List<TSend> getsendFrame(String meterID);
    public void addSendFrame(TSend send);
    public void deleteSendFrame(int id);
}
