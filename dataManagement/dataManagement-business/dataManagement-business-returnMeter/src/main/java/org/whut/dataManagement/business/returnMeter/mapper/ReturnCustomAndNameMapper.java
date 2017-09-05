package org.whut.dataManagement.business.returnMeter.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/30 0030.
 */
public interface ReturnCustomAndNameMapper {
    public List<Map<String,Object>> findCustomAndName(Map<String,Object> condition);
}
