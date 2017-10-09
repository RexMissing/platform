package org.whut.dataManagement.business.returnMeter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.dataManagement.business.returnMeter.mapper.ReturnMeterMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/9 0009.
 */
@DataSource("NetBarCode")
public class ConfirmCodeService {
    @Autowired
    private ReturnMeterMapper returnMeterMapper;

    public List<Map<String,Object>> confirmFMeterCode(Map<String,Object> condition)
    {
        return returnMeterMapper.confirmFMeterCode(condition);
    }
}
