package org.whut.dataManagement.business.meterProduceData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterProduceData.mapper.StorageMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/7 0007.
 */
@DataSource("NetBarCode")
public class StorageService {
    @Autowired
    private StorageMapper storageMapper;
    public List<Map<String,Object>> findByCondition(String fmetercode)
    {
        return storageMapper.findByCondition(fmetercode);
    }
}
