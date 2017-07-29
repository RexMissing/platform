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

    public List<ReturnMeter> list() {
        return returnMeterMapper.getlist();
    }
}
