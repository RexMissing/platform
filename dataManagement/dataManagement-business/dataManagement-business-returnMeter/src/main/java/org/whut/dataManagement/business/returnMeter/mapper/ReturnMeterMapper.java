package org.whut.dataManagement.business.returnMeter.mapper;

import org.whut.dataManagement.business.returnMeter.entity.ReturnMeter;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import javax.ws.rs.FormParam;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2017/7/24 0024.
 */
public interface ReturnMeterMapper extends AbstractMapper<ReturnMeter> {
    List<ReturnMeter> getlist();
    List<Map<String,Object>> findBySearch(Map<String,Object> condition);
    List<Map<String,Object>> findByCondition(Map<String, Object> conditions);
    List<Map<String,Object>> findByMap(Map<String,Object> condition);
}
