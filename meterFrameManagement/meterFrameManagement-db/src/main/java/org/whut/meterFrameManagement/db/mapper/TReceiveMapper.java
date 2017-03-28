package org.whut.meterFrameManagement.db.mapper;

import org.whut.meterFrameManagement.db.entity.TReceive;

import java.util.List;

/**
 * Created by zhang_minzhong on 2017/3/27.
 */
public interface TReceiveMapper {
    public List<TReceive> getReceiveFrame(String meterID);
    public void addReceiveFrame(TReceive tReceive);
    public void deleteReceiveFrame(int id);
}
