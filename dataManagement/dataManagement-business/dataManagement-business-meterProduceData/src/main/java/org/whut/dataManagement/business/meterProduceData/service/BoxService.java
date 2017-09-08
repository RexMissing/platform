package org.whut.dataManagement.business.meterProduceData.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.meterProduceData.mapper.BoxMapper;

import javax.validation.constraints.Max;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/6 0006.
 */
@DataSource("NetBarCode")
public class BoxService {
    @Autowired
    private BoxMapper boxMapper;
    public List<Map<String,Object>> findByCondition(String fmetercode)
    {
        return boxMapper.findByCondition(fmetercode);
    }
}
