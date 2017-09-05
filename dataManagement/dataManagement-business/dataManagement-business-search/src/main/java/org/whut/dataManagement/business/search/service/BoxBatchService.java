package org.whut.dataManagement.business.search.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.whut.dataManagement.business.dynamicDataSource.DataSource;
import org.whut.dataManagement.business.search.mapper.BoxBatchMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
@DataSource("NetBarCode")
public class BoxBatchService {
    @Autowired
    private BoxBatchMapper boxBatchMapper;

    public List<Map<String,Object>> getBatchInfo(Map<String,Object> condition)
    {
        return boxBatchMapper.getBatchInfo(condition);
    }
}
