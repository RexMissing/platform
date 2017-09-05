package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnCustomAndNameMapper;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
@DataSource("NetBarCode")
public class ReturnCustomAndNameService {
    @Autowired
    private ReturnCustomAndNameMapper returnCustomAndNameMapper;
    public List<Map<String,Object>> findCustomAndName(Map<String,Object> condition)
    {
        return returnCustomAndNameMapper.findCustomAndName(condition);
    }
}
