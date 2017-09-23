package org.whut.dataManagement.business.returnMeter.mapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/21 0021.
 */
public interface ReturnByCodeMapper {
    List<Map<String,Object>> findByCode(Map<String,Object> condition);
}
