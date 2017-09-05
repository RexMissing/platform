package org.whut.dataManagement.business.meterAnalysis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterAnalysis.mapper.ReturnValveAndNameMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
@DataSource("NetBarCode")
public class ReturnValveAndNameService {
    @Autowired
    private ReturnValveAndNameMapper returnValveAndNameMapper;
    public List<Map<String,Object>> findValveAndName(Map<String,Object> condition)
    {
        return returnValveAndNameMapper.findValveAndName(condition);
    }
}
