package org.whut.dataManagement.business.meterAnalysis.mapper;

import org.whut.dataManagement.business.meterAnalysis.entity.MeterAnalysis;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public interface MeterAnalysisMapper extends AbstractMapper<MeterAnalysis> {
    String getCusByCode(String fmetercode);
    String getReportMisByCode(String fmetercode);
    List<MeterAnalysis> getList();
    List<MeterAnalysis> getListByFanalysor(String fanalysor);
    List<Map<String,String>> getConfirmList();
    List<Map<String,Object>> findByCondition(Map<String,Object> condition);
    List<MeterAnalysis> findBySearch(Map<String,Object> condition);
    List<Map<String,String>> findValveAndName(Map<String,String> condition);
}
