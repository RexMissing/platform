package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeterInfo;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterInfoMapper;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
@DataSource("datamanagement")
public class ReturnMeterInfoService{
    @Autowired
    private ReturnMeterInfoMapper returnMeterInfoMapper;

    public void add(ReturnMeterInfo returnMeterInfo) {
        returnMeterInfoMapper.add(returnMeterInfo);
    }

    public void update(ReturnMeterInfo returnMeterInfo) {
         returnMeterInfoMapper.update(returnMeterInfo);
    }

    public int delete(ReturnMeterInfo returnMeterInfo) {
        return returnMeterInfoMapper.delete(returnMeterInfo);
    }

    public List<ReturnMeterInfo> getlist() {
        return returnMeterInfoMapper.getlist();
    }
}
