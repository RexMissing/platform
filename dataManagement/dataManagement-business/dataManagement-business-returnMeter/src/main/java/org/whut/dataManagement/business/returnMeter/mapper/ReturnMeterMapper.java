package org.whut.dataManagement.business.returnMeter.mapper;

import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/24 0024.
 */
public interface ReturnMeterMapper extends AbstractMapper<ReturnMeter> {
    public List<ReturnMeter> getlist();
}
