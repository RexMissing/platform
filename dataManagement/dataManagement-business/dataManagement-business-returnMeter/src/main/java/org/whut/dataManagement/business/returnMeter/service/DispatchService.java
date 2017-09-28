package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterMapper;

import java.rmi.MarshalledObject;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/27 0027.
 */
@DataSource("lyims0707")
public class DispatchService
{
    @Autowired
    private ReturnMeterMapper returnMeterMapper;
    public List<Map<String,Object>> findByDispatch(Map<String,Object> condition)
    {
        return returnMeterMapper.findByDispatch(condition);
    }
}
