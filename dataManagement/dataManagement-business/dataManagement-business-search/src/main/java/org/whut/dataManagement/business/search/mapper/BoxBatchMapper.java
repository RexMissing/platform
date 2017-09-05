package org.whut.dataManagement.business.search.mapper;

import org.whut.dataManagement.business.search.entity.BoxBatch;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/7 0007.
 */
public interface BoxBatchMapper extends AbstractMapper<BoxBatch> {
    public List<Map<String,Object>> getBatchInfo(Map<String,Object> condition);
}
