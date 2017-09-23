package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/24 0024.
 */
@DataSource("datamanagement")
public class ReturnMeterService {
    @Autowired
    private ReturnMeterMapper returnMeterMapper;
    public void add(ReturnMeter returnMeter) {
        returnMeterMapper.add(returnMeter);
    }
    public void update(ReturnMeter returnMeter) {
         returnMeterMapper.update(returnMeter);
    }

    public int delete(ReturnMeter returnMeter) {
        return returnMeterMapper.delete(returnMeter);
    }

    public List<Map<String,Object>> list() {
        return returnMeterMapper.getlist();
    }

    public List<Map<String,Object>> getBatchlist()
    {
        return returnMeterMapper.getBatchlist();
    }

    public List<Map<String,Object>> findByCondition(Map<String,Object> condition)
    {
        return returnMeterMapper.findByCondition(condition);
    }
    public List<Map<String,Object>> findBySearch(Map<String,Object> condition)
    {
        return returnMeterMapper.findBySearch(condition);
    }
    public List<Map<String,Object>> findByMap(Map<String,Object> condition)
    {
        return returnMeterMapper.findByMap(condition);
    }
    public List<Map<String,Object>> findLastData()
    {
        return returnMeterMapper.findLastData();
    }

}
