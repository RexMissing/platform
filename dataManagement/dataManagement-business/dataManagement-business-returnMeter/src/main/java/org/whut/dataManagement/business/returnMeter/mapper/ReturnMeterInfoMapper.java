package org.whut.dataManagement.business.returnMeter.mapper;

import org.whut.dataManagement.business.returnMeter.entity.ReturnMeterInfo;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public interface ReturnMeterInfoMapper extends AbstractMapper<ReturnMeterInfo> {
    public List<ReturnMeterInfo> getlist();
}
