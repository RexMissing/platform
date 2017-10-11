package org.whut.dataManagement.business.inspection.mapper;

import org.whut.dataManagement.business.inspection.entity.Inspection;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/10.
 */
public interface InspectionMapper extends AbstractMapper<Inspection> {
    List<Map<String,Object>> getlist();
    List<Map<String,Object>> findLastBatch();
    List<Map<String,Object>> getBatchlist();
    List<Map<String,Object>> findBySearch(Map<String,Object> condition);
    List<Map<String,Object>> findByfoperator(String foperator);
}
