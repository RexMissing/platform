package org.whut.dataManagement.business.meterAnalysis.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public interface ReturnValveAndNameMapper {
    List<Map<String,Object>> findValveAndName(Map<String,Object> condition);
}
