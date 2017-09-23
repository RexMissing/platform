package org.whut.dataManagement.business.returnMeter.mapper;

import org.whut.dataManagement.business.returnMeter.entity.MonthReturnMeters;
import org.whut.dataManagement.business.returnMeter.entity.ReturnMeterInfo;
import org.whut.dataManagement.business.returnMeter.entity.StaQuery;
import org.whut.platform.fundamental.orm.mapper.AbstractMapper;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/26 0026.
 */
public interface ReturnMeterInfoMapper extends AbstractMapper<ReturnMeterInfo> {
    List<ReturnMeterInfo> getlist();
    List<Map<String,Object>> findByCondition(Map<String, Object> conditions);
    List<Map<String,Object>> findBySearch(Map<String,Object> condition);

    List<ReturnMeterInfo> getstalist(StaQuery staQuery);
    List<ReturnMeterInfo> getinlist(StaQuery staQuery);
    List<ReturnMeterInfo> getcomlist(StaQuery staQuery);
    List<ReturnMeterInfo> getretlist(StaQuery staQuery);
    List<ReturnMeterInfo> gettypelist(StaQuery staQuery);
    int getMonthReturnMeters(MonthReturnMeters monthReturnMeters);
    int getYearReturnMeters(MonthReturnMeters monthReturnMeters);
}
