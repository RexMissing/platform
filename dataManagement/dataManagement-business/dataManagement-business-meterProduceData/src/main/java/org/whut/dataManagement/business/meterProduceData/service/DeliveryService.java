package org.whut.dataManagement.business.meterProduceData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterProduceData.mapper.DeliveryMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@DataSource("NetBarCode")
public class DeliveryService {
    @Autowired
    private DeliveryMapper deliveryMapper;
    public List<Map<String,Object>> findByCondition(String fmetercode)
    {
        return deliveryMapper.findByCondition(fmetercode);
    }
}
