package org.whut.dataManagement.business.meterAnalysis.mapper;

import org.whut.dataManagement.business.meterAnalysis.entity.MeterAnalysis;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/8.
 */
public interface ProductAnalysisMapper extends AbstractMapper<MeterAnalysis> {
    int ifSamecode(MeterAnalysis meterAnalysis);
    List<MeterAnalysis> getList();
    List<MeterAnalysis> getListByFanalysor(String fanalysor);
    List<MeterAnalysis> findBySearch(Map<String,Object> condition);
}
