package org.whut.dataManagement.business.meterAnalysis.mapper;

import org.whut.dataManagement.business.meterAnalysis.entity.MeterAnalysis;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/27 0027.
 */
public interface MeterAnalysisMapper extends AbstractMapper<MeterAnalysis> {
    public List<Map<String,String>> getList();
    public List<Map<String,String>> getConfirmList();
}
